package mod.acgaming.universaltweaks.tweaks.misc.narrator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.ScreenChatOptions;
import net.minecraft.client.settings.GameSettings;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTKeybindings;

@SideOnly(Side.CLIENT)
public class UTNarratorKeybind extends UTKeybindings.Key
{
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final IKeyConflictContext CONTEXT = new IKeyConflictContext()
    {
        public boolean isActive()
        {
            return !(mc.currentScreen instanceof GuiControls);
        }

        public boolean conflicts(IKeyConflictContext other)
        {
            return false;
        }
    };

    public UTNarratorKeybind()
    {
        super("narrator", CONTEXT, KeyModifier.CONTROL, Keyboard.KEY_B);
    }

    public static void createKeybind()
    {
        if (!UTConfigTweaks.MISC.utDisableNarratorToggle && UTConfigTweaks.MISC.utUseCustomNarratorKeybind)
        {
            UTKeybindings.addKey(new UTNarratorKeybind());
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void handleKeybind()
    {
        mc.gameSettings.setOptionValue(GameSettings.Options.NARRATOR, 1);
        if (mc.currentScreen instanceof ScreenChatOptions)
        {
            ((ScreenChatOptions) mc.currentScreen).updateNarratorButton();
        }
    }
}
