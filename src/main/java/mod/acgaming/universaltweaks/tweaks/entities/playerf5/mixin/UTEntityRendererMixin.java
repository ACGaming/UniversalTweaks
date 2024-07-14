package mod.acgaming.universaltweaks.tweaks.entities.playerf5.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(EntityRenderer.class)
public abstract class UTEntityRendererMixin
{
    @WrapOperation(method = "orientCamera", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;rayTraceBlocks(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;)Lnet/minecraft/util/math/RayTraceResult;"))
    private RayTraceResult utCamaraBypassesNonSolidBlocks(WorldClient instance, Vec3d start, Vec3d end, Operation<RayTraceResult> original)
    {
        if (!UTConfigTweaks.ENTITIES.utThirdPersonIgnoresNonSolidBlocks) return original.call(instance, start, end);
        return instance.rayTraceBlocks(start, end, false, true, true);
    }
}
