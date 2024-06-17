package mod.acgaming.universaltweaks.tweaks.misc.buttons.realms.mixin;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Robitobi01, WaitingIdly
@Mixin(GuiMainMenu.class)
public class UTRealmsButtonMainMenuMixin extends GuiScreen
{
    @Shadow(remap = false)
    private GuiButton modButton;

    @Inject(method = "addSingleplayerMultiplayerButtons", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiMainMenu;addButton(Lnet/minecraft/client/gui/GuiButton;)Lnet/minecraft/client/gui/GuiButton;"), cancellable = true)
    public void utRemoveRealmsButtonMainMenu(int p_73969_1_, int p_73969_2_, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRealmsButtonToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle)
        {
            UniversalTweaks.LOGGER.debug("UTRealmsButtonMainMenu ::: Initialize buttons");
        }
        buttonList.add(modButton = new GuiButton(6, this.width / 2 - 100, p_73969_1_ + p_73969_2_ * 2, I18n.format("fml.menu.mods")));
        ci.cancel();
    }

    @ModifyReturnValue(method = "areRealmsNotificationsEnabled", at = @At("RETURN"))
    private boolean utDisableRealmNotification(boolean original)
    {
        if (!UTConfigTweaks.MISC.utRealmsButtonToggle) return original;
        return false;
    }
}