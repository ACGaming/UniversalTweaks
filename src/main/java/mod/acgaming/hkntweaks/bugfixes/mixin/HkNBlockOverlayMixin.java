package mod.acgaming.hkntweaks.bugfixes.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;

import mod.acgaming.hkntweaks.bugfixes.HkNBlockOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Meldexun
@Mixin(RenderGlobal.class)
public class HkNBlockOverlayMixin
{
    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ChunkRenderContainer;renderChunkLayer(Lnet/minecraft/util/BlockRenderLayer;)V"))
    public void hknBlockOverlay(BlockRenderLayer blockLayerIn, CallbackInfo info)
    {
        if (blockLayerIn == BlockRenderLayer.SOLID)
        {
            HkNBlockOverlay.renderNearbyBlocks(Minecraft.getMinecraft().getRenderPartialTicks());
        }
    }
}