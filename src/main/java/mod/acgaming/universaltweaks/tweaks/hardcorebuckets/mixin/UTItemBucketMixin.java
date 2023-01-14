package mod.acgaming.universaltweaks.tweaks.hardcorebuckets.mixin;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBucket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemBucket.class)
public class UTItemBucketMixin
{
    @Redirect(method = "tryPlaceContainedLiquid", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z"))
    public boolean utItemBucket(World world, BlockPos blockPos, IBlockState blockState, int flags)
    {
        if (!UTConfig.tweaks.utHardcoreBucketsToggle) return world.setBlockState(blockPos, blockState, flags);
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTItemBucket ::: Place liquid");
        try
        {
            return world.setBlockState(blockPos, blockState.withProperty(BlockLiquid.LEVEL, 1), flags);
        }
        catch (Exception ignored)
        {
            return world.setBlockState(blockPos, blockState, flags);
        }
    }
}