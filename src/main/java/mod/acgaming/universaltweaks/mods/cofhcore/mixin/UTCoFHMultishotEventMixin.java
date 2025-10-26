package mod.acgaming.universaltweaks.mods.cofhcore.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

import cofh.core.init.CoreEnchantments;
import cofh.core.proxy.EventHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EventHandler.class)
public abstract class UTCoFHMultishotEventMixin
{
    @Redirect(method = "handleArrowLooseEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0))
    public Item utCoFHMultishotArrowLoose(ItemStack stack)
    {
        if (UTConfigMods.COFH_CORE.utCoFHMultishotToggle && stack.getItem() instanceof ItemBow && EnchantmentHelper.getEnchantmentLevel(CoreEnchantments.multishot, stack) > 0) return Items.BOW;
        return stack.getItem();
    }

    @Redirect(method = "handleArrowNockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0))
    public Item utCoFHMultishotArrowNock(ItemStack stack)
    {
        if (UTConfigMods.COFH_CORE.utCoFHMultishotToggle && stack.getItem() instanceof ItemBow && EnchantmentHelper.getEnchantmentLevel(CoreEnchantments.multishot, stack) > 0) return Items.BOW;
        return stack.getItem();
    }
}
