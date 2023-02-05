package mod.acgaming.universaltweaks.bugfixes.modelgap.mixin;

import java.util.List;

import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.modelgap.UTModelFix;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of MehVahdJukaar
@Mixin(ItemModelGenerator.class)
public class UTItemModelGeneratorMixin
{
    @Inject(method = "getBlockParts(Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;Ljava/lang/String;I)Ljava/util/List;", at = @At("RETURN"))
    public void utEnlargeFaces(TextureAtlasSprite sprite, String texture, int tintIndex, CallbackInfoReturnable<List<BlockPart>> cir)
    {
        if (!UTConfig.BUGFIXES_MISC.MODEL_GAP.utModelGapToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTItemModelGenerator ::: Enlarge faces");
        UTModelFix.enlargeFaces(cir);
    }

    @Inject(method = "createOrExpandSpan", at = @At(value = "HEAD"), cancellable = true)
    public void utCreateOrExpandSpan(List<ItemModelGenerator.Span> listSpans, ItemModelGenerator.SpanFacing spanFacing, int pixelX, int pixelY, CallbackInfo ci)
    {
        if (!UTConfig.BUGFIXES_MISC.MODEL_GAP.utModelGapToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTItemModelGenerator ::: Create or expand span");
        UTModelFix.createOrExpandSpan(listSpans, spanFacing, pixelX, pixelY);
        ci.cancel();
    }
}