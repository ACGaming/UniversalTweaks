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
import mod.acgaming.universaltweaks.config.UTConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static final boolean isDev = FMLLaunchHandler.isDeobfuscatedEnvironment();
    public static boolean randomPatchesLoaded;
    public static boolean spongeForgeLoaded;
    public static boolean surgeLoaded;
    public static long launchTime;

    static
    {
        if (UTConfig.DEBUG.utLoadingTimeToggle) launchTime = System.currentTimeMillis();
        if (UTConfig.BUGFIXES_MISC.utLocaleToggle && Locale.getDefault().getLanguage().equals("tr"))
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
            configs.add("mixins.bugfixes.misc.smoothlighting.json");
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
        configs.add("mixins.bugfixes.blocks.comparatortiming.json");
        configs.add("mixins.bugfixes.blocks.fallingblockdamage.json");
        configs.add("mixins.bugfixes.blocks.hopper.boundingbox.json");
        configs.add("mixins.bugfixes.blocks.hopper.tile.json");
        configs.add("mixins.bugfixes.blocks.itemframevoid.json");
        configs.add("mixins.bugfixes.blocks.ladderflying.json");
        configs.add("mixins.bugfixes.blocks.miningglitch.server.json");
        configs.add("mixins.bugfixes.blocks.pistontile.json");
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
        configs.add("mixins.bugfixes.entities.horsefalling.json");
        configs.add("mixins.bugfixes.entities.maxhealth.json");
        configs.add("mixins.bugfixes.entities.mount.json");
        configs.add("mixins.bugfixes.entities.saturation.json");
        configs.add("mixins.bugfixes.entities.skeletonaim.json");
        configs.add("mixins.bugfixes.entities.suffocation.json");
        configs.add("mixins.bugfixes.entities.tracker.json");
        configs.add("mixins.bugfixes.misc.packetsize.json");
        configs.add("mixins.bugfixes.world.chunksaving.json");
        configs.add("mixins.bugfixes.world.tileentities.json");
        configs.add("mixins.tweaks.blocks.bedobstruction.json");
        configs.add("mixins.tweaks.blocks.breakablebedrock.json");
        configs.add("mixins.tweaks.blocks.growthsize.json");
        configs.add("mixins.tweaks.blocks.hitdelay.json");
        configs.add("mixins.tweaks.blocks.leafdecay.json");
        configs.add("mixins.tweaks.blocks.lenientpaths.json");
        configs.add("mixins.tweaks.entities.ai.json");
        configs.add("mixins.tweaks.entities.ai.saddledwandering.json");
        configs.add("mixins.tweaks.entities.ai.wither.json");
        configs.add("mixins.tweaks.entities.burning.horse.json");
        configs.add("mixins.tweaks.entities.burning.zombie.json");
        configs.add("mixins.tweaks.entities.damage.arrow.json");
        configs.add("mixins.tweaks.entities.damage.collision.json");
        configs.add("mixins.tweaks.entities.damage.falling.json");
        configs.add("mixins.tweaks.entities.damage.velocity.json");
        configs.add("mixins.tweaks.entities.despawning.json");
        configs.add("mixins.tweaks.entities.loot.json");
        configs.add("mixins.tweaks.entities.spawning.caps.json");
        configs.add("mixins.tweaks.entities.spawning.creeper.confetti.json");
        configs.add("mixins.tweaks.entities.spawning.golem.json");
        configs.add("mixins.tweaks.entities.spawning.husk.json");
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
        configs.add("mixins.tweaks.misc.armorcurve.json");
        configs.add("mixins.tweaks.misc.incurablepotions.json");
        configs.add("mixins.tweaks.misc.lightning.damage.json");
        configs.add("mixins.tweaks.misc.lightning.fire.json");
        configs.add("mixins.tweaks.misc.xp.linear.json");
        configs.add("mixins.tweaks.misc.xp.smelting.json");
        configs.add("mixins.tweaks.misc.recipebook.server.json");
        configs.add("mixins.tweaks.performance.autosave.json");
        configs.add("mixins.tweaks.performance.craftingcache.json");
        configs.add("mixins.tweaks.performance.dyeblending.json");
        configs.add("mixins.tweaks.performance.prefixcheck.json");
        configs.add("mixins.tweaks.performance.redstone.json");
        configs.add("mixins.tweaks.world.chunks.gen.json");
        configs.add("mixins.tweaks.world.loading.server.json");
        configs.add("mixins.tweaks.world.sealevel.json");
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (isDev) return true;
        if (isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.bugfixes.blocks.banner.json":
                    return UTConfig.BUGFIXES_BLOCKS.utBannerBoundingBoxToggle;
                case "mixins.bugfixes.blocks.blockoverlay.json":
                    return UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle;
                case "mixins.bugfixes.blocks.miningglitch.client.json":
                    return UTConfig.BUGFIXES_BLOCKS.utMiningGlitchToggle;
                case "mixins.bugfixes.entities.elytra.json":
                    return UTConfig.BUGFIXES_ENTITIES.utElytraDeploymentLandingToggle;
                case "mixins.bugfixes.entities.villagermantle.json":
                    return UTConfig.BUGFIXES_ENTITIES.utVillagerMantleToggle;
                case "mixins.bugfixes.misc.depthmask.json":
                    return UTConfig.BUGFIXES_MISC.utDepthMaskToggle;
                case "mixins.bugfixes.misc.modelgap.json":
                    return UTConfig.BUGFIXES_MISC.MODEL_GAP.utModelGapToggle;
                case "mixins.bugfixes.misc.smoothlighting.json":
                    return UTConfig.BUGFIXES_MISC.utAccurateSmoothLighting;
                case "mixins.bugfixes.misc.startup.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utFasterBackgroundStartupToggle;
                case "mixins.bugfixes.world.frustumculling.json":
                    return UTConfig.BUGFIXES_WORLD.utFrustumCullingToggle;
                case "mixins.tweaks.entities.autojump.json":
                    return UTConfig.TWEAKS_ENTITIES.utAutoJumpToggle;
                case "mixins.tweaks.items.attackcooldown.client.json":
                    return UTConfig.TWEAKS_ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle;
                case "mixins.tweaks.items.itementities.client.json":
                    return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utItemEntitiesToggle;
                case "mixins.tweaks.misc.buttons.realms.json":
                    return UTConfig.TWEAKS_MISC.utRealmsButtonToggle && !randomPatchesLoaded;
                case "mixins.tweaks.misc.buttons.snooper.client.json":
                    return UTConfig.TWEAKS_MISC.utSnooperToggle;
                case "mixins.tweaks.misc.commands.seed.json":
                    return UTConfig.TWEAKS_MISC.utCopyWorldSeedToggle;
                case "mixins.tweaks.misc.credits.json":
                    return UTConfig.TWEAKS_MISC.utSkipCreditsToggle;
                case "mixins.tweaks.misc.gui.overlaymessage.json":
                    return UTConfig.TWEAKS_MISC.utOverlayMessageHeight != -4;
                case "mixins.tweaks.misc.gui.selecteditemtooltip.json":
                    return UTConfig.TWEAKS_MISC.utSelectedItemTooltipHeight != 59;
                case "mixins.tweaks.misc.lightning.flash.json":
                    return UTConfig.TWEAKS_MISC.LIGHTNING.utLightningFlashToggle;
                case "mixins.tweaks.misc.music.json":
                    return UTConfig.TWEAKS_MISC.utInfiniteMusicToggle;
                case "mixins.tweaks.misc.narrator.json":
                    return UTConfig.TWEAKS_MISC.utDisableNarratorToggle;
                case "mixins.tweaks.misc.nightvisionflash.json":
                    return UTConfig.TWEAKS_MISC.utNightVisionFlashToggle;
                case "mixins.tweaks.misc.recipebook.client.json":
                    return UTConfig.TWEAKS_MISC.utRecipeBookToggle;
                case "mixins.tweaks.misc.smoothscrolling.json":
                    return UTConfig.TWEAKS_MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle;
                case "mixins.tweaks.misc.toastcontrol.json":
                    return UTConfig.TWEAKS_MISC.TOAST_CONTROL.utToastControlToggle;
                case "mixins.tweaks.performance.audioreload.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utDisableAudioDebugToggle && !surgeLoaded;
                case "mixins.tweaks.performance.fps.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utUncapFPSToggle;
                case "mixins.tweaks.performance.missingmodel.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utDisableFancyMissingModelToggle;
                case "mixins.tweaks.performance.resourcemanager.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utCheckAnimatedModelsToggle;
                case "mixins.tweaks.world.loading.client.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utWorldLoadingToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.bugfixes.blocks.comparatortiming.json":
                return UTConfig.BUGFIXES_BLOCKS.utComparatorTimingToggle;
            case "mixins.bugfixes.blocks.fallingblockdamage.json":
                return UTConfig.BUGFIXES_BLOCKS.utFallingBlockDamageToggle;
            case "mixins.bugfixes.blocks.hopper.boundingbox.json":
                return UTConfig.BUGFIXES_BLOCKS.utDietHopperToggle;
            case "mixins.bugfixes.blocks.hopper.tile.json":
                return UTConfig.BUGFIXES_BLOCKS.utHopperInsertToggle;
            case "mixins.bugfixes.blocks.itemframevoid.json":
                return UTConfig.BUGFIXES_BLOCKS.utItemFrameVoidToggle;
            case "mixins.bugfixes.blocks.ladderflying.json":
                return UTConfig.BUGFIXES_BLOCKS.utLadderFlyingToggle;
            case "mixins.bugfixes.blocks.miningglitch.server.json":
                return UTConfig.BUGFIXES_BLOCKS.utMiningGlitchToggle;
            case "mixins.bugfixes.blocks.pistontile.json":
                return UTConfig.BUGFIXES_BLOCKS.utPistonTileToggle;
            case "mixins.bugfixes.misc.packetsize.json":
                return UTConfig.BUGFIXES_MISC.utPacketSize > 0x200000 && !spongeForgeLoaded && !randomPatchesLoaded;
            case "mixins.bugfixes.entities.ai.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntityAITasksToggle;
            case "mixins.bugfixes.entities.attackradius.json":
                return UTConfig.BUGFIXES_ENTITIES.utAttackRadiusToggle;
            case "mixins.bugfixes.entities.blockfire.json":
                return UTConfig.BUGFIXES_ENTITIES.utBlockFireToggle;
            case "mixins.bugfixes.entities.boatoffset.json":
                return UTConfig.BUGFIXES_ENTITIES.utBoatOffsetToggle;
            case "mixins.bugfixes.entities.boundingbox.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntityAABBToggle;
            case "mixins.bugfixes.entities.deathtime.json":
                return UTConfig.BUGFIXES_ENTITIES.utDeathTimeToggle;
            case "mixins.bugfixes.entities.destroypacket.json":
                return UTConfig.BUGFIXES_ENTITIES.utDestroyPacketToggle;
            case "mixins.bugfixes.entities.desync.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntityDesyncToggle;
            case "mixins.bugfixes.entities.dimensionchange.json":
                return UTConfig.BUGFIXES_ENTITIES.utDimensionChangeToggle;
            case "mixins.bugfixes.entities.disconnectdupe.json":
                return UTConfig.BUGFIXES_ENTITIES.utDisconnectDupeToggle;
            case "mixins.bugfixes.entities.entityid.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntityIDToggle;
            case "mixins.bugfixes.entities.horsefalling.json":
                return UTConfig.BUGFIXES_ENTITIES.utHorseFallingToggle;
            case "mixins.bugfixes.entities.maxhealth.json":
                return UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle;
            case "mixins.bugfixes.entities.mount.json":
                return UTConfig.BUGFIXES_ENTITIES.utMountDesyncToggle;
            case "mixins.bugfixes.entities.saturation.json":
                return UTConfig.BUGFIXES_ENTITIES.utExhaustionToggle;
            case "mixins.bugfixes.entities.skeletonaim.json":
                return UTConfig.BUGFIXES_ENTITIES.utSkeletonAimToggle;
            case "mixins.bugfixes.entities.suffocation.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntitySuffocationToggle;
            case "mixins.bugfixes.entities.tracker.json":
                return UTConfig.BUGFIXES_ENTITIES.utEntityTrackerToggle && !spongeForgeLoaded;
            case "mixins.bugfixes.world.chunksaving.json":
                return UTConfig.BUGFIXES_WORLD.utChunkSavingToggle && !spongeForgeLoaded;
            case "mixins.bugfixes.world.tileentities.json":
                return UTConfig.BUGFIXES_WORLD.utTileEntityMap != UTConfig.BugfixesWorldCategory.EnumMaps.HASHMAP;
            case "mixins.tweaks.blocks.bedobstruction.json":
                return UTConfig.TWEAKS_BLOCKS.utBedObstructionToggle;
            case "mixins.tweaks.blocks.breakablebedrock.json":
                return UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle;
            case "mixins.tweaks.blocks.growthsize.json":
                return UTConfig.TWEAKS_BLOCKS.utCactusSize != 3 && UTConfig.TWEAKS_BLOCKS.utSugarCaneSize != 3 && UTConfig.TWEAKS_BLOCKS.utVineSize != 0;
            case "mixins.tweaks.blocks.hitdelay.json":
                return UTConfig.TWEAKS_BLOCKS.utBlockHitDelay != 5;
            case "mixins.tweaks.blocks.leafdecay.json":
                return UTConfig.TWEAKS_BLOCKS.utLeafDecayToggle;
            case "mixins.tweaks.blocks.lenientpaths.json":
                return UTConfig.TWEAKS_BLOCKS.utLenientPathsToggle;
            case "mixins.tweaks.entities.ai.json":
                return UTConfig.TWEAKS_ENTITIES.utAIReplacementToggle;
            case "mixins.tweaks.entities.ai.saddledwandering.json":
                return UTConfig.TWEAKS_ENTITIES.utSaddledWanderingToggle;
            case "mixins.tweaks.entities.ai.wither.json":
                return UTConfig.TWEAKS_ENTITIES.utWitherAIToggle;
            case "mixins.tweaks.entities.burning.horse.json":
                return UTConfig.TWEAKS_ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle;
            case "mixins.tweaks.entities.burning.zombie.json":
                return UTConfig.TWEAKS_ENTITIES.utBurningBabyZombiesToggle;
            case "mixins.tweaks.entities.damage.arrow.json":
                return UTConfig.TWEAKS_ENTITIES.utCriticalArrowDamage != -1;
            case "mixins.tweaks.entities.damage.collision.json":
                return UTConfig.TWEAKS_ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle;
            case "mixins.tweaks.entities.damage.falling.json":
                return UTConfig.TWEAKS_ENTITIES.WATER_FALL_DAMAGE.utFallDamageToggle;
            case "mixins.tweaks.entities.damage.velocity.json":
                return UTConfig.TWEAKS_ENTITIES.DAMAGE_VELOCITY.utDamageVelocityToggle;
            case "mixins.tweaks.entities.despawning.json":
                return UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle;
            case "mixins.tweaks.entities.loot.json":
                return UTConfig.TWEAKS_ENTITIES.utCreeperMusicDiscsToggle;
            case "mixins.tweaks.entities.spawning.caps.json":
                return UTConfig.TWEAKS_ENTITIES.SPAWN_CAPS.utSpawnCapsToggle;
            case "mixins.tweaks.entities.spawning.creeper.confetti.json":
                return UTConfig.TWEAKS_ENTITIES.utCreeperConfettiChance != 0.0D;
            case "mixins.tweaks.entities.spawning.golem.json":
                return UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGWitherToggle;
            case "mixins.tweaks.entities.spawning.husk.json":
            case "mixins.tweaks.entities.spawning.stray.json":
                return UTConfig.TWEAKS_ENTITIES.utHuskStraySpawningToggle;
            case "mixins.tweaks.entities.speed.boat.json":
                return UTConfig.TWEAKS_ENTITIES.utBoatSpeed != 0.04D;
            case "mixins.tweaks.entities.speed.player.json":
                return UTConfig.TWEAKS_ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle;
            case "mixins.tweaks.entities.taming.horse.json":
                return UTConfig.TWEAKS_ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
            case "mixins.tweaks.entities.trading.json":
                return UTConfig.TWEAKS_ENTITIES.utVillagerTradeLevelingToggle;
            case "mixins.tweaks.items.attackcooldown.server.json":
                return UTConfig.TWEAKS_ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle;
            case "mixins.tweaks.items.eating.json":
                return UTConfig.TWEAKS_ITEMS.utAlwaysEatToggle;
            case "mixins.tweaks.items.hardcorebuckets.json":
                return UTConfig.TWEAKS_ITEMS.utHardcoreBucketsToggle;
            case "mixins.tweaks.items.itementities.server.json":
                return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utItemEntitiesToggle;
            case "mixins.tweaks.items.repairing.json":
                return UTConfig.TWEAKS_ITEMS.utCraftingRepairToggle;
            case "mixins.tweaks.items.xpbottle.json":
                return UTConfig.TWEAKS_ITEMS.utXPBottleAmount != -1;
            case "mixins.tweaks.misc.armorcurve.json":
                return UTConfig.TWEAKS_MISC.ARMOR_CURVE.utArmorCurveToggle;
            case "mixins.tweaks.misc.buttons.snooper.server.json":
                return UTConfig.TWEAKS_MISC.utSnooperToggle;
            case "mixins.tweaks.misc.lightning.damage.json":
                return UTConfig.TWEAKS_MISC.LIGHTNING.utLightningDamage != 5.0D || UTConfig.TWEAKS_MISC.LIGHTNING.utLightningFireTicks != 8;
            case "mixins.tweaks.misc.lightning.fire.json":
                return UTConfig.TWEAKS_MISC.LIGHTNING.utLightningFireToggle;
            case "mixins.tweaks.misc.incurablepotions.json":
                return UTConfig.TWEAKS_MISC.INCURABLE_POTIONS.utIncurablePotionsToggle;
            case "mixins.tweaks.misc.xp.linear.json":
                return UTConfig.TWEAKS_MISC.utLinearXP != 0;
            case "mixins.tweaks.misc.recipebook.server.json":
                return UTConfig.TWEAKS_MISC.utRecipeBookToggle;
            case "mixins.tweaks.misc.xp.smelting.json":
                return UTConfig.TWEAKS_MISC.utSmeltingXPToggle;
            case "mixins.tweaks.performance.autosave.json":
                return UTConfig.TWEAKS_PERFORMANCE.utAutoSaveInterval != 900;
            case "mixins.tweaks.performance.craftingcache.json":
                return UTConfig.TWEAKS_PERFORMANCE.utCraftingCacheToggle;
            case "mixins.tweaks.performance.dyeblending.json":
                return UTConfig.TWEAKS_PERFORMANCE.utDyeBlendingToggle;
            case "mixins.tweaks.performance.prefixcheck.json":
                return UTConfig.TWEAKS_PERFORMANCE.utPrefixCheckToggle;
            case "mixins.tweaks.performance.redstone.json":
                return UTConfig.TWEAKS_PERFORMANCE.utRedstoneLightingToggle;
            case "mixins.tweaks.world.chunks.gen.json":
                return UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle;
            case "mixins.tweaks.world.loading.server.json":
                return UTConfig.TWEAKS_PERFORMANCE.utWorldLoadingToggle;
            case "mixins.tweaks.world.sealevel.json":
                return UTConfig.TWEAKS_WORLD.utSeaLevel != 63;
        }
        return true;
    }
}