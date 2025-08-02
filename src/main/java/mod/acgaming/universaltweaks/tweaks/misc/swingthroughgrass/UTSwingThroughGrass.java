package mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.util.UTRayTraceEntity;

// Courtesy of Exidex, Rongmario
public class UTSwingThroughGrass
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utSwingThroughGrass(PlayerInteractEvent.LeftClickBlock event)
    {
        if (UTSwingThroughGrassLists.blacklistedItems.contains(event.getEntityPlayer().getHeldItemMainhand().getItem())) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSwingThroughGrass ::: Left click block event");
        Entity entity = getEntityBehindGrass(event.getWorld(), event.getPos(), event.getEntityPlayer());
        if (entity != null)
        {
            event.getEntityPlayer().attackTargetEntityWithCurrentItem(entity);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utInteractThroughGrass(PlayerInteractEvent.RightClickBlock event)
    {
        if (UTSwingThroughGrassLists.blacklistedItems.contains(event.getEntityPlayer().getHeldItemMainhand().getItem())) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSwingThroughGrass ::: Right click block event");
        Entity entity = getEntityBehindGrass(event.getWorld(), event.getPos(), event.getEntityPlayer());
        if (entity != null)
        {
            event.getEntityPlayer().interactOn(entity, event.getHand());
            event.setCanceled(true);
        }
    }

    public static Entity getEntityBehindGrass(World world, BlockPos pos, EntityPlayer player)
    {
        if (player == null) return null;
        IBlockState state = world.getBlockState(pos).getActualState(world, pos);
        Block block = state.getBlock();
        if ((UTSwingThroughGrassLists.blacklistedBlocks.contains(block) || state.getCollisionBoundingBox(world, pos) != Block.NULL_AABB) && !UTSwingThroughGrassLists.whitelistedBlocks.contains(block)) return null;
        return UTRayTraceEntity.rayTraceEntityAsPlayer(player);
    }
}