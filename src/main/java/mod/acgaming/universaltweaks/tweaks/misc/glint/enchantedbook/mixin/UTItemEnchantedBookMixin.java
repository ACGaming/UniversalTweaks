package mod.acgaming.universaltweaks.tweaks.misc.glint.enchantedbook.mixin;

import net.minecraft.item.ItemEnchantedBook;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = ItemEnchantedBook.class)
public abstract class UTItemEnchantedBookMixin
{
    @ModifyReturnValue(method = "hasEffect", at = @At("RETURN"))
    private boolean utHasEffect(boolean original)
    {
        if (!UTConfigTweaks.MISC.utDisableEnchantmentBookGlint) return original;
        return false;
    }
}