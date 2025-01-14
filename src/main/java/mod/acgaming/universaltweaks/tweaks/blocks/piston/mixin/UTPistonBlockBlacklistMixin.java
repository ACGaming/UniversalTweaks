package mod.acgaming.universaltweaks.tweaks.blocks.piston.mixin;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.tweaks.blocks.piston.UTPistonBlockBlacklist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockPistonBase.class)
public abstract class UTPistonBlockBlacklistMixin
{
    @Inject(method = "canPush", at = @At("HEAD"), cancellable = true)
    private static void utPistonCanPush(IBlockState blockState, World world, BlockPos pos, EnumFacing facing, boolean destroyBlocks, EnumFacing enumFacing, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPistonBlockBlacklist ::: Can push block check");
        if (UTPistonBlockBlacklist.BLOCK_BLACKLIST.contains(blockState.getBlock())) cir.setReturnValue(false);
    }
}
