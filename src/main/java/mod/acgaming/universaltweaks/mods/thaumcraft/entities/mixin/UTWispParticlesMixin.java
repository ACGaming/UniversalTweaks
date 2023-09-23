package mod.acgaming.universaltweaks.mods.thaumcraft.entities.mixin;

import mod.acgaming.universaltweaks.config.UTConfigMods;
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
        if (UTConfigMods.THAUMCRAFT_ENTITIES.utTCWispParticlesToggle) return 10;
        else return constant;
    }
}