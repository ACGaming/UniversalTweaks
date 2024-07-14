package mod.acgaming.universaltweaks.tweaks.world.stronghold.mixin;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;

import org.spongepowered.asm.mixin.Mixin;

/**
 * Forces stronghold to always generate its blocks by ignoring air checks.
 * Other modded structures may still overwrite stronghold gen.
 */
// Courtesy of yungnickyoung
@Mixin(StructureStrongholdPieces.Stronghold.class)
public abstract class UTSafeStrongholdGenMixin extends StructureComponent
{
    // Ignore air blocks when generating.
    @Override
    protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
    {
        super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, false);
    }

    // Ignore air blocks when generating.
    @Override
    protected void fillWithRandomizedBlocks(World worldIn, StructureBoundingBox boundingboxIn, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean alwaysReplace, Random rand, BlockSelector blockselector)
    {
        super.fillWithRandomizedBlocks(worldIn, boundingboxIn, minX, minY, minZ, maxX, maxY, maxZ, false, rand, blockselector);
    }
}
