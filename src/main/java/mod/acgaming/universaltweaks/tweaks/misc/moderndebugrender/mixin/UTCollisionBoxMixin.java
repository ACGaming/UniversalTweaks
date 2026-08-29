package mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.mixin;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRendererCollisionBox;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.tweaks.misc.moderndebugrender.UTModernDebugRender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DebugRendererCollisionBox.class)
public abstract class UTCollisionBoxMixin
{
    @Shadow
    private double renderPosX;
    @Shadow
    private double renderPosY;
    @Shadow
    private double renderPosZ;

    @Unique
    private long utLastUpdate;

    @Unique
    private List<AxisAlignedBB> utShapes;

    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getCollisionBoxes(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;)Ljava/util/List;"))
    private List<AxisAlignedBB> utCacheShapes(World world, Entity entity, AxisAlignedBB box, Operation<List<AxisAlignedBB>> original)
    {
        long now = System.nanoTime();
        // null rather than a timestamp sentinel, because nanoTime has no defined origin to compare against
        if (this.utShapes == null || now - this.utLastUpdate > 100_000_000L)
        {
            this.utLastUpdate = now;
            Entity camera = Minecraft.getMinecraft().getRenderViewEntity();
            this.utShapes = camera == null ? original.call(world, entity, box) : original.call(world, camera, camera.getEntityBoundingBox().grow(6.0D));
        }
        return this.utShapes;
    }

    // The shapes arrive offset by the player position, which is not the camera while spectating
    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;drawSelectionBoundingBox(Lnet/minecraft/util/math/AxisAlignedBB;FFFF)V"), index = 0)
    private AxisAlignedBB utCameraOffset(AxisAlignedBB box, @Local(argsOnly = true) float partialTicks)
    {
        Vec3d camera = UTModernDebugRender.cameraPos(partialTicks);
        return camera == null ? box : box.offset(this.renderPosX - camera.x, this.renderPosY - camera.y, this.renderPosZ - camera.z);
    }

    @ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;glLineWidth(F)V"))
    private float utStrokeWidth(float width)
    {
        return UTModernDebugRender.STROKE_WIDTH;
    }
}