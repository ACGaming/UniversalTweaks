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
        if (firstLaunch || UTConfigParser.isPresent("B:\"Show Loading Time\"=true")) launchTime = System.currentTimeMillis();
        if ((firstLaunch || UTConfigParser.isPresent("B:\"Locale Crash\"=true")) && Locale.getDefault().getLanguage().equals("tr"))
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
            "mixins.bugfixes.entities.ai.json",
            "mixins.bugfixes.entities.attackradius.json",
            "mixins.bugfixes.entities.blockfire.json",
            "mixins.bugfixes.entities.boatoffset.json",
            "mixins.bugfixes.entities.boundingbox.json",
            "mixins.bugfixes.entities.deathtime.json",
            "mixins.bugfixes.entities.destroypacket.json",
            "mixins.bugfixes.entities.desync.json",
            "mixins.bugfixes.entities.dimensionchange.json",
            "mixins.bugfixes.entities.disconnectdupe.json",
            "mixins.bugfixes.entities.elytra.json",
            "mixins.bugfixes.entities.entityid.json",
            "mixins.bugfixes.entities.maxhealth.json",
            "mixins.bugfixes.entities.mount.json",
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
            "mixins.bugfixes.world.tileentities.json",
            "mixins.tweaks.blocks.bedobstruction.json",
            "mixins.tweaks.blocks.breakablebedrock.json",
            "mixins.tweaks.blocks.growthsize.json",
            "mixins.tweaks.blocks.hitdelay.json",
            "mixins.tweaks.blocks.leafdecay.json",
            "mixins.tweaks.blocks.lenientpaths.json",
            "mixins.tweaks.entities.ai.json",
            "mixins.tweaks.entities.ai.saddledwandering.json",
            "mixins.tweaks.entities.autojump.json",
            "mixins.tweaks.entities.burning.horses.json",
            "mixins.tweaks.entities.damage.collision.json",
            "mixins.tweaks.entities.damage.falling.json",
            "mixins.tweaks.entities.despawning.json",
            "mixins.tweaks.entities.loot.json",
            "mixins.tweaks.entities.spawning.caps.json",
            "mixins.tweaks.entities.spawning.creepers.confetti.json",
            "mixins.tweaks.entities.spawning.golems.json",
            "mixins.tweaks.entities.spawning.husk.json",
            "mixins.tweaks.entities.spawning.stray.json",
            "mixins.tweaks.entities.speed.boat.json",
            "mixins.tweaks.entities.speed.player.json",
            "mixins.tweaks.entities.taming.horses.json",
            "mixins.tweaks.items.attackcooldown.json",
            "mixins.tweaks.items.hardcorebuckets.json",
            "mixins.tweaks.items.itementities.client.json",
            "mixins.tweaks.items.itementities.server.json",
            "mixins.tweaks.items.rarity.json",
            "mixins.tweaks.items.repairing.json",
            "mixins.tweaks.items.xpbottle.json",
            "mixins.tweaks.misc.buttons.realms.json",
            "mixins.tweaks.misc.buttons.snooper.client.json",
            "mixins.tweaks.misc.commands.seed.json",
            "mixins.tweaks.misc.credits.json",
            "mixins.tweaks.misc.difficulty.json",
            "mixins.tweaks.misc.incurablepotions.json",
            "mixins.tweaks.misc.lightningflash.json",
            "mixins.tweaks.misc.xp.linear.json",
            "mixins.tweaks.misc.xp.smelting.json",
            "mixins.tweaks.misc.music.json",
            "mixins.tweaks.misc.narrator.json",
            "mixins.tweaks.misc.nightvisionflash.json",
            "mixins.tweaks.misc.recipebook.client.json",
            "mixins.tweaks.misc.recipebook.server.json",
            "mixins.tweaks.misc.smoothscrolling.json",
            "mixins.tweaks.misc.toastcontrol.json",
            "mixins.tweaks.performance.audioreload.json",
            "mixins.tweaks.performance.autosave.json",
            "mixins.tweaks.performance.craftingcache.json",
            "mixins.tweaks.performance.dyeblending.json",
            "mixins.tweaks.performance.fps.json",
            "mixins.tweaks.performance.missingmodel.json",
            "mixins.tweaks.performance.prefixcheck.json",
            "mixins.tweaks.performance.redstone.json",
            "mixins.tweaks.performance.resourcemanager.json",
            "mixins.tweaks.world.chunks.gen.json",
            "mixins.tweaks.world.loading.client.json",
            "mixins.tweaks.world.loading.server.json") :
            Arrays.asList(
                "mixins.bugfixes.blocks.comparatortiming.json",
                "mixins.bugfixes.blocks.hopper.boundingbox.json",
                "mixins.bugfixes.blocks.hopper.tile.json",
                "mixins.bugfixes.blocks.itemframevoid.json",
                "mixins.bugfixes.blocks.ladderflying.json",
                "mixins.bugfixes.blocks.miningglitch.server.json",
                "mixins.bugfixes.blocks.pistontile.json",
                "mixins.bugfixes.entities.ai.json",
                "mixins.bugfixes.entities.attackradius.json",
                "mixins.bugfixes.entities.blockfire.json",
                "mixins.bugfixes.entities.boatoffset.json",
                "mixins.bugfixes.entities.boundingbox.json",
                "mixins.bugfixes.entities.deathtime.json",
                "mixins.bugfixes.entities.destroypacket.json",
                "mixins.bugfixes.entities.desync.json",
                "mixins.bugfixes.entities.dimensionchange.json",
                "mixins.bugfixes.entities.disconnectdupe.json",
                "mixins.bugfixes.entities.entityid.json",
                "mixins.bugfixes.entities.maxhealth.json",
                "mixins.bugfixes.entities.mount.json",
                "mixins.bugfixes.entities.saturation.json",
                "mixins.bugfixes.entities.skeletonaim.json",
                "mixins.bugfixes.entities.suffocation.json",
                "mixins.bugfixes.entities.tracker.json",
                "mixins.bugfixes.world.chunksaving.json",
                "mixins.bugfixes.world.tileentities.json",
                "mixins.tweaks.blocks.bedobstruction.json",
                "mixins.tweaks.blocks.breakablebedrock.json",
                "mixins.tweaks.blocks.growthsize.json",
                "mixins.tweaks.blocks.hitdelay.json",
                "mixins.tweaks.blocks.leafdecay.json",
                "mixins.tweaks.blocks.lenientpaths.json",
                "mixins.tweaks.entities.ai.json",
                "mixins.tweaks.entities.ai.saddledwandering.json",
                "mixins.tweaks.entities.burning.horses.json",
                "mixins.tweaks.entities.damage.collision.json",
                "mixins.tweaks.entities.damage.falling.json",
                "mixins.tweaks.entities.despawning.json",
                "mixins.tweaks.entities.loot.json",
                "mixins.tweaks.entities.spawning.caps.json",
                "mixins.tweaks.entities.spawning.creepers.confetti.json",
                "mixins.tweaks.entities.spawning.golems.json",
                "mixins.tweaks.entities.spawning.husk.json",
                "mixins.tweaks.entities.spawning.stray.json",
                "mixins.tweaks.entities.speed.boat.json",
                "mixins.tweaks.entities.speed.player.json",
                "mixins.tweaks.entities.taming.horses.json",
                "mixins.tweaks.items.attackcooldown.json",
                "mixins.tweaks.items.hardcorebuckets.json",
                "mixins.tweaks.items.itementities.server.json",
                "mixins.tweaks.items.rarity.json",
                "mixins.tweaks.items.repairing.json",
                "mixins.tweaks.items.xpbottle.json",
                "mixins.tweaks.misc.buttons.snooper.server.json",
                "mixins.tweaks.misc.difficulty.json",
                "mixins.tweaks.misc.incurablepotions.json",
                "mixins.tweaks.misc.xp.linear.json",
                "mixins.tweaks.misc.xp.smelting.json",
                "mixins.tweaks.misc.recipebook.server.json",
                "mixins.tweaks.performance.autosave.json",
                "mixins.tweaks.performance.craftingcache.json",
                "mixins.tweaks.performance.dyeblending.json",
                "mixins.tweaks.performance.prefixcheck.json",
                "mixins.tweaks.performance.redstone.json",
                "mixins.tweaks.world.chunks.gen.json",
                "mixins.tweaks.world.loading.server.json"
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
                case "mixins.tweaks.performance.audioreload.json":
                    try
                    {
                        Class.forName("net.darkhax.surge.core.SurgeLoadingPlugin");
                        return false;
                    }
                    catch (ClassNotFoundException e)
                    {
                        return firstLaunch || UTConfigParser.isPresent("B:\"Disable Audio Debug\"=true");
                    }
                case "mixins.bugfixes.blocks.blockoverlay.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"[1] Block Overlay Toggle\"=true");
                case "mixins.bugfixes.blocks.miningglitch.client.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Mining Glitch\"=true");
                case "mixins.bugfixes.entities.elytra.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Elytra Deployment & Landing\"=true");
                case "mixins.bugfixes.entities.villagermantle.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Villager Mantle Hoods\"=true");
                case "mixins.bugfixes.misc.depthmask.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Depth Mask\"=true");
                case "mixins.bugfixes.misc.modelgap.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"[1] Model Gap Toggle\"=true");
                case "mixins.bugfixes.misc.smoothlighting.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Accurate Smooth Lighting\"=true");
                case "mixins.bugfixes.misc.startup.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Faster Background Startup\"=true");
                case "mixins.bugfixes.world.frustumculling.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Frustum Culling\"=true");
                case "mixins.tweaks.entities.autojump.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Auto Jump Replacement\"=true");
                case "mixins.tweaks.items.itementities.client.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"[01] Item Entities Toggle\"=true");
                case "mixins.tweaks.misc.buttons.realms.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Remove Realms Button\"=true");
                case "mixins.tweaks.misc.buttons.snooper.client.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Remove Snooper\"=true");
                case "mixins.tweaks.misc.commands.seed.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"Copy World Seed\"=true");
                case "mixins.tweaks.misc.credits.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"Skip Credits\"=true");
                case "mixins.tweaks.misc.lightningflash.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"No Lightning Flash\"=true");
                case "mixins.tweaks.misc.music.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"Infinite Music\"=true");
                case "mixins.tweaks.misc.narrator.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Disable Narrator\"=true");
                case "mixins.tweaks.misc.nightvisionflash.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"No Night Vision Flash\"=true");
                case "mixins.tweaks.misc.recipebook.client.json":
                    return !firstLaunch && UTConfigParser.isPresent("B:\"Remove Recipe Book\"=true");
                case "mixins.tweaks.misc.smoothscrolling.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"[1] Smooth Scrolling Toggle\"=true");
                case "mixins.tweaks.misc.toastcontrol.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"[1] Toast Control Toggle\"=true");
                case "mixins.tweaks.performance.fps.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Uncap FPS\"=true");
                case "mixins.tweaks.performance.missingmodel.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Disable Fancy Missing Model\"=true");
                case "mixins.tweaks.performance.resourcemanager.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Check Animated Models\"=true");
                case "mixins.tweaks.world.loading.client.json":
                    return firstLaunch || UTConfigParser.isPresent("B:\"Fast World Loading\"=true");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blocks.comparatortiming.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Comparator Timing\"=true");
            case "mixins.bugfixes.blocks.hopper.boundingbox.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Hopper Bounding Box\"=true");
            case "mixins.bugfixes.blocks.hopper.tile.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Hopper Insert Safety Check\"=true");
            case "mixins.bugfixes.blocks.itemframevoid.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Item Frame Void\"=true");
            case "mixins.bugfixes.blocks.ladderflying.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Ladder Flying Slowdown\"=true");
            case "mixins.bugfixes.blocks.miningglitch.server.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Mining Glitch\"=true");
            case "mixins.bugfixes.blocks.pistontile.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Piston Progress\"=true");
            case "mixins.bugfixes.entities.ai.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Concurrent Entity AI Tasks\"=true");
            case "mixins.bugfixes.entities.attackradius.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Attack Radius\"=true");
            case "mixins.bugfixes.entities.blockfire.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Block Fire\"=true");
            case "mixins.bugfixes.entities.boatoffset.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Boat Riding Offset\"=true");
            case "mixins.bugfixes.entities.boundingbox.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Entity Bounding Box\"=true");
            case "mixins.bugfixes.entities.deathtime.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Death Time\"=true");
            case "mixins.bugfixes.entities.destroypacket.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Destroy Entity Packets\"=true");
            case "mixins.bugfixes.entities.desync.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Entity Desync\"=true");
            case "mixins.bugfixes.entities.dimensionchange.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Dimension Change Player States\"=true");
            case "mixins.bugfixes.entities.disconnectdupe.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Disconnect Dupe\"=true");
            case "mixins.bugfixes.entities.entityid.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Entity ID\"=true");
            case "mixins.bugfixes.entities.maxhealth.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Max Player Health\"=true");
            case "mixins.bugfixes.entities.mount.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Mount Desync\"=true");
            case "mixins.bugfixes.entities.saturation.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Player Saturation\"=true");
            case "mixins.bugfixes.entities.skeletonaim.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Skeleton Aim\"=true");
            case "mixins.bugfixes.entities.suffocation.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Entity Suffocation\"=true");
            case "mixins.bugfixes.entities.tracker.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Entity Tracker\"=true");
            case "mixins.bugfixes.world.chunksaving.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Chunk Saving\"=true");
            case "mixins.bugfixes.world.tileentities.json":
                return firstLaunch || !UTConfigParser.isPresent("S:\"Tile Entity Map\"=HASHMAP");
            case "mixins.tweaks.blocks.bedobstruction.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Bed Obstruction Replacement\"=true");
            case "mixins.tweaks.blocks.breakablebedrock.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Breakable Bedrock Toggle\"=true");
            case "mixins.tweaks.blocks.growthsize.json":
                return !firstLaunch && (!UTConfigParser.isPresent("I:\"Cactus Size\"=3") || !UTConfigParser.isPresent("I:\"Sugar Cane Size\"=3") || !UTConfigParser.isPresent("I:\"Vine Size\"=0"));
            case "mixins.tweaks.blocks.hitdelay.json":
                return !firstLaunch && !UTConfigParser.isPresent("I:\"Block Hit Delay\"=5");
            case "mixins.tweaks.blocks.leafdecay.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Fast Leaf Decay\"=true");
            case "mixins.tweaks.blocks.lenientpaths.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Lenient Paths\"=true");
            case "mixins.tweaks.entities.ai.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"AI Replacement\"=true");
            case "mixins.tweaks.entities.ai.saddledwandering.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"No Saddled Wandering\"=true");
            case "mixins.tweaks.entities.burning.horses.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Burning Undead Horses\"=true");
            case "mixins.tweaks.entities.damage.collision.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Collision Damage Toggle\"=true");
            case "mixins.tweaks.entities.damage.falling.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Water Fall Damage Toggle\"=true");
            case "mixins.tweaks.entities.despawning.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Mob Despawn Improvement\"=true");
            case "mixins.tweaks.entities.loot.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"Disable Creeper Music Discs\"=true");
            case "mixins.tweaks.entities.spawning.caps.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Spawn Caps Toggle\"=true");
            case "mixins.tweaks.entities.spawning.creepers.confetti.json":
                return !firstLaunch && !UTConfigParser.isPresent("D:\"Creeper Confetti Spawning Chance\"=0.0");
            case "mixins.tweaks.entities.spawning.golems.json":
                return !firstLaunch && (UTConfigParser.isPresent("B:\"[1] Iron Golem Toggle\"=true") || UTConfigParser.isPresent("B:\"[2] Snow Golem Toggle\"=true") || UTConfigParser.isPresent("B:\"[3] Wither Toggle\"=true"));
            case "mixins.tweaks.entities.spawning.husk.json":
            case "mixins.tweaks.entities.spawning.stray.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Husk & Stray Spawning\"=true");
            case "mixins.tweaks.entities.speed.boat.json":
                return !firstLaunch && !UTConfigParser.isPresent("D:\"Boat Speed\"=0.04");
            case "mixins.tweaks.entities.speed.player.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Player Speed Toggle\"=true");
            case "mixins.tweaks.entities.taming.horses.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Taming Undead Horses\"=true");
            case "mixins.tweaks.items.attackcooldown.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"No Attack Cooldown\"=true");
            case "mixins.tweaks.items.hardcorebuckets.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"Hardcore Buckets\"=true");
            case "mixins.tweaks.items.itementities.server.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"[01] Item Entities Toggle\"=true");
            case "mixins.tweaks.items.repairing.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"No Crafting Repair\"=true");
            case "mixins.tweaks.items.xpbottle.json":
                return !firstLaunch && !UTConfigParser.isPresent("I:\"XP Bottle Amount\"=-1");
            case "mixins.tweaks.misc.buttons.snooper.server.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Remove Snooper\"=true");
            case "mixins.tweaks.misc.incurablepotions.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"[1] Incurable Potions Toggle\"=true");
            case "mixins.tweaks.misc.xp.linear.json":
                return !firstLaunch && !UTConfigParser.isPresent("I:\"Linear XP Amount\"=0");
            case "mixins.tweaks.misc.recipebook.server.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"Remove Recipe Book\"=true");
            case "mixins.tweaks.misc.xp.smelting.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"No Smelting XP\"=true");
            case "mixins.tweaks.performance.autosave.json":
                return !firstLaunch && !UTConfigParser.isPresent("I:\"Auto Save Interval\"=900");
            case "mixins.tweaks.performance.craftingcache.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Crafting Cache\"=true");
            case "mixins.tweaks.performance.dyeblending.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Fast Dye Blending\"=true");
            case "mixins.tweaks.performance.prefixcheck.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Fast Prefix Checking\"=true");
            case "mixins.tweaks.performance.redstone.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"No Redstone Lighting\"=true");
            case "mixins.tweaks.world.chunks.gen.json":
                return !firstLaunch && UTConfigParser.isPresent("B:\"[1] Chunk Gen Limit Toggle\"=true");
            case "mixins.tweaks.world.loading.server.json":
                return firstLaunch || UTConfigParser.isPresent("B:\"Fast World Loading\"=true");
        }
        return true;
    }
}