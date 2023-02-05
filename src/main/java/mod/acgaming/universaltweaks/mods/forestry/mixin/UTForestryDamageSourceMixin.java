package mod.acgaming.universaltweaks.mods.forestry.mixin;

import forestry.core.utils.DamageSourceForestry;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(DamageSourceForestry.class)
public class UTForestryDamageSourceMixin
{
    @WrapWithCondition(method = "<init>", at = @At(value = "INVOKE", target = "Lforestry/core/utils/DamageSourceForestry;setDamageBypassesArmor()Lnet/minecraft/util/DamageSource;"))
    public boolean utForestryDamageSource(DamageSourceForestry instance)
    {
        return !UTConfig.MOD_INTEGRATION.FORESTRY.utBeeDamageArmorBypassToggle;
    }
}