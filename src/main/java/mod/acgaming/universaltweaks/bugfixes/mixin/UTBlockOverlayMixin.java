package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;

import mod.acgaming.universaltweaks.bugfixes.UTBlockOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Meldexun
@Mixin(RenderGlobal.class)
public class UTBlockOverlayMixin
{
    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ChunkRenderContainer;renderChunkLayer(Lnet/minecraft/util/BlockRenderLayer;)V"))
    public void utBlockOverlay(BlockRenderLayer blockLayerIn, CallbackInfo info)
    {
        if (blockLayerIn == BlockRenderLayer.SOLID)
        {
            UTBlockOverlay.renderNearbyBlocks(Minecraft.getMinecraft().getRenderPartialTicks());
        }
    }
}