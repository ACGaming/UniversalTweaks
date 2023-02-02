package mod.acgaming.universaltweaks.bugfixes.depthmask.mixin;

import net.minecraft.client.particle.ParticleManager;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(ParticleManager.class)
public class UTParticleManagerMixin
{
    @WrapWithCondition(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 0))
    public boolean utRenderParticles(boolean flagIn)
    {
        return !UTConfig.BUGFIXES_MISC.utDepthMaskToggle;
    }
}