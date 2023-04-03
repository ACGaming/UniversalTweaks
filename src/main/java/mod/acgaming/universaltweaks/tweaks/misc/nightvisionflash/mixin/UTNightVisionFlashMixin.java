package mod.acgaming.universaltweaks.tweaks.misc.nightvisionflash.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class UTNightVisionFlashMixin
{
    @Inject(method = "getNightVisionBrightness", at = @At("HEAD"), cancellable = true)
    public void utNightVisionFlash(EntityLivingBase entitylivingbaseIn, float partialTicks, CallbackInfoReturnable<Float> cir)
    {
        if (UTConfig.TWEAKS_MISC.utNightVisionFlashToggle && entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration() > 0) cir.setReturnValue(1F);
    }
}