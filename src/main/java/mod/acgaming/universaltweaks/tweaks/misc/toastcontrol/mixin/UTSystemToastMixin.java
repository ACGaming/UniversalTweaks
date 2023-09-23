package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.mixin;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.RecipeToast;
import net.minecraft.item.crafting.IRecipe;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RecipeToast.class)
public class UTSystemToastMixin
{
    @Inject(method = "addOrUpdate", at = @At(value = "HEAD"), cancellable = true)
    private static void utSystemToast(GuiToast p_193665_0_, IRecipe p_193665_1_, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlSystemToggle) ci.cancel();
    }
}