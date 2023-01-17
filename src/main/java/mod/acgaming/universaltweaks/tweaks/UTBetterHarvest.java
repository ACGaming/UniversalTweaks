package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockReed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTBetterHarvest
{
    @SubscribeEvent
    public static void utBetterHarvest(BlockEvent.BreakEvent event)
    {
        if (!UTConfig.TWEAKS_BLOCKS.utBetterHarvestToggle || event.getPlayer().isSneaking()) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterHarvest ::: Block break event");
        IBlockState blockState = event.getState();
        Block block = blockState.getBlock();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        if (block instanceof BlockReed && !(world.getBlockState(pos.down()).getBlock() instanceof BlockReed))
        {
            event.setCanceled(true);
            if (world.getBlockState(pos.up()).getBlock() instanceof BlockReed) world.destroyBlock(pos.up(), true);
        }
        else if (block instanceof BlockCactus && !(world.getBlockState(pos.down()).getBlock() instanceof BlockCactus))
        {
            event.setCanceled(true);
            if (world.getBlockState(pos.up()).getBlock() instanceof BlockCactus) world.destroyBlock(pos.up(), true);
        }
        else if (block instanceof BlockCrops)
        {
            if (!((BlockCrops) block).isMaxAge(blockState)) event.setCanceled(true);
        }
    }
}