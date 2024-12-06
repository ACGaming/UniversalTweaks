package mod.acgaming.universaltweaks.tweaks.blocks.bedobstruction.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of fonnymunkey
@Mixin(EntityPlayer.class)
public class UTBedObstructionMixin
{
    @Inject(at = @At("HEAD"), method = "getBedSpawnLocation(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Z)Lnet/minecraft/util/math/BlockPos;", cancellable = true)
    private static void utBedSpawnLocation(World world, BlockPos bedLocation, boolean forceSpawn, CallbackInfoReturnable<BlockPos> cir)
    {
        if (!UTConfigTweaks.BLOCKS.utBedObstructionToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBedObstruction ::: Get bed spawn location");
        IBlockState state = world.getBlockState(bedLocation);
        Block block = state.getBlock();
        if (!block.isBed(state, world, bedLocation, null))
        {
            if (!forceSpawn) cir.setReturnValue(null);
            else cir.setReturnValue(ut$iterateSpawnPoint(world, bedLocation));
        }
        else cir.setReturnValue(ut$iterateBedPoint(world, bedLocation));
    }

    @Unique
    private static BlockPos ut$iterateSpawnPoint(World world, BlockPos pos)
    {
        if (ut$isValidSpawnPos(world, pos, false)) return pos;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int xIter = x - 1; xIter <= x + 1; ++xIter)
        {
            for (int zIter = z - 1; zIter <= z + 1; ++zIter)
            {
                BlockPos blockPos = new BlockPos(xIter, y, zIter);
                if (ut$isValidSpawnPos(world, blockPos, false)) return blockPos;
            }
        }
        return null;
    }

    @Unique
    private static BlockPos ut$iterateBedPoint(World world, BlockPos pos)
    {
        IBlockState blockState = world.getBlockState(pos);
        EnumFacing enumFacing = blockState.getPropertyKeys().contains(BlockHorizontal.FACING) ? blockState.getValue(BlockHorizontal.FACING) : EnumFacing.NORTH;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int yOffset = 0; yOffset <= 1; ++yOffset)
        {
            for (int facingOffset = 0; facingOffset <= 1; ++facingOffset)
            {
                int xOffset = x - enumFacing.getXOffset() * facingOffset;
                int zOffset = z - enumFacing.getZOffset() * facingOffset;
                for (int xIter = xOffset - 1; xIter <= xOffset + 1; ++xIter)
                {
                    for (int zIter = zOffset - 1; zIter <= zOffset + 1; ++zIter)
                    {
                        BlockPos blockPos = new BlockPos(xIter, y + yOffset, zIter);
                        if (ut$isValidSpawnPos(world, blockPos, true)) return blockPos;
                    }
                }
            }
        }
        return null;
    }

    @Unique
    private static boolean ut$isValidSpawnPos(World worldIn, BlockPos blockPos, boolean requireFloor)
    {
        return (worldIn.getBlockState(blockPos.down()).getMaterial().isSolid() || !requireFloor) && worldIn.getBlockState(blockPos).getBlock().canSpawnInBlock() && worldIn.getBlockState(blockPos.up()).getBlock().canSpawnInBlock();
    }
}