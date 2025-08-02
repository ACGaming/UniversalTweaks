package mod.acgaming.universaltweaks.tweaks.items.itementities;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.util.UTRayTraceEntity;

public class UTEntityItemInteract
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utEntityItemInteract(PlayerInteractEvent.RightClickBlock event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityItemInteract ::: Right click block");
        IBlockState state = event.getWorld().getBlockState(event.getPos()).getActualState(event.getWorld(), event.getPos());
        if (state.getCollisionBoundingBox(event.getWorld(), event.getPos()) != Block.NULL_AABB) return;
        EntityPlayer player = event.getEntityPlayer();
        if (player == null) return;
        Entity entity = UTRayTraceEntity.rayTraceEntityAsPlayer(player);
        if (entity instanceof EntityItem)
        {
            entity.processInitialInteract(player, player.getActiveHand());
            event.setCanceled(true);
        }
    }
}