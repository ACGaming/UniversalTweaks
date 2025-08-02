package mod.acgaming.universaltweaks.bugfixes.entities.consumption;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

// MC-849
// https://bugs.mojang.com/browse/MC-849
// Courtesy of Marcono1234, makamys
public class UTDoubleConsumption
{
    @SubscribeEvent
    public static void utDoubleConsumption(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.player.getActiveItemStack() != ItemStack.EMPTY)
        {
            ItemStack currentItem = event.player.inventory.getCurrentItem();
            if (event.player.getActiveItemStack() != currentItem && ItemStack.areItemStacksEqual(currentItem, event.player.getActiveItemStack()))
            {
                event.player.activeItemStack = currentItem;
            }
        }
    }
}