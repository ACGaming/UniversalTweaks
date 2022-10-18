package mod.acgaming.hkntweaks.tweaks.stronghold.worldgen;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

/**
 * Courtesy of yungnickyoung
 * Replacement for normal StructureComponent in StructureSafeStrongholdPieces.
 */
public abstract class SafeStructureComponent extends StructureComponent
{
    // Duplicate vars needed since the originals are private
    public Mirror _mirror;
    public Rotation _rotation;

    public SafeStructureComponent(int type)
    {
        super(type);
    }

    public SafeStructureComponent()
    {
    }

    /**
     * Same as original {@link StructureComponent#setBlockState} method but with additional checks on existing blocks
     * to improve performance and prevent chests being destroyed.
     */
    @Override
    protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
    {
        BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y), this.getZWithOffset(x, z));
        if (boundingboxIn.isVecInside(blockpos))
        {
            IBlockState currBlockState = worldIn.getBlockState(blockpos).getBlock().getDefaultState();
            if (currBlockState == blockstateIn.getBlock().getDefaultState()) return;
            if (currBlockState == Blocks.CHEST.getDefaultState()) return; // don't break chests
            if (this._mirror != Mirror.NONE) blockstateIn = blockstateIn.withMirror(this._mirror);
            if (this._rotation != Rotation.NONE) blockstateIn = blockstateIn.withRotation(this._rotation);
            worldIn.setBlockState(blockpos, blockstateIn, 2);
        }
    }

    /**
     * Same as original {@link StructureComponent#fillWithBlocks} method but without conditional check on
     * {@code existingOnly} boolean parameter.
     */
    @Override
    protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
    {
        for (int i = yMin; i <= yMax; ++i)
        {
            for (int j = xMin; j <= xMax; ++j)
            {
                for (int k = zMin; k <= zMax; ++k)
                {
                    if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax) this.setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
                    else this.setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
                }
            }
        }
    }

    /**
     * Same as original {@link StructureComponent#fillWithRandomizedBlocks} method but without conditional check on
     * {@code alwaysReplace} boolean parameter.
     */
    @Override
    protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector)
    {
        for (int i = minY; i <= maxY; ++i)
        {
            for (int j = minX; j <= maxX; ++j)
            {
                for (int k = minZ; k <= maxZ; ++k)
                {
                    blockselector.selectBlocks(rand, j, i, k, i == minY || i == maxY || j == minX || j == maxX || k == minZ || k == maxZ);
                    this.setBlockState(worldIn, blockselector.getBlockState(), j, i, k, boundingboxIn);
                }
            }
        }
    }

    /**
     * Calls original {@link StructureComponent#setCoordBaseMode} method and copies values to duplicate
     * vars since originals are private.
     */
    @Override
    public void setCoordBaseMode(@Nullable EnumFacing facing)
    {
        super.setCoordBaseMode(facing);
        if (facing == null)
        {
            this._rotation = Rotation.NONE;
            this._mirror = Mirror.NONE;
        }
        else
        {
            switch (facing)
            {
                case SOUTH:
                    this._mirror = Mirror.LEFT_RIGHT;
                    this._rotation = Rotation.NONE;
                    break;
                case WEST:
                    this._mirror = Mirror.LEFT_RIGHT;
                    this._rotation = Rotation.CLOCKWISE_90;
                    break;
                case EAST:
                    this._mirror = Mirror.NONE;
                    this._rotation = Rotation.CLOCKWISE_90;
                    break;
                default:
                    this._mirror = Mirror.NONE;
                    this._rotation = Rotation.NONE;
            }
        }
    }
}