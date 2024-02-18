package mod.acgaming.universaltweaks.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static final boolean isDev = FMLLaunchHandler.isDeobfuscatedEnvironment();
    public static boolean randomPatchesLoaded;
    public static boolean renderLibLoaded;
    public static boolean spongeForgeLoaded;
    public static boolean surgeLoaded;
    public static long launchTime;

    static
    {
        if (UTConfigGeneral.DEBUG.utLoadingTimeToggle) launchTime = System.currentTimeMillis();
        if (UTConfigBugfixes.MISC.utLocaleToggle && Locale.getDefault().getLanguage().equals("tr"))
        {
            UniversalTweaks.LOGGER.info("The locale is Turkish, which is unfortunately not supported by some mods. Changing to English...");
            Locale.setDefault(Locale.ENGLISH);
        }

        try
        {
            Class.forName("com.therandomlabs.randompatches.core.RPCore");
            randomPatchesLoaded = true;
        }
        catch (ClassNotFoundException ignored) {}

        try
        {
            Class.forName("meldexun.renderlib.RenderLib");
            renderLibLoaded = true;
        }
        catch (ClassNotFoundException ignored) {}

        try
        {
            Class.forName("org.spongepowered.mod.util.CompatibilityException");
            spongeForgeLoaded = true;
        }
        catch (ClassNotFoundException ignored) {}

        try
        {
            Class.forName("net.darkhax.surge.core.SurgeLoadingPlugin");
            surgeLoaded = true;
        }
        catch (ClassNotFoundException ignored) {}
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
        List<String> configs = new ArrayList<>();
        // CLIENT ONLY
        if (isClient)
        {
            configs.add("mixins.bugfixes.blocks.banner.json");
            configs.add("mixins.bugfixes.blocks.blockoverlay.json");
            configs.add("mixins.bugfixes.blocks.miningglitch.client.json");
            configs.add("mixins.bugfixes.entities.elytra.json");
            configs.add("mixins.bugfixes.entities.villagermantle.json");
            configs.add("mixins.bugfixes.misc.depthmask.json");
            configs.add("mixins.bugfixes.misc.modelgap.json");
            configs.add("mixins.bugfixes.misc.potionamplifier.json");
            configs.add("mixins.bugfixes.misc.smoothlighting.json");
            configs.add("mixins.bugfixes.misc.spectatormenu.json");
            configs.add("mixins.bugfixes.misc.startup.json");
            configs.add("mixins.bugfixes.world.frustumculling.json");
            configs.add("mixins.tweaks.entities.autojump.json");
            configs.add("mixins.tweaks.items.attackcooldown.client.json");
            configs.add("mixins.tweaks.items.itementities.client.json");
            configs.add("mixins.tweaks.items.rarity.json");
            configs.add("mixins.tweaks.misc.buttons.realms.json");
            configs.add("mixins.tweaks.misc.buttons.snooper.client.json");
            configs.add("mixins.tweaks.misc.commands.seed.json");
            configs.add("mixins.tweaks.misc.credits.json");
            configs.add("mixins.tweaks.misc.difficulty.client.json");
            configs.add("mixins.tweaks.misc.gui.lanserverproperties.json");
            configs.add("mixins.tweaks.misc.gui.overlaymessage.json");
            configs.add("mixins.tweaks.misc.gui.selecteditemtooltip.json");
            configs.add("mixins.tweaks.misc.lightning.flash.json");
            configs.add("mixins.tweaks.misc.music.json");
            configs.add("mixins.tweaks.misc.narrator.json");
            configs.add("mixins.tweaks.misc.nightvisionflash.json");
            configs.add("mixins.tweaks.misc.recipebook.client.json");
            configs.add("mixins.tweaks.misc.smoothscrolling.json");
            configs.add("mixins.tweaks.misc.toastcontrol.json");
            configs.add("mixins.tweaks.performance.audioreload.json");
            configs.add("mixins.tweaks.performance.fps.json");
            configs.add("mixins.tweaks.performance.missingmodel.json");
            configs.add("mixins.tweaks.performance.resourcemanager.json");
            configs.add("mixins.tweaks.world.loading.client.json");
        }
        // SERVER ONLY
        else
        {
            configs.add("mixins.tweaks.misc.buttons.snooper.server.json");
            configs.add("mixins.tweaks.misc.difficulty.server.json");
        }
        // COMMON
        configs.add("mixins.bugfixes.blocks.bed.json");
        configs.add("mixins.bugfixes.blocks.comparatortiming.json");
        configs.add("mixins.bugfixes.blocks.fallingblockdamage.json");
        configs.add("mixins.bugfixes.blocks.hopper.boundingbox.json");
        configs.add("mixins.bugfixes.blocks.hopper.tile.json");
        configs.add("mixins.bugfixes.blocks.itemframevoid.json");
        configs.add("mixins.bugfixes.blocks.ladderflying.json");
        configs.add("mixins.bugfixes.blocks.miningglitch.server.json");
        configs.add("mixins.bugfixes.blocks.piston.tile.json");
        configs.add("mixins.bugfixes.blocks.piston.retraction.json");
        configs.add("mixins.bugfixes.entities.ai.json");
        configs.add("mixins.bugfixes.entities.attackradius.json");
        configs.add("mixins.bugfixes.entities.blockfire.json");
        configs.add("mixins.bugfixes.entities.boatoffset.json");
        configs.add("mixins.bugfixes.entities.boundingbox.json");
        configs.add("mixins.bugfixes.entities.deathtime.json");
        configs.add("mixins.bugfixes.entities.destroypacket.json");
        configs.add("mixins.bugfixes.entities.desync.json");
        configs.add("mixins.bugfixes.entities.dimensionchange.json");
        configs.add("mixins.bugfixes.entities.disconnectdupe.json");
        configs.add("mixins.bugfixes.entities.entityid.json");
        configs.add("mixins.bugfixes.entities.entitylists.json");
        configs.add("mixins.bugfixes.entities.horsefalling.json");
        configs.add("mixins.bugfixes.entities.maxhealth.json");
        configs.add("mixins.bugfixes.entities.minecart.json");
        configs.add("mixins.bugfixes.entities.mount.json");
        configs.add("mixins.bugfixes.entities.saturation.json");
        configs.add("mixins.bugfixes.entities.skeletonaim.json");
        configs.add("mixins.bugfixes.entities.suffocation.json");
        configs.add("mixins.bugfixes.entities.tracker.json");
        configs.add("mixins.bugfixes.misc.crafteditemstatistics.json");
        configs.add("mixins.bugfixes.misc.enchantment.json");
        configs.add("mixins.bugfixes.misc.packetsize.json");
        configs.add("mixins.bugfixes.misc.particlespawning.json");
        configs.add("mixins.bugfixes.world.chunksaving.json");
        configs.add("mixins.bugfixes.world.tileentities.json");
        configs.add("mixins.bugfixes.world.witchhuts.json");
        configs.add("mixins.tweaks.blocks.bedobstruction.json");
        configs.add("mixins.tweaks.blocks.breakablebedrock.json");
        configs.add("mixins.tweaks.blocks.growthsize.json");
        configs.add("mixins.tweaks.blocks.hitdelay.json");
        configs.add("mixins.tweaks.blocks.leafdecay.json");
        configs.add("mixins.tweaks.blocks.lenientpaths.json");
        configs.add("mixins.tweaks.blocks.overhaulbeacon.json");
        configs.add("mixins.tweaks.blocks.sapling.json");
        configs.add("mixins.tweaks.entities.ai.json");
        configs.add("mixins.tweaks.entities.ai.saddledwandering.json");
        configs.add("mixins.tweaks.entities.ai.wither.json");
        configs.add("mixins.tweaks.entities.armedarmorstands.json");
        configs.add("mixins.tweaks.entities.burning.horse.json");
        configs.add("mixins.tweaks.entities.burning.zombie.json");
        configs.add("mixins.tweaks.entities.damage.arrow.json");
        configs.add("mixins.tweaks.entities.damage.collision.json");
        configs.add("mixins.tweaks.entities.damage.falling.json");
        configs.add("mixins.tweaks.entities.damage.velocity.json");
        configs.add("mixins.tweaks.entities.despawning.json");
        configs.add("mixins.tweaks.entities.loot.json");
        configs.add("mixins.tweaks.entities.saturation.json");
        configs.add("mixins.tweaks.entities.spawning.caps.json");
        configs.add("mixins.tweaks.entities.spawning.creeper.confetti.json");
        configs.add("mixins.tweaks.entities.spawning.golem.json");
        configs.add("mixins.tweaks.entities.spawning.husk.json");
        configs.add("mixins.tweaks.entities.spawning.portal.json");
        configs.add("mixins.tweaks.entities.spawning.stray.json");
        configs.add("mixins.tweaks.entities.speed.boat.json");
        configs.add("mixins.tweaks.entities.speed.player.json");
        configs.add("mixins.tweaks.entities.taming.horse.json");
        configs.add("mixins.tweaks.entities.trading.json");
        configs.add("mixins.tweaks.items.attackcooldown.server.json");
        configs.add("mixins.tweaks.items.eating.json");
        configs.add("mixins.tweaks.items.hardcorebuckets.json");
        configs.add("mixins.tweaks.items.itementities.server.json");
        configs.add("mixins.tweaks.items.rarity.json");
        configs.add("mixins.tweaks.items.repairing.json");
        configs.add("mixins.tweaks.items.xpbottle.json");
        configs.add("mixins.tweaks.misc.advancements.json");
        configs.add("mixins.tweaks.misc.armorcurve.json");
        configs.add("mixins.tweaks.misc.bannerlayers.json");
        configs.add("mixins.tweaks.misc.console.addpacket.json");
        configs.add("mixins.tweaks.misc.incurablepotions.json");
        configs.add("mixins.tweaks.misc.lightning.damage.json");
        configs.add("mixins.tweaks.misc.lightning.fire.json");
        configs.add("mixins.tweaks.misc.xp.cap.json");
        configs.add("mixins.tweaks.misc.xp.linear.json");
        configs.add("mixins.tweaks.misc.xp.smelting.json");
        configs.add("mixins.tweaks.misc.recipebook.server.json");
        configs.add("mixins.tweaks.performance.advancementcheck.json");
        configs.add("mixins.tweaks.performance.autosave.json");
        configs.add("mixins.tweaks.performance.craftingcache.json");
        configs.add("mixins.tweaks.performance.dyeblending.json");
        configs.add("mixins.tweaks.performance.oredictionarycheck.json");
        configs.add("mixins.tweaks.performance.prefixcheck.json");
        configs.add("mixins.tweaks.performance.redstone.json");
        configs.add("mixins.tweaks.performance.texturemapcheck.json");
        configs.add("mixins.tweaks.world.chunks.gen.json");
        configs.add("mixins.tweaks.world.loading.server.json");
        configs.add("mixins.tweaks.world.sealevel.json");
        configs.add("mixins.tweaks.misc.chat.compactmessage.json");
        configs.add("mixins.tweaks.world.village.json");
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (isDev)
        {
            // Causes crashes in dev env only
            if (mixinConfig.equals("mixins.tweaks.misc.armorcurve.json")) return false;
            return true;
        }
        if (isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.bugfixes.blocks.banner.json":
                    return UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle && !renderLibLoaded;
                case "mixins.bugfixes.blocks.blockoverlay.json":
                    return UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle;
                case "mixins.bugfixes.blocks.miningglitch.client.json":
                    return UTConfigBugfixes.BLOCKS.utMiningGlitchToggle;
                case "mixins.bugfixes.entities.elytra.json":
                    return UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle;
                case "mixins.bugfixes.entities.villagermantle.json":
                    return UTConfigBugfixes.ENTITIES.utVillagerMantleToggle;
                case "mixins.bugfixes.misc.depthmask.json":
                    return UTConfigBugfixes.MISC.utDepthMaskToggle;
                case "mixins.bugfixes.misc.modelgap.json":
                    return UTConfigBugfixes.MISC.MODEL_GAP.utModelGapToggle;
                case "mixins.bugfixes.misc.potionamplifier.json":
                    return UTConfigBugfixes.MISC.utPotionAmplifierVisibilityToggle;
                case "mixins.bugfixes.misc.smoothlighting.json":
                    return UTConfigBugfixes.MISC.utAccurateSmoothLighting;
                case "mixins.bugfixes.misc.spectatormenu.json":
                    return UTConfigBugfixes.MISC.utSpectatorMenuToggle;
                case "mixins.bugfixes.misc.startup.json":
                    return UTConfigTweaks.PERFORMANCE.utFasterBackgroundStartupToggle;
                case "mixins.bugfixes.world.frustumculling.json":
                    return UTConfigBugfixes.WORLD.utFrustumCullingToggle;
                case "mixins.tweaks.entities.autojump.json":
                    return UTConfigTweaks.ENTITIES.utAutoJumpToggle;
                case "mixins.tweaks.items.attackcooldown.client.json":
                    return UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle;
                case "mixins.tweaks.items.itementities.client.json":
                    return UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle;
                case "mixins.tweaks.misc.buttons.realms.json":
                    return UTConfigTweaks.MISC.utRealmsButtonToggle && !randomPatchesLoaded;
                case "mixins.tweaks.misc.buttons.snooper.client.json":
                    return UTConfigTweaks.MISC.utSnooperToggle;
                case "mixins.tweaks.misc.commands.seed.json":
                    return UTConfigTweaks.MISC.utCopyWorldSeedToggle;
                case "mixins.tweaks.misc.credits.json":
                    return UTConfigTweaks.MISC.utSkipCreditsToggle;
                case "mixins.tweaks.misc.gui.lanserverproperties.json":
                    return UTConfigTweaks.MISC.utLANServerProperties;
                case "mixins.tweaks.misc.gui.overlaymessage.json":
                    return UTConfigTweaks.MISC.utOverlayMessageHeight != -4;
                case "mixins.tweaks.misc.gui.selecteditemtooltip.json":
                    return UTConfigTweaks.MISC.utSelectedItemTooltipHeight != 59;
                case "mixins.tweaks.misc.lightning.flash.json":
                    return UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle;
                case "mixins.tweaks.misc.music.json":
                    return UTConfigTweaks.MISC.utInfiniteMusicToggle;
                case "mixins.tweaks.misc.narrator.json":
                    return UTConfigTweaks.MISC.utDisableNarratorToggle;
                case "mixins.tweaks.misc.nightvisionflash.json":
                    return UTConfigTweaks.MISC.utNightVisionFlashToggle;
                case "mixins.tweaks.misc.recipebook.client.json":
                    return UTConfigTweaks.MISC.utRecipeBookToggle;
                case "mixins.tweaks.misc.smoothscrolling.json":
                    return UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle;
                case "mixins.tweaks.misc.toastcontrol.json":
                    return UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle;
                case "mixins.tweaks.performance.audioreload.json":
                    return UTConfigTweaks.PERFORMANCE.utDisableAudioDebugToggle && !surgeLoaded;
                case "mixins.tweaks.performance.fps.json":
                    return UTConfigTweaks.PERFORMANCE.utUncapFPSToggle;
                case "mixins.tweaks.performance.missingmodel.json":
                    return UTConfigTweaks.PERFORMANCE.utDisableFancyMissingModelToggle;
                case "mixins.tweaks.performance.resourcemanager.json":
                    return UTConfigTweaks.PERFORMANCE.utCheckAnimatedModelsToggle;
                case "mixins.tweaks.world.loading.client.json":
                    return UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blocks.comparatortiming.json":
                return UTConfigBugfixes.BLOCKS.utComparatorTimingToggle;
            case "mixins.bugfixes.blocks.fallingblockdamage.json":
                return UTConfigBugfixes.BLOCKS.utFallingBlockDamageToggle;
            case "mixins.bugfixes.blocks.hopper.boundingbox.json":
                return UTConfigBugfixes.BLOCKS.utDietHopperToggle;
            case "mixins.bugfixes.blocks.hopper.tile.json":
                return UTConfigBugfixes.BLOCKS.utHopperInsertToggle;
            case "mixins.bugfixes.blocks.itemframevoid.json":
                return UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle;
            case "mixins.bugfixes.blocks.ladderflying.json":
                return UTConfigBugfixes.BLOCKS.utLadderFlyingToggle;
            case "mixins.bugfixes.blocks.miningglitch.server.json":
                return UTConfigBugfixes.BLOCKS.utMiningGlitchToggle;
            case "mixins.bugfixes.blocks.piston.tile.json":
                return UTConfigBugfixes.BLOCKS.utPistonTileToggle;
            case "mixins.bugfixes.blocks.piston.retraction.json":
                return UTConfigBugfixes.BLOCKS.utPistonRetractionToggle;
            case "mixins.bugfixes.blocks.bed.json":
                return UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle;
            case "mixins.bugfixes.misc.crafteditemstatistics.json":
                return UTConfigBugfixes.MISC.utCraftedItemStatisticsToggle;
            case "mixins.bugfixes.misc.enchantment.json":
                return UTConfigBugfixes.MISC.utBlastProtectionKnockbackToggle;
            case "mixins.bugfixes.misc.packetsize.json":
                return UTConfigBugfixes.MISC.utPacketSize > 0x200000 && !spongeForgeLoaded && !randomPatchesLoaded;
            case "mixins.tweaks.misc.particlespawning.json":
                return UTConfigBugfixes.MISC.utParticleSpawningToggle;
            case "mixins.bugfixes.entities.ai.json":
                return UTConfigBugfixes.ENTITIES.utEntityAITasksToggle;
            case "mixins.bugfixes.entities.attackradius.json":
                return UTConfigBugfixes.ENTITIES.utAttackRadiusToggle;
            case "mixins.bugfixes.entities.blockfire.json":
                return UTConfigBugfixes.ENTITIES.utBlockFireToggle;
            case "mixins.bugfixes.entities.boatoffset.json":
                return UTConfigBugfixes.ENTITIES.utBoatOffsetToggle;
            case "mixins.bugfixes.entities.boundingbox.json":
                return UTConfigBugfixes.ENTITIES.utEntityAABBToggle;
            case "mixins.bugfixes.entities.deathtime.json":
                return UTConfigBugfixes.ENTITIES.utDeathTimeToggle;
            case "mixins.bugfixes.entities.destroypacket.json":
                return UTConfigBugfixes.ENTITIES.utDestroyPacketToggle;
            case "mixins.bugfixes.entities.desync.json":
                return UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle;
            case "mixins.bugfixes.entities.dimensionchange.json":
                return UTConfigBugfixes.ENTITIES.utDimensionChangeToggle;
            case "mixins.bugfixes.entities.disconnectdupe.json":
                return UTConfigBugfixes.ENTITIES.utDisconnectDupeToggle;
            case "mixins.bugfixes.entities.entityid.json":
                return UTConfigBugfixes.ENTITIES.utEntityIDToggle;
            case "mixins.bugfixes.entities.entitylists.json":
                return UTConfigBugfixes.ENTITIES.utEntityListsToggle;
            case "mixins.bugfixes.entities.horsefalling.json":
                return UTConfigBugfixes.ENTITIES.utHorseFallingToggle;
            case "mixins.bugfixes.entities.maxhealth.json":
                return UTConfigBugfixes.ENTITIES.utMaxHealthToggle;
            case "mixins.bugfixes.entities.minecart.json":
                return UTConfigBugfixes.ENTITIES.utMinecartAIToggle;
            case "mixins.bugfixes.entities.mount.json":
                return UTConfigBugfixes.ENTITIES.utMountDesyncToggle;
            case "mixins.bugfixes.entities.saturation.json":
                return UTConfigBugfixes.ENTITIES.utExhaustionToggle;
            case "mixins.bugfixes.entities.skeletonaim.json":
                return UTConfigBugfixes.ENTITIES.utSkeletonAimToggle;
            case "mixins.bugfixes.entities.suffocation.json":
                return UTConfigBugfixes.ENTITIES.utEntitySuffocationToggle;
            case "mixins.bugfixes.entities.tracker.json":
                return UTConfigBugfixes.ENTITIES.utEntityTrackerToggle && !spongeForgeLoaded;
            case "mixins.bugfixes.world.chunksaving.json":
                return UTConfigBugfixes.WORLD.utChunkSavingToggle && !spongeForgeLoaded;
            case "mixins.bugfixes.world.tileentities.json":
                return UTConfigBugfixes.WORLD.utTileEntityMap != UTConfigBugfixes.WorldCategory.EnumMaps.HASHMAP;
            case "mixins.bugfixes.world.witchhuts.json":
                return UTConfigBugfixes.WORLD.utWitchStructuresToggle;
            case "mixins.tweaks.blocks.bedobstruction.json":
                return UTConfigTweaks.BLOCKS.utBedObstructionToggle;
            case "mixins.tweaks.blocks.breakablebedrock.json":
                return UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle;
            case "mixins.tweaks.blocks.growthsize.json":
                return UTConfigTweaks.BLOCKS.utCactusSize != 3 && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3 && UTConfigTweaks.BLOCKS.utVineSize != 0;
            case "mixins.tweaks.blocks.hitdelay.json":
                return UTConfigTweaks.BLOCKS.utBlockHitDelay != 5;
            case "mixins.tweaks.blocks.leafdecay.json":
                return UTConfigTweaks.BLOCKS.utLeafDecayToggle;
            case "mixins.tweaks.blocks.lenientpaths.json":
                return UTConfigTweaks.BLOCKS.utLenientPathsToggle;
            case "mixins.tweaks.blocks.overhaulbeacon.json":
                return UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconToggle;
            case "mixins.tweaks.blocks.sapling.json":
                return UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingBehaviorToggle;
            case "mixins.tweaks.entities.ai.json":
                return UTConfigTweaks.ENTITIES.utAIReplacementToggle;
            case "mixins.tweaks.entities.ai.saddledwandering.json":
                return UTConfigTweaks.ENTITIES.utSaddledWanderingToggle;
            case "mixins.tweaks.entities.ai.wither.json":
                return UTConfigTweaks.ENTITIES.utWitherAIToggle;
            case "mixins.tweaks.entities.armedarmorstands.json":
                return UTConfigTweaks.ENTITIES.utArmedArmorStandsToggle;
            case "mixins.tweaks.entities.burning.horse.json":
                return UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle;
            case "mixins.tweaks.entities.burning.zombie.json":
                return UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle;
            case "mixins.tweaks.entities.damage.arrow.json":
                return UTConfigTweaks.ENTITIES.utCriticalArrowDamage != -1;
            case "mixins.tweaks.entities.damage.collision.json":
                return UTConfigTweaks.ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle;
            case "mixins.tweaks.entities.damage.falling.json":
                return UTConfigTweaks.ENTITIES.WATER_FALL_DAMAGE.utFallDamageToggle;
            case "mixins.tweaks.entities.damage.velocity.json":
                return UTConfigTweaks.ENTITIES.DAMAGE_VELOCITY.utDamageVelocityToggle;
            case "mixins.tweaks.entities.despawning.json":
                return UTConfigTweaks.ENTITIES.utMobDespawnToggle;
            case "mixins.tweaks.entities.loot.json":
                return UTConfigTweaks.ENTITIES.utCreeperMusicDiscsToggle;
            case "mixins.tweaks.entities.saturation.json":
                return UTConfigTweaks.ENTITIES.utRidingExhaustion != 0.0D;
            case "mixins.tweaks.entities.spawning.caps.json":
                return UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle;
            case "mixins.tweaks.entities.spawning.creeper.confetti.json":
                return UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance != 0.0D;
            case "mixins.tweaks.entities.spawning.golem.json":
                return UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGWitherToggle;
            case "mixins.tweaks.entities.spawning.husk.json":
            case "mixins.tweaks.entities.spawning.stray.json":
                return UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle;
            case "mixins.tweaks.entities.spawning.portal.json":
                return UTConfigTweaks.ENTITIES.utPortalSpawningToggle;
            case "mixins.tweaks.entities.speed.boat.json":
                return UTConfigTweaks.ENTITIES.utBoatSpeed != 0.04D;
            case "mixins.tweaks.entities.speed.player.json":
                return UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle;
            case "mixins.tweaks.entities.taming.horse.json":
                return UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
            case "mixins.tweaks.entities.trading.json":
                return UTConfigTweaks.ENTITIES.utVillagerTradeLevelingToggle || UTConfigTweaks.ENTITIES.utVillagerTradeRestockToggle;
            case "mixins.tweaks.items.attackcooldown.server.json":
                return UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle;
            case "mixins.tweaks.items.eating.json":
                return UTConfigTweaks.ITEMS.utAlwaysEatToggle;
            case "mixins.tweaks.items.hardcorebuckets.json":
                return UTConfigTweaks.ITEMS.utHardcoreBucketsToggle;
            case "mixins.tweaks.items.itementities.server.json":
                return UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle;
            case "mixins.tweaks.items.repairing.json":
                return UTConfigTweaks.ITEMS.utCraftingRepairToggle;
            case "mixins.tweaks.items.xpbottle.json":
                return UTConfigTweaks.ITEMS.utXPBottleAmount != -1;
            case "mixins.tweaks.misc.advancements.json":
                return UTConfigTweaks.MISC.utDisableAdvancementsToggle;
            case "mixins.tweaks.misc.armorcurve.json":
                return UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle;
            case "mixins.tweaks.misc.bannerlayers.json":
                return UTConfigTweaks.MISC.utBannerLayers != 6;
            case "mixins.tweaks.misc.buttons.snooper.server.json":
                return UTConfigTweaks.MISC.utSnooperToggle;
            case "mixins.tweaks.misc.console.addpacket.json":
                return UTConfigTweaks.MISC.utImprovedEntityTrackerToggle;
            case "mixins.tweaks.misc.lightning.damage.json":
                return UTConfigTweaks.MISC.LIGHTNING.utLightningDamage != 5.0D || UTConfigTweaks.MISC.LIGHTNING.utLightningFireTicks != 8;
            case "mixins.tweaks.misc.lightning.fire.json":
                return UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle;
            case "mixins.tweaks.misc.incurablepotions.json":
                return UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle;
            case "mixins.tweaks.misc.xp.cap.json":
                return UTConfigTweaks.MISC.utXPLevelCap > -1;
            case "mixins.tweaks.misc.xp.linear.json":
                return UTConfigTweaks.MISC.utLinearXP > 0;
            case "mixins.tweaks.misc.recipebook.server.json":
                return UTConfigTweaks.MISC.utRecipeBookToggle;
            case "mixins.tweaks.misc.xp.smelting.json":
                return UTConfigTweaks.MISC.utSmeltingXPToggle;
            case "mixins.tweaks.performance.advancementcheck.json":
                return UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle;
            case "mixins.tweaks.performance.autosave.json":
                return UTConfigTweaks.PERFORMANCE.utAutoSaveInterval != 900;
            case "mixins.tweaks.performance.craftingcache.json":
                return UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle;
            case "mixins.tweaks.performance.dyeblending.json":
                return UTConfigTweaks.PERFORMANCE.utDyeBlendingToggle;
            case "mixins.tweaks.performance.oredictionarycheck.json":
                return UTConfigTweaks.PERFORMANCE.utOreDictionaryCheckToggle;
            case "mixins.tweaks.performance.prefixcheck.json":
                return UTConfigTweaks.PERFORMANCE.utPrefixCheckToggle;
            case "mixins.tweaks.performance.redstone.json":
                return UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle;
            case "mixins.tweaks.performance.texturemapcheck.json":
                return UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle;
            case "mixins.tweaks.world.chunks.gen.json":
                return UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle;
            case "mixins.tweaks.world.loading.server.json":
                return UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle;
            case "mixins.tweaks.world.sealevel.json":
                return UTConfigTweaks.WORLD.utSeaLevel != 63;
            case "mixins.tweaks.world.village.json":
                return UTConfigTweaks.WORLD.utVillageDistance != 32;
        }
        return true;
    }
}