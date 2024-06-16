package mod.acgaming.universaltweaks.tweaks.misc.particlelimit.mixin;

import net.minecraft.client.particle.ParticleManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(ParticleManager.class)
public class UTParticleManagerMixin
{
    @ModifyConstant(method = "updateEffects", constant = @Constant(intValue = 16384))
    public int utRenderParticles(int original)
    {
        if (UTConfigTweaks.MISC.utParticleLimit <= 0) return original;
        return UTConfigTweaks.MISC.utParticleLimit;
    }
}