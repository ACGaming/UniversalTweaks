package mod.acgaming.universaltweaks.tweaks.misc.viewbobbing.mixin;

import net.minecraft.client.renderer.EntityRenderer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks.BobbingMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderer.class)
public class UTViewBobbingMixin
{
    @Redirect(method = "setupCameraTransform", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V"))
    private void utCameraBobbing(EntityRenderer entity, float partialTicks)
    {
        if (UTConfigTweaks.MISC.utViewBobbing != BobbingMode.HAND_ONLY) ((UTApplyBobbingInvoker) (Object)entity).invokeApplyBobbing(partialTicks);
    }

    @Redirect(method = "renderHand", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V"))
    private void utHandBobbing(EntityRenderer entity, float partialTicks)
    {
        if (UTConfigTweaks.MISC.utViewBobbing != BobbingMode.CAMERA_ONLY) ((UTApplyBobbingInvoker) (Object)entity).invokeApplyBobbing(partialTicks);
    }
}