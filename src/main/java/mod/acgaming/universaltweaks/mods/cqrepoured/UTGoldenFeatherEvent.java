package mod.acgaming.universaltweaks.mods.cqrepoured;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import team.cqr.cqrepoured.init.CQRItems;

public class UTGoldenFeatherEvent
{
    @SubscribeEvent
    public void onLivingFallEvent(LivingFallEvent event)
    {
        if (event.getDistance() > 0.0F && event.getEntityLiving() instanceof EntityPlayer)
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