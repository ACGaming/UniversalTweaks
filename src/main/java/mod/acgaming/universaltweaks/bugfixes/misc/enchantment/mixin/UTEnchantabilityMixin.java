package mod.acgaming.universaltweaks.bugfixes.misc.enchantment.mixin;

import net.minecraft.enchantment.EnchantmentHelper;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

/**
 * @author Invadermonky
 *
 * Adds a check at the head of {@link EnchantmentHelper#calcItemStackEnchantability(Random, int, int, ItemStack)}
 * preventing negative enchanting table power values from throwing exceptions when generating random values.
 */
@Mixin(EnchantmentHelper.class)
public class UTEnchantabilityMixin
{
    @ModifyVariable(method = "calcItemStackEnchantability", at = @At("HEAD"), ordinal = 1)
    private static int utPreventNegativePower(int power)
    {
        return Math.max(power, 0);
    }
}
