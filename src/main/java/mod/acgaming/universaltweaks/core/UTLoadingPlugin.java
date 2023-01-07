package mod.acgaming.universaltweaks.core;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigParser;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static final boolean isDev = FMLLaunchHandler.isDeobfuscatedEnvironment();
    public static final boolean firstLaunch = UTConfigParser.init();
    public static long launchTime;

    static
    {
        if (firstLaunch || UTConfigParser.isEnabled("B:\"Show Loading Time\"=true")) launchTime = System.currentTimeMillis();
        if ((firstLaunch || UTConfigParser.isEnabled("B:\"Locale Fix\"=true")) && Locale.getDefault().getLanguage().equals("tr"))
        {
            UniversalTweaks.LOGGER.info("The locale is Turkish, which is unfortunately not supported by some mods. Changing to English...");
            Locale.setDefault(Locale.ENGLISH);
        }
    }

    @Override
    public String[] getASMTransformerClass()
    {
        return new String[0];
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {

    }

    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public List<String> getMixinConfigs()
    {
        return isClient ? Arrays.asList(
            "mixins.bugfixes.blockfire.json",
            "mixins.bugfixes.blockoverlay.json",
            "mixins.bugfixes.boatoffset.json",
            "mixins.bugfixes.comparatortiming.json",
            "mixins.bugfixes.deathtime.json",
            "mixins.bugfixes.destroypacket.json",
            "mixins.bugfixes.dimensionchange.json",
            "mixins.bugfixes.disconnectdupe.json",
            "mixins.bugfixes.entityaabb.json",
            "mixins.bugfixes.entitydesync.json",
            "mixins.bugfixes.entitysuffocation.json",
            "mixins.bugfixes.entitytracker.json",
            "mixins.bugfixes.exhaustion.json",
            "mixins.bugfixes.frustumculling.json",
            "mixins.bugfixes.hopperaabb.json",
            "mixins.bugfixes.itemframevoid.json",
            "mixins.bugfixes.ladderflying.json",
            "mixins.bugfixes.miningglitch.client.json",
            "mixins.bugfixes.miningglitch.server.json",
            "mixins.bugfixes.pistontile.json",
            "mixins.bugfixes.skeletonaim.json",
            "mixins.bugfixes.startup.json",
            "mixins.bugfixes.teloadorder.json",
            "mixins.bugfixes.villagermantle.json",
            "mixins.tweaks.ai.json",
            "mixins.tweaks.attackcooldown.json",
            "mixins.tweaks.attributes.json",
            "mixins.tweaks.audioreload.json",
            "mixins.tweaks.autojump.json",
            "mixins.tweaks.bedobstruction.json",
            "mixins.tweaks.betterplacement.json",
            "mixins.tweaks.chunkgenlimit.json",
            "mixins.tweaks.collisiondamage.json",
            "mixins.tweaks.creeperconfetti.json",
            "mixins.tweaks.dyeblending.json",
            "mixins.tweaks.falldamage.json",
            "mixins.tweaks.infinitemusic.json",
            "mixins.tweaks.itementity.json",
            "mixins.tweaks.lightningflash.json",
            "mixins.tweaks.mobdespawn.json",
            "mixins.tweaks.nightvisionflash.json",
            "mixins.tweaks.plantables.json",
            "mixins.tweaks.playerspeed.json",
            "mixins.tweaks.prefixcheck.json",
            "mixins.tweaks.realmsbutton.json",
            "mixins.tweaks.recipebook.json",
            "mixins.tweaks.redstonelighting.json",
            "mixins.tweaks.resourcemanager.json",
            "mixins.tweaks.saddledwandering.json",
            "mixins.tweaks.smoothscrolling.json",
            "mixins.tweaks.spawning.json",
            "mixins.tweaks.uncapfps.json",
            "mixins.tweaks.xpbottle.json") :
            Arrays.asList(
                "mixins.bugfixes.blockfire.json",
                "mixins.bugfixes.boatoffset.json",
                "mixins.bugfixes.comparatortiming.json",
                "mixins.bugfixes.deathtime.json",
                "mixins.bugfixes.destroypacket.json",
                "mixins.bugfixes.dimensionchange.json",
                "mixins.bugfixes.disconnectdupe.json",
                "mixins.bugfixes.entityaabb.json",
                "mixins.bugfixes.entitydesync.json",
                "mixins.bugfixes.entitysuffocation.json",
                "mixins.bugfixes.entitytracker.json",
                "mixins.bugfixes.exhaustion.json",
                "mixins.bugfixes.hopperaabb.json",
                "mixins.bugfixes.itemframevoid.json",
                "mixins.bugfixes.ladderflying.json",
                "mixins.bugfixes.miningglitch.server.json",
                "mixins.bugfixes.pistontile.json",
                "mixins.bugfixes.skeletonaim.json",
                "mixins.bugfixes.teloadorder.json",
                "mixins.tweaks.ai.json",
                "mixins.tweaks.attackcooldown.json",
                "mixins.tweaks.attributes.json",
                "mixins.tweaks.bedobstruction.json",
                "mixins.tweaks.chunkgenlimit.json",
                "mixins.tweaks.collisiondamage.json",
                "mixins.tweaks.creeperconfetti.json",
                "mixins.tweaks.dyeblending.json",
                "mixins.tweaks.falldamage.json",
                "mixins.tweaks.itementity.json",
                "mixins.tweaks.mobdespawn.json",
                "mixins.tweaks.plantables.json",
                "mixins.tweaks.playerspeed.json",
                "mixins.tweaks.prefixcheck.json",
                "mixins.tweaks.redstonelighting.json",
                "mixins.tweaks.saddledwandering.json",
                "mixins.tweaks.spawning.json",
                "mixins.tweaks.xpbottle.json"
            );
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (isDev) return true;
        if (isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.tweaks.audioreload.json":
                    try
                    {
                        Class.forName("net.darkhax.surge.core.SurgeLoadingPlugin");
                        return false;
                    }
                    catch (ClassNotFoundException e)
                    {
                        return firstLaunch || UTConfigParser.isEnabled("B:\"Disable Audio Debug\"=true");
                    }
                case "mixins.bugfixes.blockoverlay.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Block Overlay Fix\"=true");
                case "mixins.bugfixes.frustumculling.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Frustum Culling Fix\"=true");
                case "mixins.bugfixes.miningglitch.client.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Mining Glitch Fix\"=true");
                case "mixins.bugfixes.startup.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Faster Background Startup\"=true");
                case "mixins.bugfixes.villagermantle.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Villager Mantle Fix\"=true");
                case "mixins.tweaks.autojump.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Auto Jump Replacement\"=true");
                case "mixins.tweaks.betterplacement.json":
                    return !firstLaunch && !UTConfigParser.isEnabled("I:\"Better Placement Click Delay\"=4");
                case "mixins.tweaks.infinitemusic.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Infinite Music\"=true");
                case "mixins.tweaks.lightningflash.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"No Lightning Flash\"=true");
                case "mixins.tweaks.nightvisionflash.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"No Night Vision Flash\"=true");
                case "mixins.tweaks.realmsbutton.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Remove Realms Button\"=true");
                case "mixins.tweaks.recipebook.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Remove Recipe Book\"=true");
                case "mixins.tweaks.resourcemanager.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Disable Animated Models\"=true");
                case "mixins.tweaks.smoothscrolling.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Smooth Scrolling\"=true");
                case "mixins.tweaks.uncapfps.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Uncap FPS\"=true");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blockfire.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Block Fire Fix\"=true");
            case "mixins.bugfixes.boatoffset.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Boat Riding Offset Fix\"=true");
            case "mixins.bugfixes.comparatortiming.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Comparator Timing Fix\"=true");
            case "mixins.bugfixes.deathtime.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Death Time Fix\"=true");
            case "mixins.bugfixes.destroypacket.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Destroy Entity Packets Fix\"=true");
            case "mixins.bugfixes.dimensionchange.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Dimension Change Player States Fix\"=true");
            case "mixins.bugfixes.disconnectdupe.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Disconnect Dupe Fix\"=true");
            case "mixins.bugfixes.hopperaabb.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Hopper Bounding Box Fix\"=true");
            case "mixins.bugfixes.itemframevoid.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Item Frame Void Fix\"=true");
            case "mixins.bugfixes.entityaabb.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Bounding Box Fix\"=true");
            case "mixins.bugfixes.entitydesync.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Desync Fix\"=true");
            case "mixins.bugfixes.entitysuffocation.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Suffocation Fix\"=true");
            case "mixins.bugfixes.entitytracker.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Tracker Fix\"=true");
            case "mixins.bugfixes.exhaustion.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Exhaustion Fix\"=true");
            case "mixins.bugfixes.ladderflying.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Ladder Flying Slowdown Fix\"=true");
            case "mixins.bugfixes.miningglitch.server.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Mining Glitch Fix\"=true");
            case "mixins.bugfixes.pistontile.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Piston Progress Fix\"=true");
            case "mixins.bugfixes.skeletonaim.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Skeleton Aim Fix\"=true");
            case "mixins.bugfixes.teloadorder.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Tile Entity Update Order Fix\"=true");
            case "mixins.tweaks.ai.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"AI Replacement\"=true");
            case "mixins.tweaks.attackcooldown.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"No Attack Cooldown\"=true");
            case "mixins.tweaks.attributes.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Attributes Uncap\"=true");
            case "mixins.tweaks.bedobstruction.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Bed Obstruction Replacement\"=true");
            case "mixins.tweaks.chunkgenlimit.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Chunk Gen Limit\"=true");
            case "mixins.tweaks.collisiondamage.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Horizontal Collision Damage\"=true");
            case "mixins.tweaks.creeperconfetti.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Creeper Confetti\"=true");
            case "mixins.tweaks.dyeblending.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Fast Dye Blending\"=true");
            case "mixins.tweaks.itementity.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Item Entity Combination\"=true");
            case "mixins.tweaks.falldamage.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Water Fall Damage\"=true");
            case "mixins.tweaks.mobdespawn.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Mob Despawn Improvement\"=true");
            case "mixins.tweaks.plantables.json":
                return !firstLaunch && (!UTConfigParser.isEnabled("I:\"Cactus Size\"=3") || !UTConfigParser.isEnabled("I:\"Sugar Cane Size\"=3"));
            case "mixins.tweaks.playerspeed.json":
                return !firstLaunch && (!UTConfigParser.isEnabled("D:\"Player Fly Speed\"=0.05") || !UTConfigParser.isEnabled("D:\"Player Walk Speed\"=0.1"));
            case "mixins.tweaks.prefixcheck.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Fast Prefix Checking\"=true");
            case "mixins.tweaks.redstonelighting.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"No Redstone Lighting\"=true");
            case "mixins.tweaks.saddledwandering.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"No Saddled Wandering\"=true");
            case "mixins.tweaks.spawning.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Husk & Stray Spawning\"=true");
            case "mixins.tweaks.xpbottle.json":
                return !firstLaunch && !UTConfigParser.isEnabled("I:\"XP Bottle Amount\"=-1");
        }
        return true;
    }
}