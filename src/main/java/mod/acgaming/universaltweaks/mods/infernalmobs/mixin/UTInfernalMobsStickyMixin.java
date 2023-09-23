package mod.acgaming.universaltweaks.mods.infernalmobs.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import atomicstryker.infernalmobs.common.mods.MM_Sticky;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MM_Sticky.class)
public class UTInfernalMobsStickyMixin
{
    @Redirect(method = "onHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItem;setPickupDelay(I)V"))
    public void utInfernalMobsStickyRecall(EntityItem entityItem, int ticks)
    {
        if (!UTConfigMods.INFERNAL_MOBS.utIMStickyRecallToggle) entityItem.setPickupDelay(ticks);
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfernalMobsSticky ::: Set pickup delay");
        Enchantment recall = Enchantment.getEnchantmentByLocation("capsule:recall");
        if (recall != null && EnchantmentHelper.getEnchantmentLevel(recall, entityItem.getItem()) > 0) entityItem.setNoPickupDelay();
        else entityItem.setPickupDelay(ticks);
    }

    @Redirect(method = "onHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/DamageSource;getTrueSource()Lnet/minecraft/entity/Entity;", ordinal = 0))
    public Entity utInfernalMobsStickyPedestal(DamageSource source)
    {
        if (UTConfigMods.INFERNAL_MOBS.utIMStickyPedestalToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfernalMobsSticky ::: Get true source");
            if (source.getTrueSource() instanceof EntityPlayer && source.getTrueSource().getName().equals("reliquary_pedestal_fake_player")) return null;
        }
        return source.getTrueSource();
    }
}