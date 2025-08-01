package mod.acgaming.universaltweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.util.compat.UTObsoleteModsHandler;

@Config(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME + " - General")
public class UTConfigGeneral
{
    @Config.LangKey("cfg.universaltweaks.config.debug")
    @Config.Name("Debug")
    public static final DebugCategory DEBUG = new DebugCategory();

    @Config.LangKey("cfg.universaltweaks.config.masterswitches")
    @Config.Name("Master Switches")
    public static final MasterSwitchesCategory MASTER_SWITCHES = new MasterSwitchesCategory();

    public static class DebugCategory
    {
        @Config.Name("Bypass Incompatibility Warnings")
        @Config.Comment("For those who live life on the edge, may or may not include Jons")
        public boolean utBypassIncompatibilityToggle = false;

        @Config.Name("Debug Logging")
        @Config.Comment("Enables debug logging")
        public boolean utDebugToggle = false;

        @Config.Name("Show Loading Time")
        @Config.Comment("Prints the time the game needed to launch to the log")
        public boolean utLoadingTimeToggle = true;
    }

    public static class MasterSwitchesCategory
    {
        @Config.Name("Master Switch: Bugfixes")
        @Config.Comment
            ({
                "Enables the 'Bugfixes' module",
                "Turn off to disable all bugfix tweaks"
            })
        public boolean utMasterSwitchBugfixes = true;

        @Config.Name("Master Switch: Mod Integration")
        @Config.Comment
            ({
                "Enables the 'Mod Integration' module",
                "Turn off to disable all mod integration tweaks"
            })
        public boolean utMasterSwitchModIntegration = true;

        @Config.Name("Master Switch: Tweaks")
        @Config.Comment
            ({
                "Enables the 'Tweaks' module",
                "Turn off to disable all vanilla tweaks"
            })
        public boolean utMasterSwitchTweaks = true;
    }

    static
    {
        ConfigAnytime.register(UTConfigGeneral.class);
    }

    @Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(UniversalTweaks.MODID))
            {
                ConfigManager.sync(UniversalTweaks.MODID, Config.Type.INSTANCE);
                UTObsoleteModsHandler.setHasShownObsoleteMods(false);
            }
        }
    }
}