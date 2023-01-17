package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTLeafDecay
{
    @SubscribeEvent
    public static void utLeafDecay(BlockEvent.NeighborNotifyEvent event)
    {
        if (!UTConfig.TWEAKS_BLOCKS.utLeafDecayToggle) return;
        World world = event.getWorld();
        for (EnumFacing facing : event.getNotifiedSides())
        {
            BlockPos pos = event.getPos().offset(facing);
            IBlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block.isLeaves(blockState, world, pos)) world.scheduleUpdate(pos, block, 5 + world.rand.nextInt(6));
        }
    }
}