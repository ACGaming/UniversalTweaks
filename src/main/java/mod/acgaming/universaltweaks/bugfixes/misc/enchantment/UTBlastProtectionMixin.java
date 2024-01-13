package mod.acgaming.universaltweaks.bugfixes.misc.enchantment.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

// MC-198809
// https://bugs.mojang.com/browse/MC-198809
// Courtesy of fonnymunkey
@Mixin(EnchantmentProtection.class)
public abstract class UTBlastProtectionMixin
{
    /**
     * @author fonnymunkey
     * @reason Fix blast protection flooring reduction making it not effective
     */
    @Overwrite
    public static double getBlastDamageReduction(EntityLivingBase entityLivingBaseIn, double damage)
    {
        int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.BLAST_PROTECTION, entityLivingBaseIn);

        if (i > 0)
        {
            if (UTConfigBugfixes.MISC.utBlastProtectionKnockbackToggle)
            {
                damage -= damage * Math.min(1D, (double) ((float) i * 0.15F));
            } else
            {
                damage -= (double) MathHelper.floor(damage * (double) ((float) i * 0.15F));
            }
        }

        return damage;
    }
}