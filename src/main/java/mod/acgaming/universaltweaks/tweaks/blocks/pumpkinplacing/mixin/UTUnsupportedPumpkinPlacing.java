package mod.acgaming.universaltweaks.tweaks.blocks.pumpkinplacing.mixin;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockPumpkin.class)
public abstract class UTUnsupportedPumpkinPlacing
{
    @Inject(method = "canPlaceBlockAt", at = @At(value = "HEAD"), cancellable = true)
    public void utUnsupportedPlacingOverride(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.BLOCKS.utUnsupportedPumpkinPlacing) return;
        cir.setReturnValue(worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos));
    }
}
