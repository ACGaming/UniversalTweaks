package mod.acgaming.universaltweaks.tweaks.misc.armorcurve.mixin;

import java.math.BigDecimal;

import net.minecraft.util.CombatRules;

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
        BigDecimal ret = UTArmorCurve.armor.with("damage", new BigDecimal(damage)).and("armor", new BigDecimal(armor)).and("toughness", new BigDecimal(armorToughness)).eval();
        ret = UTArmorCurve.toughness.with("damage", ret).and("armor", new BigDecimal(armor)).and("toughness", new BigDecimal(armorToughness)).eval();
        info.setReturnValue(ret.floatValue());
    }

    @Inject(cancellable = true, at = @At("HEAD"), method = "getDamageAfterMagicAbsorb(FF)F")
    private static void utGetDamageAfterMagicAbsorb(float damage, float prot, CallbackInfoReturnable<Float> info)
    {
        if (!Float.isFinite(damage) || !Float.isFinite(prot)) return;
        BigDecimal ret = UTArmorCurve.enchants.with("damage", new BigDecimal(damage)).and("enchant", new BigDecimal(prot)).eval();
        info.setReturnValue(ret.floatValue());
    }
}