package mod.acgaming.universaltweaks.mods.extrautilities.breakcreativemill.mixin;

import com.rwtema.extrautils2.ExtraUtils2;
import com.rwtema.extrautils2.backend.XUBlockStatic;
import com.rwtema.extrautils2.blocks.BlockPassiveGenerator;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = BlockPassiveGenerator.class, remap = false)
public abstract class UTBreakableCreativeMill extends XUBlockStatic
{
    @Inject(method = "getBlockHardness", at = @At(value = "HEAD"), cancellable = true)
    private void utBreakCreativeMill(IBlockState blockState, World worldIn, BlockPos pos, CallbackInfoReturnable<Float> cir)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utFixCreativeMillHarvestability) return;
        if (ExtraUtils2.allowNonCreativeHarvest) cir.setReturnValue(super.getBlockHardness(blockState, worldIn, pos));
    }
}
