package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.mixin;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.multiplayer.ClientAdvancementManager;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientAdvancementManager.class)
public class UTAdvancementToastMixin
{
    @WrapWithCondition(method = "read", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/toasts/GuiToast;add(Lnet/minecraft/client/gui/toasts/IToast;)V"))
    public boolean utAdvancementToast(GuiToast instance, IToast toastIn)
    {
        return !UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlAdvancementsToggle;
    }
}