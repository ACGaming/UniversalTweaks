package mod.acgaming.universaltweaks.bugfixes.misc.depthmask.mixin;

import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.monster.EntityCreeper;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// MC-79697
// https://bugs.mojang.com/browse/MC-79697
@Mixin(LayerCreeperCharge.class)
public abstract class UTCreeperLayerMixin implements LayerRenderer<EntityCreeper>
{
    @ModifyArg(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntityCreeper;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;depthMask(Z)V", ordinal = 1))
    public boolean utCreeperLayer(boolean flagIn)
    {
        if (UTConfig.BUGFIXES_MISC.utDepthMaskToggle) return true;
        else return flagIn;
    }
}