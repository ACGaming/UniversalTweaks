package mod.acgaming.universaltweaks.tweaks.blocks.observer.mixin;

import net.minecraft.block.BlockObserver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = BlockObserver.class)
public abstract class UTBlockObserverMixin
{
    @Shadow
    protected abstract void updateNeighborsInFront(World worldIn, BlockPos pos, IBlockState state);

    @Inject(method = "onBlockAdded", at = @At("HEAD"), cancellable = true)
    private void utPreventPlacementFromCausingPulse(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci)
    {
        if (!UTConfigTweaks.BLOCKS.utPreventObserverActivatesOnPlacement) return;
        if (!worldIn.isRemote)
        {
            if (state.getValue(BlockObserver.POWERED) && !worldIn.isUpdateScheduled(pos, ((BlockObserver) (Object) this)))
            {
                IBlockState unpowered = state.withProperty(BlockObserver.POWERED, false);
                worldIn.setBlockState(pos, unpowered, 18);
                this.updateNeighborsInFront(worldIn, pos, state);
            }
        }
        ci.cancel();
    }

}