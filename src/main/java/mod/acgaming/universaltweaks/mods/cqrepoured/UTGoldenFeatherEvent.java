package mod.acgaming.universaltweaks.mods.cqrepoured;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import team.cqr.cqrepoured.init.CQRItems;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTGoldenFeatherEvent
{
    @SubscribeEvent
    public static void onLivingFallEvent(LivingFallEvent event)
    {
        if (UTConfig.MOD_INTEGRATION.CHOCOLATE_QUEST.utCQRGoldenFeatherToggle && event.getDistance() > 0.0F && event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack mainhand = player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
            ItemStack offhand = player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
            if (mainhand.getItem() != CQRItems.FEATHER_GOLDEN && offhand.getItem() != CQRItems.FEATHER_GOLDEN)
            {
                for (ItemStack stack : player.inventory.mainInventory)
                {
                    if (stack.getItem() == CQRItems.FEATHER_GOLDEN)
                    {
                        stack.damageItem((int) event.getDistance(), player);
                        event.setDistance(0.0F);
                        return;
                    }
                }
            }
        }
    }
}