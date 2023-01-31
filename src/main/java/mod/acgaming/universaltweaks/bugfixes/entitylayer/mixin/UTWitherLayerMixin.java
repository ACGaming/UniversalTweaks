package mod.acgaming.universaltweaks.bugfixes.entitylayer.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.boss.EntityWither;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-79697
// https://bugs.mojang.com/browse/MC-79697
@Mixin(LayerWitherAura.class)
public abstract class UTWitherLayerMixin implements LayerRenderer<EntityWither>
{
    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/boss/EntityWither;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableBlend()V", shift = At.Shift.AFTER))
    public void utWitherLayer(EntityWither entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci)
    {
        if (UTConfig.BUGFIXES_ENTITIES.utEntityLayersToggle) GlStateManager.depthMask(true);
    }
}