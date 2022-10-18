package mod.acgaming.hkntweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.hkntweaks.HkNTweaks;

@Config(modid = HkNTweaks.MODID, name = "HkNTweaks")
public class HkNTweaksConfig
{
    @Config.Name("Bugfixes")
    @Config.Comment("Bugfixes Category")
    public static BugfixesCategory bugfixes = new BugfixesCategory();

    @Config.Name("Tweaks")
    @Config.Comment("Tweaks Category")
    public static TweaksCategory tweaks = new TweaksCategory();

    public static class BugfixesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Block Overlay Fix")
        @Config.Comment("Fixes x-ray when standing in non-suffocating blocks")
        public boolean hknBlockOverlayToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Dimension Change Player States Fix")
        @Config.Comment("Fixes missing player states when changing dimensions by sending additional packets")
        public boolean hknDimensionChangeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Bounding Box Fix")
        @Config.Comment("Saves entity bounding boxes to tags to prevent breakouts and suffocation")
        public boolean hknEntityAABBToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Ladder Flying Slowdown Fix")
        @Config.Comment("Disables climbing movement when flying")
        public boolean hknLadderFlyingToggle = true;

        @Config.RequiresWorldRestart
        @Config.Name("Max Health Fix")
        @Config.Comment("Saves increased player health to tags")
        public boolean hknMaxHealthToggle = true;

        @Config.Name("Mending Fix")
        @Config.Comment("Only repairs damaged equipment with XP")
        public boolean hknMendingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Piston Progress Fix")
        @Config.Comment("Properly saves the last state of pistons to tags")
        public boolean hknPistonTileToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Skeleton Aim Fix")
        @Config.Comment("Fixes skeletons not looking at their targets when strafing")
        public boolean hknSkeletonAimToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Tile Entity Update Order Fix")
        @Config.Comment("Keeps the order of tile entities on chunk load")
        public boolean hknTELoadOrderToggle = true;
    }

    public static class TweaksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Attributes Uncap")
        @Config.Comment("Virtually uncaps entity attributes")
        public boolean hknAttributesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Auto Jump Replacement")
        @Config.Comment("Replaces auto jump with an increased step height")
        public boolean hknAutoJumpToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Bed Obstruction Replacement")
        @Config.Comment("Replaces bed obstruction checks with an improved version")
        public boolean hknBedObstructionToggle = true;

        @Config.Name("Bow Infinity Remedy")
        @Config.Comment("Bows enchanted with Infinity no longer require arrows")
        public boolean hknBowInfinityToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Water Fall Damage")
        @Config.Comment("Re-implements an improved version of pre-1.4 fall damage in water")
        public boolean hknFallDamageToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Water Fall Damage Reduction")
        @Config.Comment("How much fall damage gets reduced by water per tick")
        public float hknFallDamageValue = 2F;

        @Config.RequiresMcRestart
        @Config.Name("Mob Despawn Improvement")
        @Config.Comment("Mobs carrying picked up items will drop their equipment and despawn properly")
        public boolean hknMobDespawnToggle = true;

        @Config.Name("Offhand Improvement")
        @Config.Comment("Prevents placing offhand blocks when blocks or food are held in the mainhand")
        public boolean hknOffhandToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Stronghold Replacement")
        @Config.Comment("Replaces stronghold generation with a safer variant")
        public boolean hknStrongholdToggle = true;
    }

    @Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(HkNTweaks.MODID))
            {
                ConfigManager.sync(HkNTweaks.MODID, Config.Type.INSTANCE);
                HkNTweaks.LOGGER.info("HkN Tweaks config reloaded");
            }
        }
    }
}