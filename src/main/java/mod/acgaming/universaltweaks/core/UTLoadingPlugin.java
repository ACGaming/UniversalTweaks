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
        if ((firstLaunch || UTConfigParser.isEnabled("B:\"Locale Crash\"=true")) && Locale.getDefault().getLanguage().equals("tr"))
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
            "mixins.bugfixes.blocks.blockoverlay.json",
            "mixins.bugfixes.blocks.comparatortiming.json",
            "mixins.bugfixes.blocks.hopper.boundingbox.json",
            "mixins.bugfixes.blocks.hopper.tile.json",
            "mixins.bugfixes.blocks.itemframevoid.json",
            "mixins.bugfixes.blocks.ladderflying.json",
            "mixins.bugfixes.blocks.miningglitch.client.json",
            "mixins.bugfixes.blocks.miningglitch.server.json",
            "mixins.bugfixes.blocks.pistontile.json",
            "mixins.bugfixes.entities.blockfire.json",
            "mixins.bugfixes.entities.boatoffset.json",
            "mixins.bugfixes.entities.boundingbox.json",
            "mixins.bugfixes.entities.deathtime.json",
            "mixins.bugfixes.entities.destroypacket.json",
            "mixins.bugfixes.entities.desync.json",
            "mixins.bugfixes.entities.dimensionchange.json",
            "mixins.bugfixes.entities.disconnectdupe.json",
            "mixins.bugfixes.entities.maxhealth.json",
            "mixins.bugfixes.entities.saturation.json",
            "mixins.bugfixes.entities.skeletonaim.json",
            "mixins.bugfixes.entities.suffocation.json",
            "mixins.bugfixes.entities.tracker.json",
            "mixins.bugfixes.entities.villagermantle.json",
            "mixins.bugfixes.misc.depthmask.json",
            "mixins.bugfixes.misc.modelgap.json",
            "mixins.bugfixes.misc.smoothlighting.json",
            "mixins.bugfixes.misc.startup.json",
            "mixins.bugfixes.world.chunksaving.json",
            "mixins.bugfixes.world.frustumculling.json",
            "mixins.bugfixes.world.teloadorder.json",
            "mixins.tweaks.ai.json",
            "mixins.tweaks.attackcooldown.json",
            "mixins.tweaks.audioreload.json",
            "mixins.tweaks.autojump.json",
            "mixins.tweaks.bedobstruction.json",
            "mixins.tweaks.boatspeed.json",
            "mixins.tweaks.breakablebedrock.json",
            "mixins.tweaks.chunkgenlimit.json",
            "mixins.tweaks.collisiondamage.json",
            "mixins.tweaks.craftingcache.json",
            "mixins.tweaks.creeperconfetti.json",
            "mixins.tweaks.customrarity.json",
            "mixins.tweaks.dyeblending.json",
            "mixins.tweaks.falldamage.json",
            "mixins.tweaks.hardcorebuckets.json",
            "mixins.tweaks.incurablepotions.json",
            "mixins.tweaks.infinitemusic.json",
            "mixins.tweaks.itementities.client.json",
            "mixins.tweaks.itementities.server.json",
            "mixins.tweaks.lightningflash.json",
            "mixins.tweaks.linearxp.json",
            "mixins.tweaks.mobdespawn.json",
            "mixins.tweaks.narrator.json",
            "mixins.tweaks.nightvisionflash.json",
            "mixins.tweaks.nogolems.json",
            "mixins.tweaks.plantables.json",
            "mixins.tweaks.playerspeed.json",
            "mixins.tweaks.prefixcheck.json",
            "mixins.tweaks.realmsbutton.json",
            "mixins.tweaks.recipebook.client.json",
            "mixins.tweaks.recipebook.server.json",
            "mixins.tweaks.redstonelighting.json",
            "mixins.tweaks.resourcemanager.json",
            "mixins.tweaks.saddledwandering.json",
            "mixins.tweaks.skipcredits.json",
            "mixins.tweaks.smoothscrolling.json",
            "mixins.tweaks.snooper.client.json",
            "mixins.tweaks.spawning.json",
            "mixins.tweaks.toastcontrol.json",
            "mixins.tweaks.uncapfps.json",
            "mixins.tweaks.worldloading.client.json",
            "mixins.tweaks.worldloading.server.json",
            "mixins.tweaks.xpbottle.json") :
            Arrays.asList(
                "mixins.bugfixes.blocks.comparatortiming.json",
                "mixins.bugfixes.blocks.hopper.boundingbox.json",
                "mixins.bugfixes.blocks.hopper.tile.json",
                "mixins.bugfixes.blocks.itemframevoid.json",
                "mixins.bugfixes.blocks.ladderflying.json",
                "mixins.bugfixes.blocks.miningglitch.server.json",
                "mixins.bugfixes.blocks.pistontile.json",
                "mixins.bugfixes.entities.blockfire.json",
                "mixins.bugfixes.entities.boatoffset.json",
                "mixins.bugfixes.entities.boundingbox.json",
                "mixins.bugfixes.entities.deathtime.json",
                "mixins.bugfixes.entities.destroypacket.json",
                "mixins.bugfixes.entities.desync.json",
                "mixins.bugfixes.entities.dimensionchange.json",
                "mixins.bugfixes.entities.disconnectdupe.json",
                "mixins.bugfixes.entities.maxhealth.json",
                "mixins.bugfixes.entities.saturation.json",
                "mixins.bugfixes.entities.skeletonaim.json",
                "mixins.bugfixes.entities.suffocation.json",
                "mixins.bugfixes.entities.tracker.json",
                "mixins.bugfixes.world.chunksaving.json",
                "mixins.bugfixes.world.teloadorder.json",
                "mixins.tweaks.ai.json",
                "mixins.tweaks.attackcooldown.json",
                "mixins.tweaks.bedobstruction.json",
                "mixins.tweaks.boatspeed.json",
                "mixins.tweaks.breakablebedrock.json",
                "mixins.tweaks.chunkgenlimit.json",
                "mixins.tweaks.collisiondamage.json",
                "mixins.tweaks.craftingcache.json",
                "mixins.tweaks.creeperconfetti.json",
                "mixins.tweaks.customrarity.json",
                "mixins.tweaks.dyeblending.json",
                "mixins.tweaks.falldamage.json",
                "mixins.tweaks.hardcorebuckets.json",
                "mixins.tweaks.incurablepotions.json",
                "mixins.tweaks.itementities.server.json",
                "mixins.tweaks.linearxp.json",
                "mixins.tweaks.mobdespawn.json",
                "mixins.tweaks.nogolems.json",
                "mixins.tweaks.plantables.json",
                "mixins.tweaks.playerspeed.json",
                "mixins.tweaks.prefixcheck.json",
                "mixins.tweaks.recipebook.server.json",
                "mixins.tweaks.redstonelighting.json",
                "mixins.tweaks.saddledwandering.json",
                "mixins.tweaks.snooper.server.json",
                "mixins.tweaks.spawning.json",
                "mixins.tweaks.worldloading.server.json",
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
                case "mixins.bugfixes.blocks.blockoverlay.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"[1] Block Overlay Toggle\"=true");
                case "mixins.bugfixes.misc.depthmask.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Depth Mask\"=true");
                case "mixins.bugfixes.world.frustumculling.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Frustum Culling\"=true");
                case "mixins.bugfixes.blocks.miningglitch.client.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Mining Glitch\"=true");
                case "mixins.bugfixes.misc.modelgap.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"[1] Model Gap Toggle\"=true");
                case "mixins.bugfixes.misc.smoothlighting.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Accurate Smooth Lighting\"=true");
                case "mixins.bugfixes.misc.startup.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Faster Background Startup\"=true");
                case "mixins.bugfixes.entities.villagermantle.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Villager Mantle Hoods\"=true");
                case "mixins.tweaks.autojump.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Auto Jump Replacement\"=true");
                case "mixins.tweaks.infinitemusic.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Infinite Music\"=true");
                case "mixins.tweaks.itementities.client.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"[01] Item Entities Toggle\"=true");
                case "mixins.tweaks.lightningflash.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"No Lightning Flash\"=true");
                case "mixins.tweaks.narrator.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Disable Narrator\"=true");
                case "mixins.tweaks.nightvisionflash.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"No Night Vision Flash\"=true");
                case "mixins.tweaks.realmsbutton.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Remove Realms Button\"=true");
                case "mixins.tweaks.recipebook.client.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Remove Recipe Book\"=true");
                case "mixins.tweaks.resourcemanager.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Check Animated Models\"=true");
                case "mixins.tweaks.skipcredits.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Skip Credits\"=true");
                case "mixins.tweaks.smoothscrolling.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"[1] Smooth Scrolling Toggle\"=true");
                case "mixins.tweaks.snooper.client.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Remove Snooper\"=true");
                case "mixins.tweaks.toastcontrol.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"[1] Toast Control Toggle\"=true");
                case "mixins.tweaks.uncapfps.json":
                    return firstLaunch || UTConfigParser.isEnabled("B:\"Uncap FPS\"=true");
                case "mixins.tweaks.worldloading.client.json":
                    return !firstLaunch && UTConfigParser.isEnabled("B:\"Fast World Loading\"=true");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.entities.blockfire.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Block Fire\"=true");
            case "mixins.bugfixes.entities.boatoffset.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Boat Riding Offset\"=true");
            case "mixins.bugfixes.blocks.comparatortiming.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Comparator Timing\"=true");
            case "mixins.bugfixes.world.chunksaving.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Chunk Saving\"=true");
            case "mixins.bugfixes.entities.deathtime.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Death Time\"=true");
            case "mixins.bugfixes.entities.destroypacket.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Destroy Entity Packets\"=true");
            case "mixins.bugfixes.entities.dimensionchange.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Dimension Change Player States\"=true");
            case "mixins.bugfixes.entities.disconnectdupe.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Disconnect Dupe\"=true");
            case "mixins.bugfixes.entities.boundingbox.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Bounding Box\"=true");
            case "mixins.bugfixes.entities.desync.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Desync\"=true");
            case "mixins.bugfixes.entities.maxhealth.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Max Player Health\"=true");
            case "mixins.bugfixes.entities.suffocation.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Suffocation\"=true");
            case "mixins.bugfixes.entities.tracker.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Entity Tracker\"=true");
            case "mixins.bugfixes.entities.saturation.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Player Saturation\"=true");
            case "mixins.bugfixes.blocks.hopper.boundingbox.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Hopper Bounding Box\"=true");
            case "mixins.bugfixes.blocks.hopper.tile.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Hopper Insert Safety Check\"=true");
            case "mixins.bugfixes.blocks.itemframevoid.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Item Frame Void\"=true");
            case "mixins.bugfixes.blocks.ladderflying.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Ladder Flying Slowdown\"=true");
            case "mixins.bugfixes.blocks.miningglitch.server.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Mining Glitch\"=true");
            case "mixins.bugfixes.blocks.pistontile.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Piston Progress\"=true");
            case "mixins.bugfixes.entities.skeletonaim.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Skeleton Aim\"=true");
            case "mixins.bugfixes.world.teloadorder.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Tile Entity Update Order\"=true");
            case "mixins.tweaks.ai.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"AI Replacement\"=true");
            case "mixins.tweaks.attackcooldown.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"No Attack Cooldown\"=true");
            case "mixins.tweaks.bedobstruction.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Bed Obstruction Replacement\"=true");
            case "mixins.tweaks.boatspeed.json":
                return !firstLaunch && !UTConfigParser.isEnabled("D:\"Boat Speed\"=0.04");
            case "mixins.tweaks.breakablebedrock.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"[1] Breakable Bedrock Toggle\"=true");
            case "mixins.tweaks.chunkgenlimit.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"[1] Chunk Gen Limit Toggle\"=true");
            case "mixins.tweaks.collisiondamage.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"[1] Collision Damage Toggle\"=true");
            case "mixins.tweaks.craftingcache.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Crafting Cache\"=true");
            case "mixins.tweaks.creeperconfetti.json":
                return !firstLaunch && !UTConfigParser.isEnabled("D:\"Creeper Confetti\"=0.0");
            case "mixins.tweaks.dyeblending.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Fast Dye Blending\"=true");
            case "mixins.tweaks.falldamage.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"[1] Water Fall Damage Toggle\"=true");
            case "mixins.tweaks.hardcorebuckets.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Hardcore Buckets\"=true");
            case "mixins.tweaks.incurablepotions.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"[1] Incurable Potions Toggle\"=true");
            case "mixins.tweaks.itementities.server.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"[01] Item Entities Toggle\"=true");
            case "mixins.tweaks.linearxp.json":
                return !firstLaunch && !UTConfigParser.isEnabled("I:\"Linear XP Amount\"=0");
            case "mixins.tweaks.mobdespawn.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Mob Despawn Improvement\"=true");
            case "mixins.tweaks.nogolems.json":
                return !firstLaunch && (UTConfigParser.isEnabled("B:\"[1] Iron Golem Toggle\"=true") || UTConfigParser.isEnabled("B:\"[2] Snow Golem Toggle\"=true") || UTConfigParser.isEnabled("B:\"[3] Wither Toggle\"=true"));
            case "mixins.tweaks.plantables.json":
                return !firstLaunch && (!UTConfigParser.isEnabled("I:\"Cactus Size\"=3") || !UTConfigParser.isEnabled("I:\"Sugar Cane Size\"=3"));
            case "mixins.tweaks.playerspeed.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"[1] Player Speed Toggle\"=true");
            case "mixins.tweaks.prefixcheck.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Fast Prefix Checking\"=true");
            case "mixins.tweaks.recipebook.server.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Remove Recipe Book\"=true");
            case "mixins.tweaks.redstonelighting.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"No Redstone Lighting\"=true");
            case "mixins.tweaks.saddledwandering.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"No Saddled Wandering\"=true");
            case "mixins.tweaks.snooper.server.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Remove Snooper\"=true");
            case "mixins.tweaks.spawning.json":
                return firstLaunch || UTConfigParser.isEnabled("B:\"Husk & Stray Spawning\"=true");
            case "mixins.tweaks.worldloading.server.json":
                return !firstLaunch && UTConfigParser.isEnabled("B:\"Fast World Loading\"=true");
            case "mixins.tweaks.xpbottle.json":
                return !firstLaunch && !UTConfigParser.isEnabled("I:\"XP Bottle Amount\"=-1");
        }
        return true;
    }
}