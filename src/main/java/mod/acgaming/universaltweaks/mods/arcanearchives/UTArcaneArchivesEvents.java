package mod.acgaming.universaltweaks.mods.arcanearchives;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import com.aranaira.arcanearchives.blocks.RadiantCraftingTable;
import com.aranaira.arcanearchives.inventory.ContainerRadiantCraftingTable;

// Courtesy of Focamacho
public class UTArcaneArchivesEvents
{
    //Radiant Crafting Table dupe Fix
    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event)
    {
        if (event.getEntityPlayer() != null)
        {
            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof RadiantCraftingTable)
            {
                List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
                for (EntityPlayer player : players)
                {
                    if (player.openContainer instanceof ContainerRadiantCraftingTable)
                    {
                        ContainerRadiantCraftingTable container = (ContainerRadiantCraftingTable) player.openContainer;
                        if (container.tile.getPos().equals(event.getPos()))
                        {
                            event.setCanceled(true);
                            event.getEntityPlayer().sendMessage(new TextComponentString("Someone is already using this."));
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onTickPlayer(PlayerTickEvent event)
    {
        if (event.player.openContainer instanceof ContainerRadiantCraftingTable)
        {
            if (!event.player.getEntityData().getBoolean("RadiantCraftingTableDupeFix"))
            {
                BlockPos craftingTablePos = ((ContainerRadiantCraftingTable) event.player.openContainer).tile.getPos();
                List<EntityPlayer> players = event.player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.player.posX - 32, event.player.posY - 32, event.player.posZ - 32, event.player.posX + 32, event.player.posY + 32, event.player.posZ + 32));
                for (EntityPlayer player : players)
                {
                    if (player != event.player && player.openContainer instanceof ContainerRadiantCraftingTable)
                    {
                        ContainerRadiantCraftingTable container = (ContainerRadiantCraftingTable) player.openContainer;
                        if (container.tile.getPos().equals(craftingTablePos))
                        {
                            event.player.closeScreen();
                            event.player.sendMessage(new TextComponentString("Someone is already using this."));
                            break;
                        }
                    }
                }
                event.player.getEntityData().setBoolean("RadiantCraftingTableDupeFix", true);
            }
        }
        else
        {
            event.player.getEntityData().setBoolean("RadiantCraftingTableDupeFix", false);
        }
    }
}