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
    public static BugfixesCategory bugfixes = new BugfixesCategory();

    @Config.Name("Tweaks")
    public static TweaksCategory tweaks = new TweaksCategory();

    public static class BugfixesCategory
    {

    }

    public static class TweaksCategory
    {
        @Config.Name("Auto Jump Toggle")
        @Config.Comment("")
        public boolean hknAutoJumpToggle = true;

        @Config.Name("Bed Obstruction Toggle")
        @Config.Comment("")
        public boolean hknBedObstructionToggle = true;

        @Config.Name("Bow Infinity Toggle")
        @Config.Comment("")
        public boolean hknBowInfinityToggle = true;

        @Config.Name("Water Fall Damage Toggle")
        @Config.Comment("")
        public boolean hknFallDamageToggle = false;

        @Config.Name("Water Fall Damage Reduction")
        @Config.Comment("How much fall damage gets reduced by water per tick")
        public float hknFallDamageSubtrahend = 2F;

        @Config.Name("Mob Despawn Toggle")
        @Config.Comment("")
        public boolean hknMobDespawnToggle = true;

        @Config.Name("Offhand Toggle")
        @Config.Comment("")
        public boolean hknOffhandToggle = true;
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