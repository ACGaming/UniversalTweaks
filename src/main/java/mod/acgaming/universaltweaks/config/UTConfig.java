package mod.acgaming.universaltweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;

@Config(modid = UniversalTweaks.MODID, name = "UniversalTweaks")
public class UTConfig
{
    @Config.Name("Bugfixes")
    @Config.Comment("Bugfixes Category")
    public static BugfixesCategory bugfixes = new BugfixesCategory();

    @Config.Name("Debug")
    @Config.Comment("Debug Category")
    public static DebugCategory debug = new DebugCategory();

    @Config.Name("Tweaks")
    @Config.Comment("Tweaks Category")
    public static TweaksCategory tweaks = new TweaksCategory();

    public static class BugfixesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Block Overlay Fix")
        @Config.Comment("Fixes x-ray when standing in non-suffocating blocks")
        public boolean utBlockOverlayToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Destroy Entity Packets Fix")
        @Config.Comment("Fixes lag caused by dead entities by sending additional packets when the player is not alive")
        public boolean utDestroyPacketToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Dimension Change Player States Fix")
        @Config.Comment("Fixes missing player states when changing dimensions by sending additional packets")
        public boolean utDimensionChangeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Bounding Box Fix")
        @Config.Comment("Saves entity bounding boxes to tags to prevent breakouts and suffocation")
        public boolean utEntityAABBToggle = true;

        @Config.RequiresWorldRestart
        @Config.Name("Help Fix")
        @Config.Comment("Replaces the help command, sorts and reports broken commands")
        public boolean utHelpToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Ladder Flying Slowdown Fix")
        @Config.Comment("Disables climbing movement when flying")
        public boolean utLadderFlyingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Locale Fix")
        @Config.Comment("Prevents various crashes with Turkish locale")
        public boolean utLocaleToggle = true;

        @Config.RequiresWorldRestart
        @Config.Name("Max Health Fix")
        @Config.Comment("Saves increased player health to tags")
        public boolean utMaxHealthToggle = true;

        @Config.Name("Mending Fix")
        @Config.Comment("Only repairs damaged equipment with XP")
        public boolean utMendingToggle = true;

        @Config.Name("Mining Glitch Fix")
        @Config.Comment("Avoids the need for multiple mining attempts by sending additional movement packets")
        public boolean utMiningGlitchToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Piston Progress Fix")
        @Config.Comment("Properly saves the last state of pistons to tags")
        public boolean utPistonTileToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Skeleton Aim Fix")
        @Config.Comment("Fixes skeletons not looking at their targets when strafing")
        public boolean utSkeletonAimToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Tile Entity Update Order Fix")
        @Config.Comment("Keeps the order of tile entities on chunk load")
        public boolean utTELoadOrderToggle = true;
    }

    public static class DebugCategory
    {
        @Config.Name("Debug logging")
        @Config.Comment("Enables debug logging")
        public boolean utDebugToggle = false;
    }

    public static class TweaksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Attributes Uncap")
        @Config.Comment("Virtually uncaps entity attributes")
        public boolean utAttributesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Auto Jump Replacement")
        @Config.Comment("Replaces auto jump with an increased step height")
        public boolean utAutoJumpToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Bed Obstruction Replacement")
        @Config.Comment("Replaces bed obstruction checks with an improved version")
        public boolean utBedObstructionToggle = true;

        @Config.Name("Bow Infinity Remedy")
        @Config.Comment("Bows enchanted with Infinity no longer require arrows")
        public boolean utBowInfinityToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Animated Models")
        @Config.Comment("Improves model load times by removing Forge's animated models")
        public boolean utDisableAnimatedModelsToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Audio Debug")
        @Config.Comment("Improves loading times by removing debug code for missing sounds and subtitles")
        public boolean utDisableAudioDebugToggle = true;

        @Config.Name("Fast Leaf Decay")
        @Config.Comment("Makes leaves decay faster when trees are chopped")
        public boolean utLeafDecayToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Water Fall Damage")
        @Config.Comment("Re-implements an improved version of pre-1.4 fall damage in water")
        public boolean utFallDamageToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Water Fall Damage Reduction")
        @Config.Comment("How much fall damage gets reduced by water per tick")
        public float utFallDamageValue = 2F;

        @Config.RequiresMcRestart
        @Config.Name("Mob Despawn Improvement")
        @Config.Comment("Mobs carrying picked up items will drop their equipment and despawn properly")
        public boolean utMobDespawnToggle = true;

        @Config.Name("Offhand Improvement")
        @Config.Comment("Prevents placing offhand blocks when blocks or food are held in the mainhand")
        public boolean utOffhandToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Stronghold Replacement")
        @Config.Comment("Replaces stronghold generation with a safer variant")
        public boolean utStrongholdToggle = true;
    }

    @Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(UniversalTweaks.MODID))
            {
                ConfigManager.sync(UniversalTweaks.MODID, Config.Type.INSTANCE);
                UniversalTweaks.LOGGER.info("Universal Tweaks config reloaded");
            }
        }
    }
}