package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTKeybindings;

@SideOnly(Side.CLIENT)
public class UTClearToastKeybind extends UTKeybindings.Key
{
    public static void createKeybind()
    {
        if (UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle && UTConfigTweaks.MISC.TOAST_CONTROL.utClearToastKeybind) UTKeybindings.addKey(new UTClearToastKeybind());
    }

    public UTClearToastKeybind()
    {
        super("clear_toasts", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleKeybind()
    {
        Minecraft.getMinecraft().getToastGui().clear();
    }
}
