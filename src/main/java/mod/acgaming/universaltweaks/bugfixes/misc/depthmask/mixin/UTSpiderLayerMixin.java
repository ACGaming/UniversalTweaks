package mod.acgaming.universaltweaks.bugfixes.misc.depthmask.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-79697
// https://bugs.mojang.com/browse/MC-79697
@Mixin(LayerSpiderEyes.class)
public abstract class UTSpiderLayerMixin<T extends EntitySpider> implements LayerRenderer<T>
{
    @Inject(method = "doRenderLayer(Lnet/minecraft/entity/monster/EntitySpider;FFFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;enableAlpha()V", shift = At.Shift.AFTER))
    public void utSpiderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, CallbackInfo ci)
    {
        if (UTConfigBugfixes.MISC.utDepthMaskToggle) GlStateManager.depthMask(true);
    }
}