package mod.acgaming.universaltweaks.bugfixes.misc.smoothlighting.mixin;

import net.minecraft.block.state.IBlockState;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of Sk1erLLC
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "net.minecraft.client.renderer.BlockModelRenderer$AmbientOcclusionFace")
public class UTSmoothLightingMixin
{
    @Redirect(method = "updateVertexBrightness(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;[FLjava/util/BitSet;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;isTranslucent()Z"))
    public boolean utSmoothLighting(IBlockState state)
    {
        if (!UTConfigBugfixes.MISC.utAccurateSmoothLighting) return state.isTranslucent();
        return !state.causesSuffocation() || state.getLightOpacity() == 0;
    }
}