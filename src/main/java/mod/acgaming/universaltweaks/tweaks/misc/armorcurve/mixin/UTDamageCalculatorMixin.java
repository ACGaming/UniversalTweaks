package mod.acgaming.universaltweaks.tweaks.misc.armorcurve.mixin;

import java.math.BigDecimal;

import net.minecraft.util.CombatRules;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.armorcurve.UTArmorCurve;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Jackiecrazy
@Mixin(CombatRules.class)
public abstract class UTDamageCalculatorMixin
{
    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageAfterAbsorb(FFF)F")
    private static void utGetDamageAfterAbsorb(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> info)
    {
        if (!Float.isFinite(damage) || !Float.isFinite(armor) || !Float.isFinite(armorToughness)) return;
        BigDecimal retArmor = UTArmorCurve.armor.with("damage", BigDecimal.valueOf(damage)).and("armor", BigDecimal.valueOf(armor)).and("toughness", BigDecimal.valueOf(armorToughness)).eval();
        if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveLogging) UniversalTweaks.LOGGER.info("UTArmorCurve ::: Armor Damage: " + retArmor);
        BigDecimal retToughness = UTArmorCurve.toughness.with("damage", retArmor).and("armor", BigDecimal.valueOf(armor)).and("toughness", BigDecimal.valueOf(armorToughness)).eval();
        if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveLogging) UniversalTweaks.LOGGER.info("UTArmorCurve ::: Armor Toughness Damage: " + retToughness);
        info.setReturnValue(retToughness.floatValue());
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageAfterMagicAbsorb(FF)F")
    private static void utGetDamageAfterMagicAbsorb(float damage, float prot, CallbackInfoReturnable<Float> info)
    {
        if (!Float.isFinite(damage) || !Float.isFinite(prot)) return;
        BigDecimal retEnchants = UTArmorCurve.enchants.with("damage", BigDecimal.valueOf(damage)).and("enchant", BigDecimal.valueOf(prot)).eval();
        if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveLogging) UniversalTweaks.LOGGER.info("UTArmorCurve ::: Enchantment Damage: " + retEnchants);
        info.setReturnValue(retEnchants.floatValue());
    }
}