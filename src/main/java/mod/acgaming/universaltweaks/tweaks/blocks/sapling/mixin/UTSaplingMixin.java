package mod.acgaming.universaltweaks.tweaks.blocks.sapling.mixin;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTRandomUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockSapling.class)
public abstract class UTSaplingMixin extends BlockBush
{
    @Shadow
    public abstract void grow(World worldIn, BlockPos pos, IBlockState state, Random rand);

    /**
     * @author ACGaming
     * @reason Optimized + customizable sapling update tick
     */
    @Overwrite
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            super.updateTick(worldIn, pos, state, rand);
            if (worldIn.getLightFromNeighbors(pos.up()) >= UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingLightLevel && UTRandomUtil.chance(UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingGrowthChance) && worldIn.isAreaLoaded(pos, 1)) this.grow(worldIn, pos, state, rand);
        }
    }
}