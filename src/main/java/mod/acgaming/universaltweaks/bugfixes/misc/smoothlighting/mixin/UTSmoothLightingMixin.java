package mod.acgaming.universaltweaks.bugfixes.misc.smoothlighting.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelRenderer;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of Sk1erLLC
@Mixin(BlockModelRenderer.AmbientOcclusionFace.class)
public class UTSmoothLightingMixin
{
    @Redirect(method = "updateVertexBrightness", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;isTranslucent()Z"))
    public boolean utSmoothLighting(IBlockState state)
    {
        if (!UTConfigBugfixes.MISC.utAccurateSmoothLighting) return state.isTranslucent();
        return !state.causesSuffocation() || state.getLightOpacity() == 0;
    }
}
