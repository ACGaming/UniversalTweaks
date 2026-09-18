package mod.acgaming.universaltweaks.mods.extrautilities.enderlily.mixin;

import com.rwtema.extrautils2.worldgen.SingleChunkGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SingleChunkGen.class, remap = false)
public abstract class UTEnderLilyWorldgenMixin
{
    @Shadow
    @Final
    public String name;

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    private void utPreventEnderLilyWorldgenCrash(Chunk chunk, BlockPos pos, IBlockState state, CallbackInfo ci)
    {
        if ("EnderLillies".equals(name) && pos.getY() >= 256)
        {
            ci.cancel();
        }
    }
}