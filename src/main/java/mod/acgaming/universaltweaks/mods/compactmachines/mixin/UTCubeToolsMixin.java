package mod.acgaming.universaltweaks.mods.compactmachines.mixin;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos;
import net.minecraft.world.IBlockAccess;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.dave.compactmachines3.init.Blockss;
import org.dave.compactmachines3.misc.CubeTools;
import org.dave.compactmachines3.reference.EnumMachineSize;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of jchung01
@Mixin(value = CubeTools.class, remap = false)
public class UTCubeToolsMixin
{

    /**
     * Using a block's complete (x,y,z) position, determine the size of compact machine it belongs to.
     *
     * @param section the section of world, possibly a sub-chunk or chunk
     * @param pos     the block pos, including its y dimension
     * @return the corresponding compact machine's interior size + 1
     */
    @Unique
    private static int universalTweaks$getCubeSizeWithYContext(IBlockAccess section, MutableBlockPos pos)
    {
        pos.setPos(pos.getX() * 1024, pos.getY(), pos.getZ());
        for (int i = EnumMachineSize.values().length - 1; i >= 0; i--)
        {
            EnumMachineSize size = EnumMachineSize.values()[i];
            // (x + dimension, y, z)
            pos.move(EnumFacing.EAST, size.getDimension());
            if (section.getBlockState(pos).getBlock() == Blockss.wall)
            {
                return size.getDimension();
            }
            // Reset to (x, y, z)
            pos.move(EnumFacing.WEST, size.getDimension());
        }
        return EnumMachineSize.TINY.getDimension();
    }

    /**
     * Reassigns the size of the compact machine that is found from this block position using the y dimension.
     * This is necessary because of two things that interact badly:
     * <p>- {@link org.dave.compactmachines3.misc.CubeTools#getCubeSize(IBlockAccess, int)} only ever uses y=40 (the machine's base) for every block position to check the machine size.</p>
     * <p>- Some optimization mods (i.e. Nothirium, Vintagium) replace the vanilla {@link net.minecraft.world.ChunkCache} with separate, sub-chunk based caches.</p>
     * <br>
     * These two things mean that when such optimization mods are present, {@link IBlockAccess#getBlockState(BlockPos)} will only be able
     * to get block state info for the bottom sub-chunk of the machine, not the upper sub-chunk. This causes invisible walls for that upper sub-chunk.
     *
     * @param instance the EnumMachineSize instance
     * @param world    the section of world, possibly a sub-chunk or chunk
     * @param pos      the block pos
     * @return the corresponding compact machine's interior size + 1
     */
    @Redirect(method = "shouldSideBeRendered", at = @At(value = "INVOKE", target = "Lorg/dave/compactmachines3/reference/EnumMachineSize;getDimension()I"))
    private static int utReassignSize(EnumMachineSize instance, IBlockAccess world, BlockPos pos)
    {
        if (!UTConfigMods.COMPACT_MACHINES.utCMRenderFixToggle) return instance.getDimension();
        PooledMutableBlockPos mPos = PooledMutableBlockPos.retain(pos);
        try
        {
            return universalTweaks$getCubeSizeWithYContext(world, mPos.setPos(pos.getX() / 1024, pos.getY(), 0));
        }
        finally
        {
            // Have to manually release in 1.12
            mPos.release();
        }
    }

}