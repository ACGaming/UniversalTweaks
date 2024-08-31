package mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlay;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

// Courtesy of jchung01
@Mixin(value = EntityRenderer.class)
public class UTEntityRendererMixin
{
    @WrapOperation(method = "renderWorldPass",
        slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/util/BlockRenderLayer;SOLID:Lnet/minecraft/util/BlockRenderLayer;", opcode = Opcodes.GETSTATIC),
            to = @At(value = "FIELD", target = "Lnet/minecraft/util/BlockRenderLayer;CUTOUT_MIPPED:Lnet/minecraft/util/BlockRenderLayer;", opcode = Opcodes.GETSTATIC)),
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;renderBlockLayer(Lnet/minecraft/util/BlockRenderLayer;DILnet/minecraft/entity/Entity;)I"))
    private int utSetBlockOverlayState(RenderGlobal instance, BlockRenderLayer layer, double partialTicks, int pass, Entity entity, Operation<Integer> original)
    {
        if (!UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) return original.call(instance, layer, partialTicks, pass, entity);
        int ret;
        UTBlockOverlay.setRender(true);
        ret = original.call(instance, layer, partialTicks, pass, entity);
        UTBlockOverlay.setRender(false);
        return ret;
    }
}
