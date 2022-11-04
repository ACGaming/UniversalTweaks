package mod.acgaming.universaltweaks.bugfixes.blockoverlay.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.blockoverlay.UTBlockOverlay;
import mod.acgaming.universaltweaks.bugfixes.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Meldexun
@Mixin(RenderGlobal.class)
public class UTBlockOverlayMixin
{
    @Shadow
    private WorldClient world;

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ChunkRenderContainer;renderChunkLayer(Lnet/minecraft/util/BlockRenderLayer;)V"))
    public void utBlockOverlay(BlockRenderLayer blockLayerIn, CallbackInfo info)
    {
        if (!UTConfig.bugfixes.utBlockOverlayToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBlockOverlay ::: Render block layer");
        Block block = world.getBlockState(mc.player.getPosition().up()).getBlock();
        if ((blockLayerIn == BlockRenderLayer.SOLID && !UTBlockOverlayLists.blacklistedBlocks.contains(block)) || UTBlockOverlayLists.whitelistedBlocks.contains(block))
        {
            UTBlockOverlay.renderNearbyBlocks(Minecraft.getMinecraft().getRenderPartialTicks());
        }
    }
}