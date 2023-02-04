package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import thaumcraft.common.entities.monster.EntityWisp;

@Mixin(EntityWisp.class)
public class UTWispParticlesMixin
{
    @ModifyConstant(method = "onDeath", constant = @Constant(floatValue = 1.0F))
    public float utWispParticles(float constant)
    {
        if (UTConfig.MOD_INTEGRATION.THAUMCRAFT.utTCWispParticlesToggle) return 10;
        else return constant;
    }
}