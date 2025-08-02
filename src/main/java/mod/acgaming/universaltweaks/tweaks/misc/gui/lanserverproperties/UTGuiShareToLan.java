package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.GameType;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
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
    public static final String NBT_TAG = "LANServerProperties";

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
    protected int port = 25565;
    protected int maxPlayers = 4;

    public UTGuiShareToLan(GuiScreen lastScreen)
    {
        super(lastScreen);
        this.lastScreen = lastScreen;
    }

    @Override
    public void initGui()
    {
        // Load saved settings or use defaults
        loadSavedSettings();

        // Initialize the default GUI
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
            UniversalTweaks.LOGGER.info("LAN Server Properties ::: Unable to locate start server button!");
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
        this.portTextField.setText(String.valueOf(port));

        // Text field for Max Players
        this.maxPlayersTextField = new GuiTextField(239, this.fontRenderer, this.width / 2 + 6, this.height - 54, 148, 20);
        this.maxPlayersTextField.setText(String.valueOf(maxPlayers));
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
            this.portTextField.setTextColor(validateInt(this.portTextField.getText()) > 0 ? 0xFFFFFF : 0xFF0000);
        }
        else if (this.maxPlayersTextField.textboxKeyTyped(typedChar, keyCode))
        {
            // Check the format, make sure the text is a valid integer
            this.maxPlayersTextField.setTextColor(validateInt(this.maxPlayersTextField.getText()) > 0 ? 0xFFFFFF : 0xFF0000);
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

    private int validateInt(String text)
    {
        boolean valid = true;
        int parsedInt = -1;
        try
        {
            if (!text.isEmpty())
            {
                parsedInt = Integer.parseInt(text);
                if (parsedInt <= 0 || parsedInt > 65535) valid = false;
            }
        }
        catch (NumberFormatException e)
        {
            valid = false;
        }
        return valid ? parsedInt : -1;
    }

    private void loadSavedSettings()
    {
        WorldServer worldServer = this.mc.getIntegratedServer().getWorld(0);
        if (worldServer != null)
        {
            WorldInfo worldInfo = worldServer.getWorldInfo();
            NBTTagCompound nbt = worldInfo.getDimensionData(worldServer.provider.getDimension());
            NBTTagCompound customSettings = nbt.getCompoundTag(NBT_TAG);

            // Load values if they exist, otherwise keep defaults
            if (!customSettings.isEmpty())
            {
                if (customSettings.hasKey("GameMode")) ((GuiShareToLanAccessor) this).setGameMode(customSettings.getString("GameMode"));
                if (customSettings.hasKey("AllowCheats")) ((GuiShareToLanAccessor) this).setAllowCheats(customSettings.getBoolean("AllowCheats"));
                if (customSettings.hasKey("OnlineMode")) this.onlineMode = customSettings.getBoolean("OnlineMode");
                if (customSettings.hasKey("SpawnAnimals")) this.spawnAnimals = customSettings.getBoolean("SpawnAnimals");
                if (customSettings.hasKey("SpawnNPCs")) this.spawnNpcs = customSettings.getBoolean("SpawnNPCs");
                if (customSettings.hasKey("AllowPvP")) this.allowPvp = customSettings.getBoolean("AllowPvP");
                if (customSettings.hasKey("AllowFlight")) this.allowFlight = customSettings.getBoolean("AllowFlight");
                if (customSettings.hasKey("Port")) this.port = customSettings.getInteger("Port");
                if (customSettings.hasKey("MaxPlayers")) this.maxPlayers = customSettings.getInteger("MaxPlayers");

                UniversalTweaks.LOGGER.info("LAN Server Properties ::: Loaded LAN server settings from level.dat");
            }
            else
            {
                ((GuiShareToLanAccessor) this).setGameMode(worldInfo.getGameType().getName());
                ((GuiShareToLanAccessor) this).setAllowCheats(worldInfo.areCommandsAllowed());

                UniversalTweaks.LOGGER.info("LAN Server Properties ::: No saved LAN settings found, using defaults");
            }
        }
        else
        {
            UniversalTweaks.LOGGER.error("LAN Server Properties ::: Failed to access WorldServer for loading settings, using defaults");
        }
    }

    private void serverStartButtonClick(GuiButton button) throws IOException
    {
        this.mc.displayGuiScreen(null);

        String portStr = portTextField.getText();
        int parsedPort = !portStr.isEmpty() ? validateInt(portStr) : this.port;

        String maxPlayersStr = maxPlayersTextField.getText();
        int parsedMaxPlayers = !maxPlayersStr.isEmpty() ? validateInt(maxPlayersStr) : this.maxPlayers;

        String gameMode = ((GuiShareToLanAccessor) this).getGameMode();
        boolean allowCheats = ((GuiShareToLanAccessor) this).getAllowCheats();

        ITextComponent textComponent;
        String newPort = this.mc.getIntegratedServer().shareToLAN(GameType.getByName(gameMode), allowCheats);
        if (newPort != null)
        {
            this.mc.getIntegratedServer().getNetworkSystem().addEndpoint(null, parsedPort);
            textComponent = new TextComponentTranslation("commands.publish.started", newPort + ", " + parsedPort);

            // Apply settings to the running server
            this.mc.getIntegratedServer().setOnlineMode(onlineMode);
            this.mc.getIntegratedServer().setCanSpawnAnimals(spawnAnimals);
            this.mc.getIntegratedServer().setCanSpawnNPCs(spawnNpcs);
            this.mc.getIntegratedServer().setAllowPvp(allowPvp);
            this.mc.getIntegratedServer().setAllowFlight(allowFlight);
            ((PlayerListAccessor) this.mc.getIntegratedServer().getPlayerList()).setMaxPlayers(parsedMaxPlayers);

            // Save settings to level.dat
            WorldServer worldServer = this.mc.getIntegratedServer().getWorld(0);
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