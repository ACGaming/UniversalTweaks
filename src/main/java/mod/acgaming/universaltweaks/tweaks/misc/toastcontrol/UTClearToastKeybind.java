package mod.acgaming.universaltweaks.tweaks.misc.toastcontrol;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

public class UTClearToastKeybind extends UTKeybindings.Key
{
    public UTClearToastKeybind()
    {
        super("clear_toasts", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, Keyboard.KEY_0);
    }

    public static void createKeybind()
    {
        if (UTConfigTweaks.MISC.TOAST_CONTROL.utClearToastKeybind) UTKeybindings.addKey(new UTClearToastKeybind());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleKeybind()
    {
        Minecraft.getMinecraft().getToastGui().clear();
    }
}
