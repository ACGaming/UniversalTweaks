package mod.acgaming.universaltweaks.mods.infernalmobs.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;

import atomicstryker.infernalmobs.common.mods.MM_Sticky;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MM_Sticky.class)
public class UTInfernalMobsStickyMixin
{
    @Redirect(method = "onHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;setPickupDelay(I)V"))
    public void utInfernalMobsSticky(EntityItem entityItem, int ticks)
    {
        if (!UTConfig.MOD_INTEGRATION.INFERNAL_MOBS.utIMStickyRecallToggle) entityItem.setPickupDelay(ticks);
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfernalMobsSticky ::: Set pickup delay");
        Enchantment recall = Enchantment.getEnchantmentByLocation("capsule:recall");
        if (recall != null && EnchantmentHelper.getEnchantmentLevel(recall, entityItem.getItem()) > 0) entityItem.setNoPickupDelay();
        else entityItem.setPickupDelay(ticks);
    }
}