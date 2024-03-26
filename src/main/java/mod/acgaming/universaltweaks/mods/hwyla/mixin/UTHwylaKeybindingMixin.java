package mod.acgaming.universaltweaks.mods.hwyla.mixin;

import net.minecraft.client.settings.GameSettings;

import mcp.mobius.waila.client.KeyEvent;
import mcp.mobius.waila.overlay.WailaTickHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = WailaTickHandler.class, remap = false)
public class UTHwylaKeybindingMixin
{
    @Redirect(method = "tickClient", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;isKeyDown(I)Z"))
    private static boolean utHwylaKeybinding(int key)
    {
        return GameSettings.isKeyDown(KeyEvent.key_show);
    }
}