package mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.util.UTRayTraceEntity;

// Courtesy of Exidex, Rongmario
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTSwingThroughGrass
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utSwingThroughGrass(PlayerInteractEvent.LeftClickBlock event)
    {
        if (!UTConfig.TWEAKS_MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSwingThroughGrass ::: Left click block event");
        IBlockState state = event.getWorld().getBlockState(event.getPos()).getActualState(event.getWorld(), event.getPos());
        Block block = state.getBlock();
        if ((UTSwingThroughGrassLists.blacklistedBlocks.contains(block) || state.getCollisionBoundingBox(event.getWorld(), event.getPos()) != Block.NULL_AABB) && !UTSwingThroughGrassLists.whitelistedBlocks.contains(block)) return;
        EntityPlayer player = event.getEntityPlayer();
        if (player == null) return;
        Entity entity = UTRayTraceEntity.rayTraceEntityAsPlayer(player);
        if (entity != null)
        {
            player.attackTargetEntityWithCurrentItem(entity);
            event.setCanceled(true);
        }
    }
}