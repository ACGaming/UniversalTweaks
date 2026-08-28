package mod.acgaming.universaltweaks.tweaks.misc.buttons.cheats;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Courtesy of modmuss50
public class UTToggleCheats
{
    private static final int ID = 101;

    @SubscribeEvent
    public static void utToggleCheatsInitGUI(GuiScreenEvent.InitGuiEvent.Post event)
    {
        if (!isEscMenuSP(event.getGui())) return;
        // Tweak Open To LAN width
        for (GuiButton button : event.getButtonList())
        {
            if (button.id == 7)
            {
                button.width = 98;
                break;
            }
        }
        GuiScreen gui = event.getGui();
        GuiButton toggleCheats = new GuiButton(ID, gui.width / 2 + 2, gui.height / 4 + 56, 98, 20, toggleText(areCheatsEnabled()));
        event.getButtonList().add(toggleCheats);
    }

    @SubscribeEvent
    public static void utToggleCheatsActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event)
    {
        if (!isEscMenuSP(event.getGui())) return;
        GuiButton button = event.getButton();
        if (button.id == ID)
        {
            toggleCheats(!areCheatsEnabled());
            boolean enabled = areCheatsEnabled();
            button.displayString = toggleText(enabled);
            Minecraft.getMinecraft().player.sendStatusMessage(enabled ? new TextComponentTranslation("msg.universaltweaks.cheats.enabled") : new TextComponentTranslation("msg.universaltweaks.cheats.disabled"), true);
        }
    }

    private static void toggleCheats(boolean enabled)
    {
        IntegratedServer integratedServer = Minecraft.getMinecraft().getIntegratedServer();
        if (integratedServer != null)
        {
            integratedServer.getPlayerList().setCommandsAllowedForAll(enabled);
            Minecraft.getMinecraft().player.setPermissionLevel(enabled ? 4 : 0);
            integratedServer.worlds[0].getWorldInfo().setAllowCommands(enabled);
        }
    }

    private static boolean areCheatsEnabled()
    {
        return Minecraft.getMinecraft().player.getPermissionLevel() == 4;
    }

    private static boolean isEscMenuSP(GuiScreen guiScreen)
    {
        if (guiScreen == null || !guiScreen.mc.isSingleplayer()) return false;
        return guiScreen instanceof GuiIngameMenu;
    }

    private static String toggleText(boolean value)
    {
        return I18n.format("btn.universaltweaks.cheats") + " " + I18n.format(value ? "options.on" : "options.off");
    }
}