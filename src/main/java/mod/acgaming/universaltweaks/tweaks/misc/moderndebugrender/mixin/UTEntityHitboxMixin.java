package mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.UTModernDebugRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RenderManager.class)
public abstract class UTEntityHitboxMixin
{
    // The lines are unlit, so the entity brightness is swapped for full bright and restored afterwards
    @Inject(method = "renderDebugBoundingBox", at = @At("HEAD"))
    private void utStrokeWidth(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci, @Share("utBrightnessX") LocalFloatRef brightnessX, @Share("utBrightnessY") LocalFloatRef brightnessY)
    {
        GlStateManager.glLineWidth(UTModernDebugRender.STROKE_WIDTH);
        brightnessX.set(OpenGlHelper.lastBrightnessX);
        brightnessY.set(OpenGlHelper.lastBrightnessY);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
    }

    // Use the actual box for eye pos calculation
    @ModifyArgs(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawBoundingBox(DDDDDDFFFF)V", ordinal = 2))
    private void utEyeBox(Args args, @Local(argsOnly = true, ordinal = 0) Entity entity, @Local(argsOnly = true, ordinal = 0) double x, @Local(argsOnly = true, ordinal = 1) double y, @Local(argsOnly = true, ordinal = 2) double z, @Local(ordinal = 0) AxisAlignedBB box)
    {
        double eyeY = y + box.minY - entity.posY + entity.getEyeHeight();
        args.set(0, box.minX - entity.posX + x);
        args.set(1, eyeY - 0.01D);
        args.set(2, box.minZ - entity.posZ + z);
        args.set(3, box.maxX - entity.posX + x);
        args.set(4, eyeY + 0.01D);
        args.set(5, box.maxZ - entity.posZ + z);
    }

    // The only draw in this method is the view vector, so afterwards the arrow head can be appended
    // while texturing and depth writes are still off.
    @Inject(method = "renderDebugBoundingBox", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;draw()V", shift = At.Shift.AFTER))
    private void utArrowHeadAndCentre(Entity entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci, @Share("utBrightnessX") LocalFloatRef brightnessX, @Share("utBrightnessY") LocalFloatRef brightnessY)
    {
        Vec3d eye = new Vec3d(x, y + entity.getEyeHeight(), z);
        UTModernDebugRender.drawArrowHead(eye, eye.add(entity.getLook(partialTicks).scale(2.0D)), 0.0F, 0.0F, 1.0F);
        UTModernDebugRender.drawPoint(x, y, z, 1.0F, 1.0F, 1.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessX.get(), brightnessY.get());
        GlStateManager.glLineWidth(UTModernDebugRender.THIN_WIDTH);
    }
}