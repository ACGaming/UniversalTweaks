package mod.acgaming.universaltweaks.tweaks.performance.weathereffects.mixin;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.EnumParticleTypes;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@Mixin(EntityRenderer.class)
public abstract class UTEntityRendererMixin
{
    @WrapWithCondition(method = "addRainParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"))
    private boolean utDisableRainParticles(WorldClient world, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        return !UTConfigTweaks.PERFORMANCE.utDisableRainParticles;
    }

    @WrapOperation(method = "renderRainSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;getRainStrength(F)F"))
    private float utRemoveRainBounce(WorldClient world, float partialTicks, Operation<Float> original)
    {
        if (!UTConfigTweaks.PERFORMANCE.utDisableRainParticles) return original.call(world, partialTicks);
        return 0f;
    }
}
