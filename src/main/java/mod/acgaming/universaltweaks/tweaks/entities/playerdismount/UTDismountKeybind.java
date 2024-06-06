package mod.acgaming.universaltweaks.tweaks.entities.playerdismount;

import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTKeybindings;

@SideOnly(Side.CLIENT)
public class UTDismountKeybind extends UTKeybindings.Key
{
    public static UTKeybindings.Key key;

    public UTDismountKeybind()
    {
        super("dismount", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_LSHIFT);
    }

    public static void createKeybind()
    {
        if (UTConfigTweaks.MISC.utUseSeparateDismountKey)
        {
            key = new UTDismountKeybind();
            UTKeybindings.addKey(key);
        }
    }

    /**
     * Logic handled in {@link mod.acgaming.universaltweaks.tweaks.entities.playerdismount.mixin.UTEntityPlayerSPMixin}
     * due to checking if the key is held, not having a specific thing be run on the key being pressed.
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void handleKeybind()
    {
        // Handled in UTEntityPlayerSPMixin, see javadoc
    }
}
