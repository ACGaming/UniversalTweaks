package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of TheComputerizer
@Mixin(BlockStateContainer.StateImplementation.class)
public class UTRedstoneLightingMixin
{
    @Shadow
    @Final
    private Block block;

    @Unique
    public boolean isRedstoneComponent(Block block)
    {
        return block instanceof BlockRedstoneTorch
            || block instanceof BlockRedstoneWire
            || block instanceof BlockRedstoneRepeater
            || block instanceof BlockRedstoneComparator
            || block instanceof BlockCompressedPowered;
    }

    @Inject(at = @At(value = "HEAD"), method = "getLightValue()I", cancellable = true)
    public void utGetLightValue(CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfig.tweaks.utRedstoneLightingToggle && isRedstoneComponent(block)) cir.setReturnValue(0);
    }

    @Inject(at = @At(value = "HEAD"), method = "getLightValue(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;)I", cancellable = true, remap = false)
    public void utGetLightValueWorld(IBlockAccess world, BlockPos pos, CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfig.tweaks.utRedstoneLightingToggle && isRedstoneComponent(block)) cir.setReturnValue(0);
    }
}