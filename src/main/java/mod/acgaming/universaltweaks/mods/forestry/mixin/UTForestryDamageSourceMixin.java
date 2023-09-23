package mod.acgaming.universaltweaks.mods.forestry.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import forestry.core.utils.DamageSourceForestry;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DamageSourceForestry.class)
public class UTForestryDamageSourceMixin
{
    @WrapWithCondition(method = "<init>", at = @At(value = "INVOKE", target = "Lforestry/core/utils/DamageSourceForestry;setDamageBypassesArmor()Lnet/minecraft/util/DamageSource;"))
    public boolean utForestryDamageSource(DamageSourceForestry instance)
    {
        return !UTConfigMods.FORESTRY.utFOBeeDamageArmorBypassToggle;
    }
}