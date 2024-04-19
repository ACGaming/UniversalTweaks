package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.mixin;

import java.util.Arrays;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiToast.class)
public abstract class UTGuiToastMixin
{
    @Inject(method = "add", at = @At(value = "HEAD"), cancellable = true)
    private void utFilterToasts(IToast toastIn, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.TOAST_CONTROL.utToastNameLogging) UniversalTweaks.LOGGER.info("UTGuiToastMixin ::: Displaying Toast: " + toastIn.getClass().getName());
        boolean isWhitelist = UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlClassListMode == UTConfigTweaks.EnumLists.WHITELIST;
        if (Arrays.asList(UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlClassList).contains(toastIn.getClass().getName()) != isWhitelist) ci.cancel();
    }
}
