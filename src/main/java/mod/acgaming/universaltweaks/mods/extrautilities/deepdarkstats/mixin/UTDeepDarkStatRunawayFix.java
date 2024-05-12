package mod.acgaming.universaltweaks.mods.extrautilities.deepdarkstats.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rwtema.extrautils2.dimensions.deep_dark.WorldProviderDeepDark;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.entity.SharedMonsterAttributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = WorldProviderDeepDark.class, remap = false)
public abstract class UTDeepDarkStatRunawayFix
{
    @ModifyExpressionValue(method = "noMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;getBaseValue()D", ordinal = 0))
    private static double utFixMaxHealth(double original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats) return original;
        return SharedMonsterAttributes.MAX_HEALTH.getDefaultValue();
    }

    @ModifyExpressionValue(method = "noMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/attributes/IAttributeInstance;getBaseValue()D", ordinal = 1))
    private static double utFixAttackDamage(double original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats) return original;
        return SharedMonsterAttributes.ATTACK_DAMAGE.getDefaultValue();
    }
}
