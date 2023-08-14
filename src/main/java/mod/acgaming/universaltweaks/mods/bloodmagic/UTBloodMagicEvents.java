package mod.acgaming.universaltweaks.mods.bloodmagic;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import WayofTime.bloodmagic.item.armour.ItemSentientArmour;

// Courtesy of Focamacho
public class UTBloodMagicEvents
{
    //Sentient Armor Dupe Fix
    @SubscribeEvent
    public void detectSentientArmor(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.side == Side.SERVER)
        {
            int fullArmor = 0;
            for (ItemStack item : event.player.inventory.armorInventory)
            {
                if (item.getItem() instanceof ItemSentientArmour)
                {
                    fullArmor++;
                }
            }
            if (fullArmor > 0 && fullArmor < 4)
            {
                if (event.player.inventory.getItemStack().getItem() instanceof ItemSentientArmour) event.player.inventory.setItemStack(ItemStack.EMPTY);
                for (Slot slot : event.player.inventoryContainer.inventorySlots)
                {
                    if (slot.getStack().getItem() instanceof ItemSentientArmour)
                    {
                        slot.putStack(ItemStack.EMPTY);
                    }
                }
                for (EntityItem entityItem : event.player.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(event.player.getPosition().getX() - 10, event.player.getPosition().getY() - 10, event.player.getPosition().getZ() - 10), new BlockPos(event.player.getPosition().getX() + 10, event.player.getPosition().getY() + 10, event.player.getPosition().getZ() + 10))))
                {
                    if (entityItem.getItem().getItem() instanceof ItemSentientArmour)
                    {
                        event.player.world.removeEntity(entityItem);
                    }
                }
            }
        }
    }
}