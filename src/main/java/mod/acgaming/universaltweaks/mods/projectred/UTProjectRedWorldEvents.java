package mod.acgaming.universaltweaks.mods.projectred;

import net.minecraft.item.Item;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import mrtjp.projectred.exploration.ContainerBackpack;

// Courtesy of Focamacho
public class UTProjectRedWorldEvents
{
    //Backpack Dupe Fix
    @SubscribeEvent
    public void onRightClick(RightClickBlock event)
    {
        if (event.getItemStack().getItem().equals(Item.getByNameOrId("projectred-exploration:backpack")))
        {
            if (event.getEntityPlayer() != null)
            {
                event.getEntityPlayer().getEntityData().setInteger("PREBackpackDupeFix", event.getEntityPlayer().inventory.currentItem);
            }
        }
    }

    @SubscribeEvent
    public void onRightClickItem(RightClickItem event)
    {
        if (event.getItemStack().getItem().equals(Item.getByNameOrId("projectred-exploration:backpack")))
        {
            if (event.getEntityPlayer() != null)
            {
                event.getEntityPlayer().getEntityData().setInteger("PREBackpackDupeFix", event.getEntityPlayer().inventory.currentItem);
            }
        }
    }

    @SubscribeEvent
    public void onTickPlayer(PlayerTickEvent event)
    {
        if (event.player.openContainer != null)
        {
            if (event.player.getEntityData().getInteger("PREBackpackDupeFix") != -1 && event.player.openContainer.getClass().equals(ContainerBackpack.class))
            {
                if (event.player.inventory.currentItem != event.player.getEntityData().getInteger("PREBackpackDupeFix"))
                {
                    event.player.closeScreen();
                    event.player.getEntityData().setInteger("PREBackpackDupeFix", -1);
                }
            }
            else
            {
                event.player.getEntityData().setInteger("PREBackpackDupeFix", -1);
            }
        }
    }
}