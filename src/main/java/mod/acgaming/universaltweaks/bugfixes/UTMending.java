package mod.acgaming.universaltweaks.bugfixes;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of FloorIsJava
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTMending
{
    public static final int DURABILITY_PER_XP = 2;

    public static ItemStack getDamagedEnchantedItem(final Enchantment ench, final EntityPlayer player)
    {
        final List<ItemStack> valid = ench.getEntityEquipment(player);
        if (valid.isEmpty()) return ItemStack.EMPTY;
        else
        {
            final List<ItemStack> choices = Lists.newArrayList();
            for (final ItemStack itemstack : valid) if (!itemstack.isEmpty() && itemstack.isItemDamaged() && EnchantmentHelper.getEnchantmentLevel(ench, itemstack) > 0) choices.add(itemstack);
            if (choices.isEmpty()) return ItemStack.EMPTY;
            return choices.get(player.getRNG().nextInt(choices.size()));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utPickupXP(final PlayerPickupXpEvent event)
    {
        if (!UTConfig.bugfixes.utMendingToggle) return;
        event.setCanceled(true);
        final EntityPlayer player = event.getEntityPlayer();
        final EntityXPOrb xp = event.getOrb();
        ItemStack item = getDamagedEnchantedItem(Enchantments.MENDING, player);
        player.xpCooldown = 2;
        player.onItemPickup(xp, 1);
        int repairPower = xp.xpValue * DURABILITY_PER_XP;
        while (!item.isEmpty() && repairPower > 0)
        {
            final int realRepair = Math.min(repairPower, item.getItemDamage());
            repairPower -= realRepair;
            item.setItemDamage(item.getItemDamage() - realRepair);
            item = getDamagedEnchantedItem(Enchantments.MENDING, player);
        }
        xp.xpValue = repairPower / DURABILITY_PER_XP;
        if (xp.xpValue > 0) player.addExperience(xp.xpValue);
        xp.setDead();
    }
}