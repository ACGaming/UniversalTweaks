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
import mod.acgaming.universaltweaks.config.UTConfig;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static final boolean isDev = FMLLaunchHandler.isDeobfuscatedEnvironment();
    public static boolean spongePlayerList;
    public static boolean spongeAnvilChunkLoader;
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
            Class.forName("org.spongepowered.mod.mixin.core.server.management.PlayerListMixin_Forge");
            spongePlayerList = true;
        }
        catch (ClassNotFoundException ignored) {}

        try
        {
            Class.forName("org.spongepowered.common.mixin.core.world.chunk.storage.AnvilChunkLoaderMixin");
            spongeAnvilChunkLoader = true;
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
        return isClient ? Arrays.asList(
            "mixins.bugfixes.blocks.blockoverlay.json",
            "mixins.bugfixes.blocks.comparatortiming.json",
            "mixins.bugfixes.blocks.fallingblockdamage.json",
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
            "mixins.bugfixes.misc.packetsize.json",
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
            "mixins.tweaks.entities.ai.wither.json",
            "mixins.tweaks.entities.autojump.json",
            "mixins.tweaks.entities.burning.horses.json",
            "mixins.tweaks.entities.damage.arrow.json",
            "mixins.tweaks.entities.damage.collision.json",
            "mixins.tweaks.entities.damage.falling.json",
            "mixins.tweaks.entities.damage.velocity.json",
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
            "mixins.tweaks.items.attackcooldown.client.json",
            "mixins.tweaks.items.attackcooldown.server.json",
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
            "mixins.tweaks.misc.difficulty.client.json",
            "mixins.tweaks.misc.incurablepotions.json",
            "mixins.tweaks.misc.lightning.damage.json",
            "mixins.tweaks.misc.lightning.fire.json",
            "mixins.tweaks.misc.lightning.flash.json",
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
                "mixins.bugfixes.blocks.fallingblockdamage.json",
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
                "mixins.bugfixes.misc.packetsize.json",
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
                "mixins.tweaks.entities.ai.wither.json",
                "mixins.tweaks.entities.burning.horses.json",
                "mixins.tweaks.entities.damage.arrow.json",
                "mixins.tweaks.entities.damage.collision.json",
                "mixins.tweaks.entities.damage.falling.json",
                "mixins.tweaks.entities.damage.velocity.json",
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
                "mixins.tweaks.items.attackcooldown.server.json",
                "mixins.tweaks.items.hardcorebuckets.json",
                "mixins.tweaks.items.itementities.server.json",
                "mixins.tweaks.items.rarity.json",
                "mixins.tweaks.items.repairing.json",
                "mixins.tweaks.items.xpbottle.json",
                "mixins.tweaks.misc.buttons.snooper.server.json",
                "mixins.tweaks.misc.difficulty.server.json",
                "mixins.tweaks.misc.incurablepotions.json",
                "mixins.tweaks.misc.lightning.damage.json",
                "mixins.tweaks.misc.lightning.fire.json",
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
                        return UTConfig.TWEAKS_PERFORMANCE.utDisableAudioDebugToggle;
                    }
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
                    return UTConfig.TWEAKS_MISC.utRealmsButtonToggle;
                case "mixins.tweaks.misc.buttons.snooper.client.json":
                    return UTConfig.TWEAKS_MISC.utSnooperToggle;
                case "mixins.tweaks.misc.commands.seed.json":
                    return UTConfig.TWEAKS_MISC.utCopyWorldSeedToggle;
                case "mixins.tweaks.misc.credits.json":
                    return UTConfig.TWEAKS_MISC.utSkipCreditsToggle;
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
                case "mixins.tweaks.performance.fps.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utUncapFPSToggle;
                case "mixins.tweaks.performance.missingmodel.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utDisableFancyMissingModelToggle;
                case "mixins.tweaks.performance.resourcemanager.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utCheckAnimatedModelsToggle;
                case "mixins.tweaks.world.loading.client.json":
                    return UTConfig.TWEAKS_PERFORMANCE.utWorldLoadingToggle;
                default:
                    return true;
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
                return UTConfig.BUGFIXES_MISC.utPacketSize > 0x200000;
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
                return UTConfig.BUGFIXES_ENTITIES.utEntityTrackerToggle && !spongePlayerList;
            case "mixins.bugfixes.world.chunksaving.json":
                return UTConfig.BUGFIXES_WORLD.utChunkSavingToggle && !spongeAnvilChunkLoader;
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
            case "mixins.tweaks.entities.burning.horses.json":
                return UTConfig.TWEAKS_ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle;
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
            case "mixins.tweaks.entities.spawning.creepers.confetti.json":
                return UTConfig.TWEAKS_ENTITIES.utCreeperConfettiChance != 0.0D;
            case "mixins.tweaks.entities.spawning.golems.json":
                return UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfig.TWEAKS_ENTITIES.NO_GOLEMS.utNGWitherToggle;
            case "mixins.tweaks.entities.spawning.husk.json":
            case "mixins.tweaks.entities.spawning.stray.json":
                return UTConfig.TWEAKS_ENTITIES.utHuskStraySpawningToggle;
            case "mixins.tweaks.entities.speed.boat.json":
                return UTConfig.TWEAKS_ENTITIES.utBoatSpeed != 0.04D;
            case "mixins.tweaks.entities.speed.player.json":
                return UTConfig.TWEAKS_ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle;
            case "mixins.tweaks.entities.taming.horses.json":
                return UTConfig.TWEAKS_ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
            case "mixins.tweaks.items.attackcooldown.server.json":
                return UTConfig.TWEAKS_ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle;
            case "mixins.tweaks.items.hardcorebuckets.json":
                return UTConfig.TWEAKS_ITEMS.utHardcoreBucketsToggle;
            case "mixins.tweaks.items.itementities.server.json":
                return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utItemEntitiesToggle;
            case "mixins.tweaks.items.repairing.json":
                return UTConfig.TWEAKS_ITEMS.utCraftingRepairToggle;
            case "mixins.tweaks.items.xpbottle.json":
                return UTConfig.TWEAKS_ITEMS.utXPBottleAmount != -1;
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
            default:
                return true;
        }
    }
}