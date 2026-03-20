package mod.acgaming.universaltweaks.tweaks.world.flatbedrock.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

import mod.acgaming.universaltweaks.tweaks.world.flatbedrock.UTFlatBedrockList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkPrimer.class)
public abstract class UTFlatBedrockMixin
{
    @Unique
    private static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();

    @Inject(method = "setBlockState", at = @At("HEAD"), cancellable = true)
    public void utFlatBedrock(int x, int y, int z, IBlockState state, CallbackInfo ci)
    {
        if (state == BEDROCK && !UTFlatBedrockList.WHITELISTED_HEIGHTS.contains(y)) ci.cancel();
    }
}
