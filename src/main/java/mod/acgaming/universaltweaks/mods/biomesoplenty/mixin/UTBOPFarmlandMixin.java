package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import biomesoplenty.common.block.BlockBOPFarmland;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BlockBOPFarmland.class, remap = false)
public abstract class UTBOPFarmlandMixin extends BlockFarmland
{

    /// Mimicking the behavior of [BlockFarmland#turnToDirt(World, BlockPos)]
    @Unique
    private static boolean ut$turnToDirt(World world, BlockPos pos, IBlockState state)
    {
        boolean result = world.setBlockState(pos, state);
        AxisAlignedBB sliver = field_194405_c.offset(pos);
        for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, sliver))
        {
            double pushUp = Math.min(sliver.maxY - sliver.minY, sliver.maxY - entity.getEntityBoundingBox().minY);
            entity.setPositionAndUpdate(entity.posX, entity.posY + pushUp + 0.001D, entity.posZ);
        }
        return result;
    }

    @Shadow
    public abstract IBlockState getDirtBlockState(IBlockState state);

    /**
     * @author MCTian_mi
     * @reason to mimic vanilla behavior
     */
    @Overwrite
    public void onFallenUpon(@NotNull World world, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance)
    {
        IBlockState dirtState = getDirtBlockState(world.getBlockState(pos));
        if (ForgeHooks.onFarmlandTrample(world, pos, dirtState, fallDistance, entity)) // Forge: Move logic to Entity#canTrample
        {
            ut$turnToDirt(world, pos, dirtState);
        }
        entity.fall(fallDistance, 1.0F); // Block#fall impl
    }

    @Redirect(
        method = "updateTick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"
        )
    )
    private boolean utBOPFarmlandUpdateTickTurnToDirt(World world, BlockPos pos, IBlockState state)
    {
        return ut$turnToDirt(world, pos, state);
    }

    @Redirect(
        method = "neighborChanged",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"
        )
    )
    private boolean utBOPFarmlandNeighborChangedTurnToDirt(World world, BlockPos pos, IBlockState state)
    {
        return ut$turnToDirt(world, pos, state);
    }

    @Unique
    @Override
    public void onBlockAdded(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state)
    {
        // Not calling super 'cause it's hardcoded to turn this into dirt
        if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
        {
            ut$turnToDirt(worldIn, pos, getDirtBlockState(state));
        }
    }
}
