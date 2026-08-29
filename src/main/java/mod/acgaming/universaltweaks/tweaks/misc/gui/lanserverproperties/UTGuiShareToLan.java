package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin.PlayerListAccessor;

// Courtesy of rikka0w0
@SideOnly(Side.CLIENT)
public class UTGuiShareToLan extends GuiScreen
{
    public static final String NBT_TAG = "LANServerProperties";

    protected static final int PORT_MIN = 1024;
    protected static final int PORT_MAX = 65535;
    protected static final int MAX_PLAYERS_MIN = 1;
    protected static final int MAX_PLAYERS_MAX = 65535;

    protected static final int ID_START = 101;
    protected static final int ID_CANCEL = 102;
    protected static final int ID_ALLOW_CHEATS = 103;
    protected static final int ID_GAME_MODE = 104;
    protected static final int ID_SPAWN_ANIMALS = 105;
    protected static final int ID_SPAWN_NPCS = 106;
    protected static final int ID_ALLOW_PVP = 107;
    protected static final int ID_ALLOW_FLIGHT = 108;
    protected static final int ID_ONLINE_MODE = 109;

    protected static final int WIDGET_WIDTH = 150;
    protected static final int WIDGET_HEIGHT = 20;

    protected final GuiScreen lastScreen;
    protected boolean settingsLoaded = false;

    protected GuiTextField portTextField = null;
    protected GuiTextField maxPlayersTextField = null;
    protected GuiButton gameModeButton = null;
    protected GuiButton allowCheatsButton = null;
    protected GuiButton onlineModeButton = null;
    protected GuiButton spawnAnimalsButton = null;
    protected GuiButton spawnNpcsButton = null;
    protected GuiButton allowPvpButton = null;
    protected GuiButton allowFlightButton = null;
    protected String gameMode = "survival";
    protected boolean allowCheats = false;
    protected boolean onlineMode = true;
    protected boolean spawnAnimals = true;
    protected boolean spawnNpcs = true;
    protected boolean allowPvp = true;
    protected boolean allowFlight = true;
    protected int port = 25565;
    protected int maxPlayers = 4;

    public UTGuiShareToLan(GuiScreen lastScreen)
    {
        this.lastScreen = lastScreen;
    }

    @Override
    public void initGui()
    {
        // Load saved settings or use defaults, but only once so a resize does not discard pending edits
        if (!this.settingsLoaded)
        {
            loadSavedSettings();
            this.settingsLoaded = true;
        }

        Keyboard.enableRepeatEvents(true);

        // Carry the field state over so a resize keeps the pending input and the focused field
        String portText = this.portTextField != null ? this.portTextField.getText() : String.valueOf(this.port);
        String maxPlayersText = this.maxPlayersTextField != null ? this.maxPlayersTextField.getText() : String.valueOf(this.maxPlayers);
        boolean maxPlayersFocused = this.maxPlayersTextField != null && this.maxPlayersTextField.isFocused();

        int leftX = this.width / 2 - 155;
        int rightX = this.width / 2 + 5;
        int centerX = this.width / 2 - WIDGET_WIDTH / 2;
        int rowY = 86;
        final int rowPitch = 24;

        this.gameModeButton = this.addButton(new GuiButton(ID_GAME_MODE, leftX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getGameModeButtonText()));
        this.allowCheatsButton = this.addButton(new GuiButton(ID_ALLOW_CHEATS, rightX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getAllowCheatsButtonText()));
        rowY += rowPitch;
        this.spawnAnimalsButton = this.addButton(new GuiButton(ID_SPAWN_ANIMALS, leftX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getSpawnAnimalsButtonText()));
        this.spawnNpcsButton = this.addButton(new GuiButton(ID_SPAWN_NPCS, rightX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getSpawnNpcsButtonText()));
        rowY += rowPitch;
        this.allowPvpButton = this.addButton(new GuiButton(ID_ALLOW_PVP, leftX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getAllowPvpButtonText()));
        this.allowFlightButton = this.addButton(new GuiButton(ID_ALLOW_FLIGHT, rightX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getAllowFlightButtonText()));
        rowY += rowPitch;
        this.onlineModeButton = this.addButton(new GuiButton(ID_ONLINE_MODE, centerX, rowY, WIDGET_WIDTH, WIDGET_HEIGHT, getOnlineModeButtonText()));

        int bottomY = this.height - 28;
        this.addButton(new GuiButton(ID_START, leftX, bottomY, WIDGET_WIDTH, WIDGET_HEIGHT, I18n.format("lanServer.start")));
        this.addButton(new GuiButton(ID_CANCEL, rightX, bottomY, WIDGET_WIDTH, WIDGET_HEIGHT, I18n.format("gui.cancel")));

        // Text fields are inset by one pixel so their border lines up with the button columns
        this.portTextField = createNumberField(leftX + 1, portText, PORT_MIN, PORT_MAX);
        this.maxPlayersTextField = createNumberField(rightX + 1, maxPlayersText, MAX_PLAYERS_MIN, MAX_PLAYERS_MAX);
        this.portTextField.setFocused(!maxPlayersFocused);
        this.maxPlayersTextField.setFocused(maxPlayersFocused);
    }

    @Override
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case ID_START:
                startServer();
                break;
            case ID_CANCEL:
                this.mc.displayGuiScreen(this.lastScreen);
                break;
            case ID_GAME_MODE:
                this.gameMode = nextGameMode(this.gameMode);
                button.displayString = getGameModeButtonText();
                break;
            case ID_ALLOW_CHEATS:
                this.allowCheats = !this.allowCheats;
                button.displayString = getAllowCheatsButtonText();
                break;
            case ID_SPAWN_ANIMALS:
                this.spawnAnimals = !this.spawnAnimals;
                button.displayString = getSpawnAnimalsButtonText();
                break;
            case ID_SPAWN_NPCS:
                this.spawnNpcs = !this.spawnNpcs;
                button.displayString = getSpawnNpcsButtonText();
                break;
            case ID_ALLOW_PVP:
                this.allowPvp = !this.allowPvp;
                button.displayString = getAllowPvpButtonText();
                break;
            case ID_ALLOW_FLIGHT:
                this.allowFlight = !this.allowFlight;
                button.displayString = getAllowFlightButtonText();
                break;
            case ID_ONLINE_MODE:
                this.onlineMode = !this.onlineMode;
                button.displayString = getOnlineModeButtonText();
                break;
            default:
                super.actionPerformed(button);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, I18n.format("lanServer.title"), this.width / 2, 50, 0xFFFFFF);
        this.drawCenteredString(this.fontRenderer, I18n.format("lanServer.otherPlayers"), this.width / 2, 66, 0xFFFFFF);

        final int labelY = 186;
        this.drawCenteredString(this.fontRenderer, I18n.format("btn.universaltweaks.lanserverproperties.port"), this.portTextField.x + this.portTextField.width / 2, labelY, 0xFFFFFF);
        this.portTextField.drawTextBox();

        this.drawCenteredString(this.fontRenderer, I18n.format("btn.universaltweaks.lanserverproperties.max_players"), this.maxPlayersTextField.x + this.maxPlayersTextField.width / 2, labelY, 0xFFFFFF);
        this.maxPlayersTextField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);

        // Buttons only know whether they are hovered after they have been drawn
        if (this.onlineModeButton.isMouseOver()) this.drawHoveringText(I18n.format("btn.universaltweaks.lanserverproperties.online_mode_desc"), mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.portTextField.textboxKeyTyped(typedChar, keyCode))
        {
            this.portTextField.setTextColor(parseBounded(this.portTextField.getText(), PORT_MIN, PORT_MAX) > 0 ? 0xFFFFFF : 0xFF0000);
        }
        else if (this.maxPlayersTextField.textboxKeyTyped(typedChar, keyCode))
        {
            this.maxPlayersTextField.setTextColor(parseBounded(this.maxPlayersTextField.getText(), MAX_PLAYERS_MIN, MAX_PLAYERS_MAX) > 0 ? 0xFFFFFF : 0xFF0000);
        }
        else if (keyCode == Keyboard.KEY_TAB)
        {
            boolean portFocused = this.portTextField.isFocused();
            this.portTextField.setFocused(!portFocused);
            this.maxPlayersTextField.setFocused(portFocused);
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

    @Override
    public void updateScreen()
    {
        super.updateScreen();
        this.portTextField.updateCursorCounter();
        this.maxPlayersTextField.updateCursorCounter();
    }

    protected GuiTextField createNumberField(int x, String text, int min, int max)
    {
        GuiTextField textField = new GuiTextField(0, this.fontRenderer, x, 198, WIDGET_WIDTH - 2, WIDGET_HEIGHT);
        textField.setMaxStringLength(5);
        textField.setValidator(input -> input == null || input.chars().allMatch(Character::isDigit));
        textField.setText(text);
        textField.setTextColor(parseBounded(textField.getText(), min, max) > 0 ? 0xFFFFFF : 0xFF0000);
        return textField;
    }

    protected static String nextGameMode(String current)
    {
        if ("survival".equals(current)) return "spectator";
        if ("spectator".equals(current)) return "creative";
        if ("creative".equals(current)) return "adventure";
        return "survival";
    }

    protected static int parseBounded(String text, int min, int max)
    {
        try
        {
            if (text.isEmpty()) return -1;
            int parsedInt = Integer.parseInt(text);
            return parsedInt >= min && parsedInt <= max ? parsedInt : -1;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }

    private void loadSavedSettings()
    {
        WorldServer worldServer = this.mc.getIntegratedServer().getWorld(0);
        //noinspection ConstantValue
        if (worldServer != null)
        {
            WorldInfo worldInfo = worldServer.getWorldInfo();
            NBTTagCompound nbt = worldInfo.getDimensionData(worldServer.provider.getDimension());
            NBTTagCompound customSettings = nbt.getCompoundTag(NBT_TAG);

            // Load values if they exist, otherwise keep defaults
            if (!customSettings.isEmpty())
            {
                if (customSettings.hasKey("GameMode")) this.gameMode = customSettings.getString("GameMode");
                if (customSettings.hasKey("AllowCheats")) this.allowCheats = customSettings.getBoolean("AllowCheats");
                if (customSettings.hasKey("OnlineMode")) this.onlineMode = customSettings.getBoolean("OnlineMode");
                if (customSettings.hasKey("SpawnAnimals")) this.spawnAnimals = customSettings.getBoolean("SpawnAnimals");
                if (customSettings.hasKey("SpawnNPCs")) this.spawnNpcs = customSettings.getBoolean("SpawnNPCs");
                if (customSettings.hasKey("AllowPvP")) this.allowPvp = customSettings.getBoolean("AllowPvP");
                if (customSettings.hasKey("AllowFlight")) this.allowFlight = customSettings.getBoolean("AllowFlight");
                if (customSettings.hasKey("Port")) this.port = customSettings.getInteger("Port");
                if (customSettings.hasKey("MaxPlayers")) this.maxPlayers = customSettings.getInteger("MaxPlayers");

                UniversalTweaks.LOGGER.debug("LAN Server Properties ::: Loaded LAN server settings from level.dat");
            }
            else
            {
                this.gameMode = worldInfo.getGameType().getName();
                this.allowCheats = worldInfo.areCommandsAllowed();

                UniversalTweaks.LOGGER.debug("LAN Server Properties ::: No saved LAN settings found, using defaults");
            }
        }
        else
        {
            UniversalTweaks.LOGGER.warn("LAN Server Properties ::: Failed to access WorldServer for loading settings, using defaults");
        }
    }

    private void startServer() throws IOException
    {
        this.mc.displayGuiScreen(null);

        int parsedPort = parseBounded(this.portTextField.getText(), PORT_MIN, PORT_MAX);
        if (parsedPort < 0) parsedPort = this.port;

        int parsedMaxPlayers = parseBounded(this.maxPlayersTextField.getText(), MAX_PLAYERS_MIN, MAX_PLAYERS_MAX);
        if (parsedMaxPlayers < 0) parsedMaxPlayers = this.maxPlayers;

        ITextComponent textComponent;
        IntegratedServer server = this.mc.getIntegratedServer();
        String newPort = server.shareToLAN(GameType.getByName(this.gameMode), this.allowCheats);
        //noinspection ConstantValue
        if (newPort != null)
        {
            server.getNetworkSystem().addEndpoint(null, parsedPort);
            textComponent = new TextComponentTranslation("commands.publish.started", newPort + ", " + parsedPort);

            // Apply settings to the running server
            server.setOnlineMode(onlineMode);
            server.setCanSpawnAnimals(spawnAnimals);
            server.setCanSpawnNPCs(spawnNpcs);
            server.setAllowPvp(allowPvp);
            server.setAllowFlight(allowFlight);
            ((PlayerListAccessor) server.getPlayerList()).setMaxPlayers(parsedMaxPlayers);

            // Save settings to level.dat
            WorldServer worldServer = this.mc.getIntegratedServer().getWorld(0);
            //noinspection ConstantValue
            if (worldServer != null)
            {
                WorldInfo worldInfo = worldServer.getWorldInfo();
                NBTTagCompound nbt = worldInfo.getDimensionData(worldServer.provider.getDimension());
                NBTTagCompound customSettings = nbt.getCompoundTag(NBT_TAG);
                if (customSettings.isEmpty())
                {
                    customSettings = new NBTTagCompound();
                    nbt.setTag(NBT_TAG, customSettings);
                }

                customSettings.setString("GameMode", gameMode);
                customSettings.setBoolean("AllowCheats", allowCheats);
                customSettings.setBoolean("OnlineMode", onlineMode);
                customSettings.setBoolean("SpawnAnimals", spawnAnimals);
                customSettings.setBoolean("SpawnNPCs", spawnNpcs);
                customSettings.setBoolean("AllowPvP", allowPvp);
                customSettings.setBoolean("AllowFlight", allowFlight);
                customSettings.setInteger("Port", parsedPort);
                customSettings.setInteger("MaxPlayers", parsedMaxPlayers);

                worldInfo.setDimensionData(worldServer.provider.getDimension(), nbt);

                UniversalTweaks.LOGGER.info("LAN Server Properties ::: Saved LAN server settings to level.dat");
            }
            else
            {
                UniversalTweaks.LOGGER.error("LAN Server Properties ::: Failed to access WorldServer for saving settings");
            }
        }
        else
        {
            textComponent = new TextComponentString("commands.publish.failed");
        }
        this.mc.ingameGUI.getChatGUI().printChatMessage(textComponent);
    }

    protected String getGameModeButtonText()
    {
        return I18n.format("btn.universaltweaks.lanserverproperties.game_mode.c") + " " + I18n.format("selectWorld.gameMode." + this.gameMode);
    }

    protected String getAllowCheatsButtonText()
    {
        return toggleText("selectWorld.allowCommands", this.allowCheats);
    }

    protected String getOnlineModeButtonText()
    {
        return toggleText("btn.universaltweaks.lanserverproperties.online_mode.c", this.onlineMode);
    }

    protected String getSpawnAnimalsButtonText()
    {
        return toggleText("btn.universaltweaks.lanserverproperties.spawn_animals.c", this.spawnAnimals);
    }

    protected String getSpawnNpcsButtonText()
    {
        return toggleText("btn.universaltweaks.lanserverproperties.spawn_npcs.c", this.spawnNpcs);
    }

    protected String getAllowPvpButtonText()
    {
        return toggleText("btn.universaltweaks.lanserverproperties.allow_pvp.c", this.allowPvp);
    }

    protected String getAllowFlightButtonText()
    {
        return toggleText("btn.universaltweaks.lanserverproperties.allow_flight.c", this.allowFlight);
    }

    protected static String toggleText(String langKey, boolean value)
    {
        return I18n.format(langKey) + " " + I18n.format(value ? "options.on" : "options.off");
    }
}