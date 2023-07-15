package mod.acgaming.universaltweaks.mods.tconstruct;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import slimeknights.tconstruct.tools.common.block.BlockToolForge;
import slimeknights.tconstruct.tools.common.inventory.ContainerToolForge;

// Courtesy of Focamacho
public class UTTConstructEvents
{
    //Tool Forge Dupe Fix
    @SubscribeEvent
    public void onRightClickBlock(RightClickBlock event)
    {
        if (event.getEntityPlayer() != null)
        {
            if (event.getWorld().getBlockState(event.getPos()).getBlock() instanceof BlockToolForge)
            {
                List<EntityPlayer> players = event.getWorld().getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.getPos().getX() - 32, event.getPos().getY() - 32, event.getPos().getZ() - 32, event.getPos().getX() + 32, event.getPos().getY() + 32, event.getPos().getZ() + 32));
                for (EntityPlayer player : players)
                {
                    if (player.openContainer instanceof ContainerToolForge)
                    {
                        ContainerToolForge container = (ContainerToolForge) player.openContainer;
                        if (container.getTile().getPos().equals(event.getPos()))
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
        if (event.player.openContainer instanceof ContainerToolForge)
        {
            if (!event.player.getEntityData().getBoolean("ToolForgeDupeFix"))
            {
                BlockPos forgePos = ((ContainerToolForge) event.player.openContainer).getTile().getPos();
                List<EntityPlayer> players = event.player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(event.player.posX - 32, event.player.posY - 32, event.player.posZ - 32, event.player.posX + 32, event.player.posY + 32, event.player.posZ + 32));
                for (EntityPlayer player : players)
                {
                    if (player != event.player && player.openContainer instanceof ContainerToolForge)
                    {
                        ContainerToolForge container = (ContainerToolForge) player.openContainer;
                        if (container.getTile().getPos().equals(forgePos))
                        {
                            event.player.closeScreen();
                            event.player.sendMessage(new TextComponentString("Someone is already using this."));
                            break;
                        }
                    }
                }
                event.player.getEntityData().setBoolean("ToolForgeDupeFix", true);
            }
        }
        else
        {
            event.player.getEntityData().setBoolean("ToolForgeDupeFix", false);
        }
    }
}