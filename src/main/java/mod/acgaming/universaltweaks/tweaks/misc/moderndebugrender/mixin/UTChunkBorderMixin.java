package mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.mixin;

import org.lwjgl.opengl.GL11;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.debug.DebugRendererChunkBorder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.UTModernDebugRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(DebugRendererChunkBorder.class)
public abstract class UTChunkBorderMixin
{
    // Interpolated camera Y. The X and Z it is derived from are dead once the two origins below are stored.
    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 1)
    private double utCameraY(double original, @Local(argsOnly = true) float partialTicks, @Share("utCameraY") LocalDoubleRef shared)
    {
        Vec3d camera = UTModernDebugRender.cameraPos(partialTicks);
        double value = camera == null ? original : camera.y;
        shared.set(value);
        return value;
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 5)
    private double utOriginX(double original, @Local(argsOnly = true) float partialTicks, @Share("utOriginX") LocalDoubleRef shared)
    {
        Vec3d camera = UTModernDebugRender.cameraPos(partialTicks);
        double value = camera == null ? original : UTModernDebugRender.sectionOrigin(camera.x);
        shared.set(value);
        return value;
    }

    @ModifyVariable(method = "render", at = @At("STORE"), ordinal = 6)
    private double utOriginZ(double original, @Local(argsOnly = true) float partialTicks, @Share("utOriginZ") LocalDoubleRef shared)
    {
        Vec3d camera = UTModernDebugRender.cameraPos(partialTicks);
        double value = camera == null ? original : UTModernDebugRender.sectionOrigin(camera.z);
        shared.set(value);
        return value;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V"))
    private void utKeepBlend()
    {
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    }

    // Colour calls 0-19 belong to the vertical grids, 20 onwards to the rings and the major lines.
    // Only the yellow ones are ours to recolour, so the red and blue passes need no further slicing.
    @ModifyArgs(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;color(FFFF)Lnet/minecraft/client/renderer/BufferBuilder;"),
        slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;color(FFFF)Lnet/minecraft/client/renderer/BufferBuilder;", ordinal = 19)))
    private void utGridCell(Args args, @Local(ordinal = 0) int coord)
    {
        if (coord % 4 == 0) utCellColor(args);
    }

    @ModifyArgs(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;color(FFFF)Lnet/minecraft/client/renderer/BufferBuilder;"),
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;color(FFFF)Lnet/minecraft/client/renderer/BufferBuilder;", ordinal = 20)))
    private void utRingCell(Args args, @Local(ordinal = 0) int coord)
    {
        if (coord % 8 == 0) utCellColor(args);
    }

    @ModifyArg(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glLineWidth(F)V"),
        slice = @Slice(to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glLineWidth(F)V", ordinal = 1)))
    private float utPassWidth(float width)
    {
        return UTModernDebugRender.THICK_WIDTH;
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/BufferBuilder;pos(DDD)Lnet/minecraft/client/renderer/BufferBuilder;", ordinal = 4), require = 1)
    private void utSplitGridBatch(float partialTicks, long finishTimeNano, CallbackInfo ci, @Local(ordinal = 0) Tessellator tess, @Local(ordinal = 0) BufferBuilder buffer)
    {
        tess.draw();
        GlStateManager.glLineWidth(UTModernDebugRender.THIN_WIDTH);
        buffer.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V", ordinal = 1, shift = At.Shift.AFTER), require = 1)
    private void utCameraSection(float partialTicks, long finishTimeNano, CallbackInfo ci, @Share("utCameraY") LocalDoubleRef cameraY, @Share("utOriginX") LocalDoubleRef originX, @Share("utOriginZ") LocalDoubleRef originZ)
    {
        double x = originX.get();
        double z = originZ.get();
        double y = UTModernDebugRender.sectionOrigin(cameraY.get());
        GlStateManager.glLineWidth(UTModernDebugRender.THIN_WIDTH);
        GlStateManager.disableDepth();
        RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(x, y, z, x + 16.0D, y + 16.0D, z + 16.0D), 0.25F, 0.25F, 1.0F, 1.0F);
        GlStateManager.enableDepth();
    }

    @Unique
    private static void utCellColor(Args args)
    {
        if ((float) args.get(0) != 1.0F || (float) args.get(1) != 1.0F || (float) args.get(2) != 0.0F) return;
        args.set(0, 0.0F);
        args.set(1, 155.0F / 255.0F);
        args.set(2, 155.0F / 255.0F);
    }
}