package mod.acgaming.universaltweaks.tweaks.blocks.endportal.mixin;

import net.minecraft.block.BlockEndPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = BlockEndPortal.class)
public abstract class UTEndPortalMixin
{
    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    private void utEnsureDownIsRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.BLOCKS.utRenderEndPortalBottom) return;
        cir.setReturnValue(side == EnumFacing.UP || side == EnumFacing.DOWN);
    }
}