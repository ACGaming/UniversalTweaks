package mod.acgaming.universaltweaks.mods.enderutilities.mixin;

import fi.dy.masa.enderutilities.gui.client.base.GuiEnderUtilities;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiEnderUtilities.class, remap = false)
public abstract class UTGuiEnderUtilitiesMixin
{
    /**
     * @reason Stop processing slot hotkeys after the GUI has been closed.
     */
    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true, require = 0, remap = true)
    private void utStopKeyHandlingAfterClose(char typedChar, int keyCode, CallbackInfo ci)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (keyCode == Keyboard.KEY_ESCAPE || mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode))
        {
            mc.player.closeScreen();
            ci.cancel();
        }
    }
}