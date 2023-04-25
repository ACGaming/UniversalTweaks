package mod.acgaming.universaltweaks.tweaks.blocks.growthsize.mixin;

import java.util.Random;

import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockVine.class)
public class UTVineSizeMixin
{
    @Inject(method = "updateTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAreaLoaded(Lnet/minecraft/util/math/BlockPos;I)Z", shift = At.Shift.AFTER), cancellable = true)
    public void utVineSize(World worldIn, BlockPos pos, IBlockState state, Random rand, CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_BLOCKS.utVineSize < 1) return;
        int i;
        for (i = 1; worldIn.getBlockState(pos.up(i)) instanceof BlockVine; i++) {}
        if (i >= UTConfig.TWEAKS_BLOCKS.utVineSize) ci.cancel();
    }
}