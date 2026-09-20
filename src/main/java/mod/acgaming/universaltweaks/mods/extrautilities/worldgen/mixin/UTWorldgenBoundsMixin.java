package mod.acgaming.universaltweaks.mods.extrautilities.worldgen.mixin;

import com.rwtema.extrautils2.worldgen.SingleChunkGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SingleChunkGen.class, remap = false)
public abstract class UTWorldgenBoundsMixin
{
    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void utPreventOutOfBoundsWorldgen(Chunk chunk, BlockPos pos, IBlockState state, CallbackInfo ci)
    {
        if (chunk.getWorld().isOutsideBuildHeight(pos))
        {
            ci.cancel();
        }
    }
}