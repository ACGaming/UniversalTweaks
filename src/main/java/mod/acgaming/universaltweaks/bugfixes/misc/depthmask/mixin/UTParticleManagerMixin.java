package mod.acgaming.universaltweaks.bugfixes.misc.depthmask.mixin;

import net.minecraft.client.particle.ParticleManager;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleManager.class)
public class UTParticleManagerMixin
{
    @WrapWithCondition(method = "renderParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 0))
    public boolean utRenderParticles(boolean flagIn)
    {
        return !UTConfigBugfixes.MISC.utDepthMaskToggle;
    }
}