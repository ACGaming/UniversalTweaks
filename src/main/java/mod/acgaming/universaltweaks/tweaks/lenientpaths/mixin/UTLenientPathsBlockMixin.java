package mod.acgaming.universaltweaks.tweaks.lenientpaths.mixin;

import net.minecraft.block.BlockGrassPath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockGrassPath.class)
public class UTLenientPathsBlockMixin
{
    @Inject(method = "updateBlockState", at = @At("HEAD"), cancellable = true)
    public void utLenientPathsBlock(World worldIn, BlockPos pos, CallbackInfo ci)
    {
        if (UTConfig.TWEAKS_BLOCKS.utLenientPathsToggle) ci.cancel();
    }
}