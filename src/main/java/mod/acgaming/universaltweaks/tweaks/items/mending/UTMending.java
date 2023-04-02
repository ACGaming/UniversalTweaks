package mod.acgaming.universaltweaks.tweaks.items.mending;

import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of FloorIsJava & Rakambda
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTMending
{
    public static ItemStack getDamagedEnchantedItem(Enchantment ench, EntityPlayer player)
    {
        List<ItemStack> valid = ench.getEntityEquipment(player);
        if (valid.isEmpty()) return ItemStack.EMPTY;
        else
        {
            List<ItemStack> choices = Lists.newArrayList();
            for (ItemStack itemstack : valid) if (!itemstack.isEmpty() && itemstack.isItemDamaged() && EnchantmentHelper.getEnchantmentLevel(ench, itemstack) > 0) choices.add(itemstack);
            if (choices.isEmpty()) return ItemStack.EMPTY;
            return choices.get(player.getRNG().nextInt(choices.size()));
        }
    }

    public static ItemStack getDamagedEnchantedItemOP(Enchantment ench, EntityPlayer player)
    {
        IInventory playerInventory = player.inventory;
        return IntStream.range(0, playerInventory.getSizeInventory())
            .mapToObj(playerInventory::getStackInSlot)
            .filter(is -> !is.isEmpty())
            .filter(ItemStack::isItemStackDamageable)
            .filter(ItemStack::isItemDamaged)
            .filter(is -> EnchantmentHelper.getEnchantmentLevel(ench, is) > 0)
            .max(Comparator.comparing(ItemStack::getItemDamage))
            .orElse(ItemStack.EMPTY);
    }

    public static int roundAverage(float value)
    {
        double floor = Math.floor(value);
        return (int) floor + (Math.random() < value - floor ? 1 : 0);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utPickupXP(PlayerPickupXpEvent event)
    {
        if (!UTConfig.TWEAKS_ITEMS.MENDING.utMendingToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMending ::: Player pick up XP event");
        event.setCanceled(true);
        EntityPlayer player = event.getEntityPlayer();
        EntityXPOrb xp = event.getOrb();
        ItemStack itemStack = UTConfig.TWEAKS_ITEMS.MENDING.utMendingOPToggle ? getDamagedEnchantedItemOP(Enchantments.MENDING, player) : getDamagedEnchantedItem(Enchantments.MENDING, player);
        player.xpCooldown = 2;
        player.onItemPickup(xp, 1);
        if (!itemStack.isEmpty() && xp.xpValue > 0)
        {
            int i = Math.min(roundAverage(xp.xpValue * UTConfig.TWEAKS_ITEMS.MENDING.utMendingRatio), itemStack.getItemDamage());
            xp.xpValue -= roundAverage(i / UTConfig.TWEAKS_ITEMS.MENDING.utMendingRatio);
            itemStack.setItemDamage(itemStack.getItemDamage() - i);
        }
        if (xp.xpValue > 0) player.addExperience(xp.xpValue);
        xp.setDead();
    }
}