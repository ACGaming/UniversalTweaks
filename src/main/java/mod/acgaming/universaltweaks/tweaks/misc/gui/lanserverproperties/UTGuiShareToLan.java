package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin.GuiShareToLanAccessor;
import mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin.PlayerListAccessor;

// Courtesy of rikka0w0
@SideOnly(Side.CLIENT)
public class UTGuiShareToLan extends GuiShareToLan
{
    public static final String ONLINE_MODE_LANG_KEY = "btn.universaltweaks.lanserverproperties.online_mode";
    public static final String ONLINE_MODE_LANG_KEY_DESC = "btn.universaltweaks.lanserverproperties.online_mode_desc";
    public static final String SPAWN_ANIMALS_LANG_KEY = "btn.universaltweaks.lanserverproperties.spawn_animals";
    public static final String SPAWN_NPCS_LANG_KEY = "btn.universaltweaks.lanserverproperties.spawn_npcs";
    public static final String ALLOW_PVP_LANG_KEY = "btn.universaltweaks.lanserverproperties.allow_pvp";
    public static final String ALLOW_FLIGHT_LANG_KEY = "btn.universaltweaks.lanserverproperties.allow_flight";
    public static final String PORT_LANG_KEY = "btn.universaltweaks.lanserverproperties.port";
    public static final String MAX_PLAYERS_LANG_KEY = "btn.universaltweaks.lanserverproperties.max_players";

    public static int validatePort(String text)
    {
        boolean valid = true;
        int port = -1;
        try
        {
            if (!text.isEmpty())
            {
                port = Integer.parseInt(text);
                if (port < 0 || port > 65535) valid = false;
            }
        }
        catch (NumberFormatException e)
        {
            valid = false;
        }

        return valid ? port : -1;
    }

    protected final GuiScreen lastScreen;
    protected GuiTextField portTextField = null;
    protected GuiTextField maxPlayersTextField = null;
    protected GuiButton onlineModeButton = null;
    protected GuiButton spawnAnimalsButton = null;
    protected GuiButton spawnNpcsButton = null;
    protected GuiButton allowPvpButton = null;
    protected GuiButton allowFlightButton = null;
    protected boolean onlineMode = true;
    protected boolean spawnAnimals = true;
    protected boolean spawnNpcs = true;
    protected boolean allowPvp = true;
    protected boolean allowFlight = true;

    public UTGuiShareToLan(GuiScreen lastScreenIn)
    {
        super(lastScreenIn);
        this.lastScreen = lastScreenIn;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        // Attempt to locate the old button
        GuiButton button = null;
        String msg = I18n.format("lanServer.start");
        for (GuiButton widget : this.buttonList)
        {
            if (widget.displayString.equals(msg))
            {
                button = widget;
                break;
            }
        }

        if (button == null)
        {
            UniversalTweaks.LOGGER.info("UTGuiShareToLan ::: Unable to locate start server button!");
            // If we cannot find the "Start LAN Server" button
            // just leave everything else there
            return;
        }

        // Add our own widgets
        // Toggle button for Online Mode
        this.onlineModeButton = new GuiButton(233, this.width / 2 - 155, 124, 150, 20, getOnlineButtonText());
        this.addButton(this.onlineModeButton);

        // Toggle button for Spawn Animals
        this.spawnAnimalsButton = new GuiButton(234, this.width / 2 + 5, 124, 150, 20, getSpawnAnimalsButtonText());
        this.addButton(this.spawnAnimalsButton);

        // Toggle button for Spawn NPCs
        this.spawnNpcsButton = new GuiButton(235, this.width / 2 + 5, 148, 150, 20, getSpawnNpcsButtonText());
        this.addButton(this.spawnNpcsButton);

        // Toggle button for Allow PVP
        this.allowPvpButton = new GuiButton(236, this.width / 2 + 5, 172, 150, 20, getAllowPvpButtonText());
        this.addButton(this.allowPvpButton);

        // Toggle button for Allow Flight
        this.allowFlightButton = new GuiButton(237, this.width / 2 + 5, 196, 150, 20, getAllowFlightButtonText());
        this.addButton(this.allowFlightButton);

        // Text field for Port
        this.portTextField = new GuiTextField(238, this.fontRenderer, this.width / 2 - 154, this.height - 54, 148, 20);
        this.portTextField.setText("25565");

        // Text field for Max Players
        this.maxPlayersTextField = new GuiTextField(239, this.fontRenderer, this.width / 2 + 6, this.height - 54, 148, 20);
        this.maxPlayersTextField.setText("4");
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 101)
        {
            serverStartButtonClick(button);
        }
        else if (button.id == 233)
        {
            this.onlineMode = !this.onlineMode;
            button.displayString = this.getOnlineButtonText();
        }
        else if (button.id == 234)
        {
            this.spawnAnimals = !this.spawnAnimals;
            button.displayString = this.getSpawnAnimalsButtonText();
        }
        else if (button.id == 235)
        {
            this.spawnNpcs = !this.spawnNpcs;
            button.displayString = this.getSpawnNpcsButtonText();
        }
        else if (button.id == 236)
        {
            this.allowPvp = !this.allowPvp;
            button.displayString = this.getAllowPvpButtonText();
        }
        else if (button.id == 237)
        {
            this.allowFlight = !this.allowFlight;
            button.displayString = this.getAllowFlightButtonText();
        }
        else
        {
            super.actionPerformed(button);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        this.drawString(this.fontRenderer, I18n.format(PORT_LANG_KEY), this.width / 2 - 155, this.height - 66, 10526880);
        this.portTextField.drawTextBox();

        this.drawString(this.fontRenderer, I18n.format(MAX_PLAYERS_LANG_KEY), this.width / 2 + 5, this.height - 66, 10526880);
        this.maxPlayersTextField.drawTextBox();

        if (this.onlineModeButton.isMouseOver()) this.drawHoveringText(I18n.format(ONLINE_MODE_LANG_KEY_DESC), mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.portTextField.textboxKeyTyped(typedChar, keyCode))
        {
            // Check the format, make sure the text is a valid integer
            this.portTextField.setTextColor(validatePort(this.portTextField.getText()) >= 0 ? 0xFFFFFF : 0xFF0000);
        }
        else if (this.maxPlayersTextField.textboxKeyTyped(typedChar, keyCode))
        {
            // Check the format, make sure the text is a valid integer
            this.maxPlayersTextField.setTextColor(validatePort(this.maxPlayersTextField.getText()) >= 0 ? 0xFFFFFF : 0xFF0000);
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.portTextField.mouseClicked(mouseX, mouseY, mouseButton);
        this.maxPlayersTextField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    private void serverStartButtonClick(GuiButton button) throws IOException
    {
        this.mc.displayGuiScreen(null);

        String portStr = portTextField.getText();
        int port = !portStr.isEmpty() ? Integer.parseInt(portStr) : 25565;

        String maxPlayersStr = maxPlayersTextField.getText();
        int maxPlayers = !maxPlayersStr.isEmpty() ? Integer.parseInt(maxPlayersStr) : 4;

        String gameMode = ((GuiShareToLanAccessor) this).getGameMode();
        boolean allowCheats = ((GuiShareToLanAccessor) this).getAllowCheats();

        ITextComponent textComponent;
        String newPort = this.mc.getIntegratedServer().shareToLAN(GameType.getByName(gameMode), allowCheats);
        if (newPort != null)
        {
            this.mc.getIntegratedServer().getNetworkSystem().addEndpoint(null, port);
            textComponent = new TextComponentTranslation("commands.publish.started", newPort + ", " + port);
            this.mc.getIntegratedServer().setOnlineMode(onlineMode);
            this.mc.getIntegratedServer().setCanSpawnAnimals(spawnAnimals);
            this.mc.getIntegratedServer().setCanSpawnNPCs(spawnNpcs);
            this.mc.getIntegratedServer().setAllowPvp(allowPvp);
            this.mc.getIntegratedServer().setAllowFlight(allowFlight);
            ((PlayerListAccessor) this.mc.getIntegratedServer().getPlayerList()).setMaxPlayers(maxPlayers);
        }
        else
        {
            textComponent = new TextComponentString("commands.publish.failed");
        }
        this.mc.ingameGUI.getChatGUI().printChatMessage(textComponent);
    }

    private String getOnlineButtonText()
    {
        return I18n.format(ONLINE_MODE_LANG_KEY) + ": " + I18n.format(this.onlineMode ? "options.on" : "options.off");
    }

    private String getSpawnAnimalsButtonText()
    {
        return I18n.format(SPAWN_ANIMALS_LANG_KEY) + ": " + I18n.format(this.spawnAnimals ? "options.on" : "options.off");
    }

    private String getSpawnNpcsButtonText()
    {
        return I18n.format(SPAWN_NPCS_LANG_KEY) + ": " + I18n.format(this.spawnNpcs ? "options.on" : "options.off");
    }

    private String getAllowPvpButtonText()
    {
        return I18n.format(ALLOW_PVP_LANG_KEY) + ": " + I18n.format(this.allowPvp ? "options.on" : "options.off");
    }

    private String getAllowFlightButtonText()
    {
        return I18n.format(ALLOW_FLIGHT_LANG_KEY) + ": " + I18n.format(this.allowFlight ? "options.on" : "options.off");
    }
}