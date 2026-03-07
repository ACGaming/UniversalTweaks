package mod.acgaming.universaltweaks.bugfixes.misc.camera.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.Vec3d;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public abstract class UTOrientCameraMixin
{
    @ModifyArg(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"), index = 1)
    private Vec3d utOrientCamera(Vec3d end, @Local(name = "f5") float f5)
    {
        return new Vec3d(end.x - (double) f5, end.y, end.z);
    }
}
