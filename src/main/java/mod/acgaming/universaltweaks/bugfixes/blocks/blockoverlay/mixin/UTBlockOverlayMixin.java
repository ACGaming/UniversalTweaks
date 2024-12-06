package mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.BlockRenderLayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlay;
import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
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
    public WorldClient world;

    @Shadow
    @Final
    private Minecraft mc;

    @Inject(method = "renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ChunkRenderContainer;renderChunkLayer(Lnet/minecraft/util/BlockRenderLayer;)V"))
    public void utRenderBlockOverlay(BlockRenderLayer blockLayerIn, CallbackInfo info)
    {
        if (!UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle || !UTBlockOverlay.shouldRender()) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBlockOverlay ::: Render block layer");
        Block block = world.getBlockState(mc.player.getPosition().up()).getBlock();
        if ((blockLayerIn == BlockRenderLayer.SOLID && !UTBlockOverlayLists.blacklistedBlocks.contains(block)) || UTBlockOverlayLists.whitelistedBlocks.contains(block))
        {
            try
            {
                UTBlockOverlay.renderNearbyBlocks(Minecraft.getMinecraft().getRenderPartialTicks());
            }
            catch (Exception e)
            {
                UniversalTweaks.LOGGER.error("UTBlockOverlay ::: Error rendering nearby blocks", e);
            }
        }
    }
}