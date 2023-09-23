package mod.acgaming.universaltweaks.tweaks.misc.buttons.cheats;

import java.util.List;
import java.util.Optional;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of modmuss50
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTToggleCheats
{
    public static GuiButton cheatsButton;

    @SubscribeEvent
    public static void utToggleCheatsInitGUI(GuiScreenEvent.InitGuiEvent event)
    {
        if (!UTConfigTweaks.MISC.utToggleCheatsToggle || !isEscMenuSP(event.getGui())) return;
        GuiScreen gui = event.getGui();
        Optional<GuiButton> optionalButton = getButton(event.getButtonList(), 7);
        if (!optionalButton.isPresent()) return;
        GuiButton openToLAN = optionalButton.get();
        openToLAN.width = 98;
        GuiButton toggleCheats = new GuiButton(101, gui.width / 2 + 2, gui.height / 4 + 56, 98, 20, areCheatsEnabled() ? new TextComponentTranslation("btn.universaltweaks.cheats.enabled").getFormattedText() : new TextComponentTranslation("btn.universaltweaks.cheats.disabled").getFormattedText());
        cheatsButton = toggleCheats;
        event.getButtonList().add(toggleCheats);
    }

    @SubscribeEvent
    public static void utToggleCheatsActionPerformed(GuiScreenEvent.ActionPerformedEvent.Post event)
    {
        if (!UTConfigTweaks.MISC.utToggleCheatsToggle || !isEscMenuSP(event.getGui())) return;
        if (event.getButton().id == 101)
        {
            toggleCheats(!areCheatsEnabled());
            cheatsButton.displayString = areCheatsEnabled() ? new TextComponentTranslation("btn.universaltweaks.cheats.enabled").getFormattedText() : new TextComponentTranslation("btn.universaltweaks.cheats.disabled").getFormattedText();
            event.getGui().mc.player.sendStatusMessage(areCheatsEnabled() ? new TextComponentTranslation("msg.universaltweaks.cheats.enabled") : new TextComponentTranslation("msg.universaltweaks.cheats.disabled"), true);
        }
    }

    public static void toggleCheats(boolean enabled)
    {
        IntegratedServer integratedServer = Minecraft.getMinecraft().getIntegratedServer();
        if (integratedServer != null)
        {
            integratedServer.getPlayerList().setCommandsAllowedForAll(enabled);
            Minecraft.getMinecraft().player.setPermissionLevel(enabled ? 4 : 0);
            integratedServer.worlds[0].getWorldInfo().setAllowCommands(enabled);
        }
    }

    public static boolean areCheatsEnabled()
    {
        return Minecraft.getMinecraft().player.getPermissionLevel() == 4;
    }

    public static Optional<GuiButton> getButton(List<GuiButton> buttonList, int id)
    {
        return buttonList.stream().filter(guiButton -> guiButton.id == id).findFirst();
    }

    public static boolean isEscMenuSP(GuiScreen guiScreen)
    {
        if (guiScreen == null || !guiScreen.mc.isSingleplayer()) return false;
        return guiScreen instanceof GuiIngameMenu;
    }
}