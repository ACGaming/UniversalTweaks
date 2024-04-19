package mod.acgaming.universaltweaks.tweaks.items.bowinfinity.mending.mixin;

import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentMending;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;

// Courtesy of WaitingIdly
@Mixin(value = EnchantmentArrowInfinite.class)
public abstract class UTEnchantmentArrowInfiniteMixin
{
    @WrapOperation(method = "canApplyTogether", constant = @Constant(classValue = EnchantmentMending.class))
    public boolean utMixinModifyConstant(Object ench, Operation<Boolean> original)
    {
        if (!UTConfigTweaks.ITEMS.INFINITY.utInfinityEnchantmentConflicts) return original.call(ench);
        return false;
    }
}
