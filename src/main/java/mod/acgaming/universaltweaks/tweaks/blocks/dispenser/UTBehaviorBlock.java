package mod.acgaming.universaltweaks.tweaks.blocks.dispenser;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.*;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Vazkii
public class UTBehaviorBlock extends BehaviorDefaultDispenseItem
{
    ItemBlock item;
    Block block;

    public UTBehaviorBlock(ItemBlock item, Block block)
    {
        this.item = item;
        this.block = block;
    }

    @Override
    public ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack)
    {
        if (UTConfig.TWEAKS_BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle)
        {
            try
            {
                EnumFacing facing = blockSource.getBlockState().getValue(BlockDispenser.FACING);
                EnumFacing.Axis axis = facing.getAxis();
                BlockPos pos = blockSource.getBlockPos().offset(facing);
                World world = blockSource.getWorld();
                if (world.isAirBlock(pos) && block.canPlaceBlockAt(world, pos))
                {
                    int meta = item.getMetadata(stack.getItemDamage());
                    IBlockState state = block.getStateFromMeta(meta);
                    Collection<IProperty<?>> props = state.getPropertyKeys();
                    if (props.contains(BlockDirectional.FACING)) state = state.withProperty(BlockDirectional.FACING, facing);
                    else if (props.contains(BlockHorizontal.FACING) && axis != Axis.Y) state = state.withProperty(BlockHorizontal.FACING, facing);
                    else if (props.contains(BlockRotatedPillar.AXIS)) state = state.withProperty(BlockRotatedPillar.AXIS, axis);

                    // SPECIAL CASES
                    if (block instanceof BlockStairs) state = state.withProperty(BlockHorizontal.FACING, facing.getOpposite());
                    if (block instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.LOG_AXIS.parseValue(axis.getName()).or(EnumAxis.NONE));
                    if (block instanceof BlockQuartz)
                    {
                        BlockQuartz.EnumType type = state.getValue(BlockQuartz.VARIANT);
                        if (ImmutableSet.of(BlockQuartz.EnumType.LINES_X, BlockQuartz.EnumType.LINES_Y, BlockQuartz.EnumType.LINES_Z).contains(type)) state = state.withProperty(BlockQuartz.VARIANT, BlockQuartz.VARIANT.parseValue("lines_" + axis.getName()).or(BlockQuartz.EnumType.LINES_Y));
                    }

                    world.setBlockState(pos, state);
                    SoundType soundtype = block.getSoundType();
                    world.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    stack.shrink(1);
                    return stack;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return super.dispenseStack(blockSource, stack);
    }
}