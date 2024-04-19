package mod.acgaming.universaltweaks.tweaks.blocks.farmlandtrample;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTFarmlandTrample
{
    @SubscribeEvent
    public static void utFarmlandTrample(BlockEvent.FarmlandTrampleEvent event)
    {
        switch (UTConfigTweaks.BLOCKS.utFarmlandTrample)
        {
            case DEFAULT:
                break;
            case NEVER:
                event.setCanceled(true);
                break;
            case ONLY_PLAYER:
                if (event.getEntity() instanceof EntityPlayer) break;
                event.setCanceled(true);
                break;
            case NOT_PLAYER:
                if (event.getEntity() instanceof EntityPlayer) event.setCanceled(true);
                break;
            case FEATHER_FALLING:
                if (Enchantments.FEATHER_FALLING == null) break;
                for (ItemStack slot : event.getEntity().getArmorInventoryList())
                {
                    if (slot.getItem() instanceof ItemArmor && ((ItemArmor) slot.getItem()).armorType == EntityEquipmentSlot.FEET && EnchantmentHelper.getEnchantmentLevel(Enchantments.FEATHER_FALLING, slot) >= 1)
                    {
                        event.setCanceled(true);
                    }
                }
        }
    }
}