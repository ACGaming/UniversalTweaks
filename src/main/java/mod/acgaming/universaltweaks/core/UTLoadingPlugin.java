package mod.acgaming.universaltweaks.core;

import java.util.*;
import java.util.function.Predicate;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import mod.acgaming.universaltweaks.Tags;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTReflectionUtil;
import zone.rong.mixinbooter.Context;
import zone.rong.mixinbooter.IEarlyMixinLoader;

@IFMLLoadingPlugin.Name("UniversalTweaksCore")
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
@IFMLLoadingPlugin.SortingIndex(Integer.MIN_VALUE)
public class UTLoadingPlugin implements IFMLLoadingPlugin, IEarlyMixinLoader
{
    // Use this logger to avoid loading the main @Mod class just to be safe.
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME);
    public static final boolean isClient = FMLLaunchHandler.side().isClient();
    public static long launchTime;

    private static final Map<String, Predicate<Context>> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins/tweaks/mixins.misc.buttons.snooper.server.json", c -> UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins/tweaks/mixins.misc.difficulty.multiplayer.json", c -> true);
            }
        }
    });

    private static final Map<String, Predicate<Context>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins/mixins.vanilla.mod.accessors.json", c -> true);
            }

            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins/bugfixes/mixins.blocks.comparatortiming.json", c -> UTConfigBugfixes.BLOCKS.utComparatorTimingToggle);
                put("mixins/bugfixes/mixins.blocks.falling.json", c -> UTConfigBugfixes.BLOCKS.utFallingBlockDamageToggle);
                put("mixins/bugfixes/mixins.blocks.hopper.boundingbox.json", c -> UTConfigBugfixes.BLOCKS.utDietHopperToggle);
                put("mixins/bugfixes/mixins.blocks.hopper.tile.json", c -> UTConfigBugfixes.BLOCKS.utHopperInsertToggle);
                put("mixins/bugfixes/mixins.blocks.itemframevoid.json", c -> UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle);
                put("mixins/bugfixes/mixins.blocks.ladderflying.json", c -> UTConfigBugfixes.BLOCKS.utLadderFlyingToggle);
                put("mixins/bugfixes/mixins.blocks.miningglitch.server.json", c -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins/bugfixes/mixins.blocks.piston.retraction.json", c -> UTConfigBugfixes.BLOCKS.utPistonRetractionToggle);
                put("mixins/bugfixes/mixins.blocks.piston.tile.json", c -> UTConfigBugfixes.BLOCKS.utPistonTileToggle);
                put("mixins/bugfixes/mixins.entities.ai.json", c -> UTConfigBugfixes.ENTITIES.utEntityAITasksToggle);
                put("mixins/bugfixes/mixins.entities.attackradius.json", c -> UTConfigBugfixes.ENTITIES.utAttackRadiusToggle);
                put("mixins/bugfixes/mixins.entities.blockfire.json", c -> UTConfigBugfixes.ENTITIES.utBlockFireToggle);
                put("mixins/bugfixes/mixins.entities.boat.breaking.json", c -> UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight >= 0);
                put("mixins/bugfixes/mixins.entities.boat.offset.json", c -> UTConfigBugfixes.ENTITIES.utBoatOffsetToggle);
                put("mixins/bugfixes/mixins.entities.boundingbox.json", c -> UTConfigBugfixes.ENTITIES.utEntityAABBToggle);
                put("mixins/bugfixes/mixins.entities.deathtime.json", c -> UTConfigBugfixes.ENTITIES.utDeathTimeToggle);
                put("mixins/bugfixes/mixins.entities.destroypacket.json", c -> UTConfigBugfixes.ENTITIES.utDestroyPacketToggle);
                put("mixins/bugfixes/mixins.entities.desync.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle);
                put("mixins/bugfixes/mixins.entities.dimensionchange.json", c -> UTConfigBugfixes.ENTITIES.utDimensionChangeToggle);
                put("mixins/bugfixes/mixins.entities.entityid.json", c -> UTConfigBugfixes.ENTITIES.utEntityIDToggle);
                put("mixins/bugfixes/mixins.entities.entitylists.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle);
                put("mixins/bugfixes/mixins.entities.horsefalling.json", c -> UTConfigBugfixes.ENTITIES.utHorseFallingToggle);
                put("mixins/bugfixes/mixins.entities.maxhealth.json", c -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
                put("mixins/bugfixes/mixins.entities.minecart.json", c -> UTConfigBugfixes.ENTITIES.utMinecartAIToggle);
                put("mixins/bugfixes/mixins.entities.mount.json", c -> UTConfigBugfixes.ENTITIES.utMountDesyncToggle);
                put("mixins/bugfixes/mixins.entities.saturation.json", c -> UTConfigBugfixes.ENTITIES.utExhaustionToggle);
                put("mixins/bugfixes/mixins.entities.skeletonaim.json", c -> UTConfigBugfixes.ENTITIES.utSkeletonAimToggle);
                put("mixins/bugfixes/mixins.entities.sleeping.json", c -> UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle);
                put("mixins/bugfixes/mixins.entities.suffocation.json", c -> UTConfigBugfixes.ENTITIES.utEntitySuffocationToggle);
                put("mixins/bugfixes/mixins.entities.tracker.json", c -> UTConfigBugfixes.ENTITIES.utEntityTrackerToggle && !c.isModPresent("spongeforge"));
                put("mixins/bugfixes/mixins.entities.untippedarrowparticles.json", c -> UTConfigBugfixes.ENTITIES.utUntippedArrowParticlesToggle);
                put("mixins/bugfixes/mixins.misc.crafteditemstatistics.json", c -> UTConfigBugfixes.MISC.utCraftedItemStatisticsToggle);
                put("mixins/bugfixes/mixins.misc.packetsize.json", c -> UTConfigBugfixes.MISC.utPacketSize > 0x200000 && !c.isModPresent("spongeforge") && !c.isModPresent("randompatches"));
                put("mixins/bugfixes/mixins.misc.particlespawning.json", c -> UTConfigBugfixes.MISC.utParticleSpawningToggle);
                put("mixins/bugfixes/mixins.misc.durabilitycap.json", c -> UTConfigBugfixes.MISC.utExtendDurabilityCap);
                put("mixins/bugfixes/mixins.world.chunksaving.json", c -> UTConfigBugfixes.WORLD.utChunkSavingToggle && !c.isModPresent("spongeforge"));
                put("mixins/bugfixes/mixins.world.portal.json", c -> UTConfigBugfixes.WORLD.utPortalLocationLink);
                put("mixins/bugfixes/mixins.world.tileentities.json", c -> UTConfigBugfixes.WORLD.utTileEntityMap != UTConfigBugfixes.WorldCategory.EnumMaps.HASHMAP);
                put("mixins/bugfixes/mixins.world.village.json", c -> UTConfigBugfixes.WORLD.utVillageComponentPartsToggle);
                put("mixins/bugfixes/mixins.world.witchhut.json", c -> UTConfigBugfixes.WORLD.utWitchStructuresToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins/tweaks/mixins.blocks.anvil.json", c -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins/tweaks/mixins.blocks.barrier.json", c -> UTConfigTweaks.BLOCKS.utBarrierParticleDisplay);
                put("mixins/tweaks/mixins.blocks.bedobstruction.json", c -> UTConfigTweaks.BLOCKS.utBedObstructionToggle);
                put("mixins/tweaks/mixins.blocks.breakablebedrock.json", c -> UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle);
                put("mixins/tweaks/mixins.blocks.enchantmenttable.json", c -> UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle);
                put("mixins/tweaks/mixins.blocks.endcrystal.json", c -> UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementToggle);
                put("mixins/tweaks/mixins.blocks.endportal.json", c -> UTConfigTweaks.BLOCKS.utRenderEndPortalBottom);
                put("mixins/tweaks/mixins.blocks.explosion.json", c -> UTConfigTweaks.BLOCKS.utExplosionDropChance != 1.0D);
                put("mixins/tweaks/mixins.blocks.falling.json", c -> UTConfigTweaks.BLOCKS.utFallingBlockLifespan != 600);
                put("mixins/tweaks/mixins.blocks.golemstructure.json", c -> UTConfigTweaks.ENTITIES.utGolemPlacement);
                put("mixins/tweaks/mixins.blocks.growthsize.json", c -> UTConfigTweaks.BLOCKS.utCactusSize != 3 && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3 && UTConfigTweaks.BLOCKS.utVineSize != 0);
                put("mixins/tweaks/mixins.blocks.leafdecay.json", c -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
                put("mixins/tweaks/mixins.blocks.lenientpaths.json", c -> UTConfigTweaks.BLOCKS.utLenientPathsToggle);
                put("mixins/tweaks/mixins.blocks.observer.json", c -> UTConfigTweaks.BLOCKS.utPreventObserverActivatesOnPlacement);
                put("mixins/tweaks/mixins.blocks.overhaulbeacon.json", c -> UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconToggle);
                put("mixins/tweaks/mixins.blocks.piston.json", c -> UTConfigTweaks.BLOCKS.PISTON.utPistonBlockBlacklistToggle);
                put("mixins/tweaks/mixins.blocks.pumpkinplacing.json", c -> UTConfigTweaks.BLOCKS.utUnsupportedPumpkinPlacing);
                put("mixins/tweaks/mixins.blocks.sapling.json", c -> UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingBehaviorToggle);
                put("mixins/tweaks/mixins.blocks.witherstructure.json", c -> UTConfigTweaks.ENTITIES.utWitherPlacement);
                put("mixins/tweaks/mixins.entities.ai.json", c -> UTConfigTweaks.ENTITIES.utAIReplacementToggle);
                put("mixins/tweaks/mixins.entities.ai.saddledwandering.json", c -> UTConfigTweaks.ENTITIES.utSaddledWanderingToggle);
                put("mixins/tweaks/mixins.entities.ai.wither.json", c -> UTConfigTweaks.ENTITIES.utWitherAIToggle);
                put("mixins/tweaks/mixins.entities.armedarmorstands.json", c -> UTConfigTweaks.ENTITIES.utArmedArmorStandsToggle);
                put("mixins/tweaks/mixins.entities.burning.horse.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle);
                put("mixins/tweaks/mixins.entities.burning.mobs.json", c -> UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle);
                put("mixins/tweaks/mixins.entities.damage.arrow.json", c -> UTConfigTweaks.ENTITIES.utCriticalArrowDamage != -1);
                put("mixins/tweaks/mixins.entities.damage.falling.json", c -> UTConfigTweaks.ENTITIES.WATER_FALL_DAMAGE.utFallDamageToggle);
                put("mixins/tweaks/mixins.entities.damage.velocity.json", c -> UTConfigTweaks.ENTITIES.DAMAGE_VELOCITY.utDamageVelocityToggle);
                put("mixins/tweaks/mixins.entities.despawning.json", c -> UTConfigTweaks.ENTITIES.utMobDespawningToggle != UTConfigTweaks.EnumMobDespawning.DEFAULT);
                put("mixins/tweaks/mixins.entities.loot.json", c -> UTConfigTweaks.ENTITIES.utCreeperMusicDiscsToggle);
                put("mixins/tweaks/mixins.entities.minecart.json", c -> UTConfigTweaks.ENTITIES.utMinecartDropsType);
                put("mixins/tweaks/mixins.entities.exhaustion.regen.json", c -> UTConfigTweaks.ENTITIES.utRegenExhaustion != 6.0D);
                put("mixins/tweaks/mixins.entities.exhaustion.riding.json", c -> UTConfigTweaks.ENTITIES.utRidingExhaustion != 0.0D);
                put("mixins/tweaks/mixins.entities.sleeping.json", c -> UTConfigTweaks.ENTITIES.SLEEPING.utEnableSleepingTweak);
                put("mixins/tweaks/mixins.entities.spawning.caps.json", c -> UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle);
                put("mixins/tweaks/mixins.entities.spawning.creeper.confetti.json", c -> UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance != 0.0D);
                put("mixins/tweaks/mixins.entities.spawning.golem.json", c -> UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGWitherToggle);
                put("mixins/tweaks/mixins.entities.spawning.husk.json", c -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins/tweaks/mixins.entities.spawning.portal.json", c -> UTConfigTweaks.ENTITIES.utPortalSpawningToggle);
                put("mixins/tweaks/mixins.entities.spawning.skeletontrap.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utSkeletonTrapSpawningToggle);
                put("mixins/tweaks/mixins.entities.spawning.stray.json", c -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins/tweaks/mixins.entities.speed.boat.json", c -> UTConfigTweaks.ENTITIES.utBoatSpeed != 0.04D);
                put("mixins/tweaks/mixins.entities.speed.cobweb.json", c -> UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessToggle);
                put("mixins/tweaks/mixins.entities.speed.player.json", c -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
                put("mixins/tweaks/mixins.entities.taming.horse.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle);
                put("mixins/tweaks/mixins.entities.trading.json", c -> UTConfigTweaks.ENTITIES.utVillagerTradeLevelingToggle || UTConfigTweaks.ENTITIES.utVillagerTradeRestockToggle);
                put("mixins/tweaks/mixins.entities.unsafesleeping.json", c -> UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utUnsafeSleepingToggle);
                put("mixins/tweaks/mixins.entities.villagerprofessions.json", c -> UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeRestriction.length > 0);
                put("mixins/tweaks/mixins.entities.voidteleport.json", c -> UTConfigTweaks.ENTITIES.VOID_TELEPORT.utVoidTeleportToggle);
                put("mixins/tweaks/mixins.items.attackcooldown.server.json", c -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins/tweaks/mixins.items.bottle.json", c -> UTConfigTweaks.ITEMS.utGlassBottlesConsumeWaterSource);
                put("mixins/tweaks/mixins.items.bucket.json", c -> UTConfigTweaks.ITEMS.utPreventBucketPlacingInPortal);
                put("mixins/tweaks/mixins.items.eating.json", c -> UTConfigTweaks.ITEMS.utAlwaysEatToggle || UTConfigTweaks.ITEMS.utSmartEatToggle);
                put("mixins/tweaks/mixins.items.eating.soupbowl.json", c -> UTConfigTweaks.ITEMS.utSoupBowlToggle);
                put("mixins/tweaks/mixins.items.hardcorebuckets.json", c -> UTConfigTweaks.ITEMS.utHardcoreBucketsToggle);
                put("mixins/tweaks/mixins.items.infinityallarrows.json", c -> UTConfigTweaks.ITEMS.INFINITY.utAllArrowsAreInfinite);
                put("mixins/tweaks/mixins.items.infinitymending.json", c -> UTConfigTweaks.ITEMS.INFINITY.utInfinityEnchantmentConflicts);
                put("mixins/tweaks/mixins.items.itementities.server.json", c -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins/tweaks/mixins.items.mobegg.json", c -> UTConfigTweaks.ITEMS.utPreventMobEggsFromChangingSpawner);
                put("mixins/tweaks/mixins.items.rarity.json", c -> UTConfigTweaks.ITEMS.utCustomRarities.length > 0);
                put("mixins/tweaks/mixins.items.repairing.json", c -> UTConfigTweaks.ITEMS.utCraftingRepairToggle);
                put("mixins/tweaks/mixins.items.xpbottle.json", c -> UTConfigTweaks.ITEMS.utXPBottleAmount != -1);
                put("mixins/tweaks/mixins.misc.advancements.json", c -> UTConfigTweaks.MISC.utDisableAdvancementsToggle);
                put("mixins/tweaks/mixins.misc.armorcurve.json", c -> !c.inDev() && UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle);
                put("mixins/tweaks/mixins.misc.armorswap.json", c -> UTConfigTweaks.MISC.utArmorSwap);
                put("mixins/tweaks/mixins.misc.bannerlayers.json", c -> UTConfigTweaks.MISC.utBannerLayers != 6);
                put("mixins/tweaks/mixins.misc.commands.seed.json", c -> UTConfigTweaks.MISC.utCopyWorldSeedToggle);
                put("mixins/tweaks/mixins.misc.difficulty.singleplayer.json", c -> true);
                put("mixins/tweaks/mixins.misc.incurablepotions.json", c -> UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle);
                put("mixins/tweaks/mixins.misc.lightning.damage.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningDamage != 5.0D || UTConfigTweaks.MISC.LIGHTNING.utLightningFireTicks != 8);
                put("mixins/tweaks/mixins.misc.lightning.fire.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle);
                put("mixins/tweaks/mixins.misc.recipebook.server.json", c -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins/tweaks/mixins.misc.sound.broadcast.dragon.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundDragon);
                put("mixins/tweaks/mixins.misc.sound.broadcast.endportal.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundEndPortal);
                put("mixins/tweaks/mixins.misc.sound.broadcast.wither.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundWither);
                put("mixins/tweaks/mixins.misc.timeouts.json", c -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins/tweaks/mixins.misc.xp.cap.json", c -> UTConfigTweaks.MISC.utXPLevelCap > -1);
                put("mixins/tweaks/mixins.misc.xp.drop.json", c -> UTConfigTweaks.MISC.utPlayerXPDropPercentage >= 0.0D);
                put("mixins/tweaks/mixins.misc.xp.linear.json", c -> UTConfigTweaks.MISC.utLinearXP > 0);
                put("mixins/tweaks/mixins.misc.xp.smelting.json", c -> UTConfigTweaks.MISC.utSmeltingXPToggle);
                put("mixins/tweaks/mixins.performance.advancements.logging.json", c -> UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle);
                put("mixins/tweaks/mixins.performance.advancements.triggers.json", c -> UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utFasterAdvancementTriggersToggle);
                put("mixins/tweaks/mixins.performance.autosave.json", c -> UTConfigTweaks.PERFORMANCE.utAutoSaveInterval != 900);
                put("mixins/tweaks/mixins.performance.craftingcache.json", c -> UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle);
                put("mixins/tweaks/mixins.performance.dyeblending.json", c -> UTConfigTweaks.PERFORMANCE.utDyeBlendingToggle);
                put("mixins/tweaks/mixins.performance.entityradiuscheck.lesscollisions.json", c -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle);
                put("mixins/tweaks/mixins.performance.entityradiuscheck.reducesearchsize.json", c -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle);
                put("mixins/tweaks/mixins.performance.pathfinding.json", c -> UTConfigTweaks.PERFORMANCE.utPathfindingChunkCacheFixToggle);
                put("mixins/tweaks/mixins.performance.prefixcheck.json", c -> UTConfigTweaks.PERFORMANCE.utPrefixCheckToggle);
                put("mixins/tweaks/mixins.performance.redstone.json", c -> UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle);
                put("mixins/tweaks/mixins.world.cavegen.json", c -> UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenToggle);
                put("mixins/tweaks/mixins.world.chunks.gen.json", c -> UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle);
                put("mixins/tweaks/mixins.world.chunks.spawn.json", c -> !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksGenToggle || !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksLoadingToggle);
                put("mixins/tweaks/mixins.world.sealevel.json", c -> UTConfigTweaks.WORLD.utSeaLevel != 63);
                put("mixins/tweaks/mixins.world.stronghold.json", c -> UTConfigTweaks.WORLD.utStrongholdToggle);
                put("mixins/tweaks/mixins.world.village.json", c -> UTConfigTweaks.WORLD.utVillageDistance != 32);
            }
        }
    });

    private static final Map<String, Predicate<Context>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins/bugfixes/mixins.blocks.banner.json", c -> UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle && !c.isModPresent("renderlib"));
                put("mixins/bugfixes/mixins.blocks.blockoverlay.json", c -> UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle);
                put("mixins/bugfixes/mixins.blocks.miningglitch.client.json", c -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins/bugfixes/mixins.entities.elytra.json", c -> UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle);
                put("mixins/bugfixes/mixins.entities.elytrarender.json", c -> UTConfigBugfixes.ENTITIES.utFixInvisiblePlayerModelWithElytra && !c.isModPresent("openmods"));
                put("mixins/bugfixes/mixins.entities.entitylists.client.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utWorldAdditionsToggle);
                put("mixins/bugfixes/mixins.entities.villagermantle.json", c -> UTConfigBugfixes.ENTITIES.utVillagerMantleToggle);
                put("mixins/bugfixes/mixins.misc.actionbar.json", c -> UTConfigBugfixes.MISC.utOverlayMessageFadeOut);
                put("mixins/bugfixes/mixins.misc.depthmask.json", c -> UTConfigBugfixes.MISC.utDepthMaskToggle);
                put("mixins/bugfixes/mixins.misc.potionamplifier.json", c -> UTConfigBugfixes.MISC.utPotionAmplifierVisibilityToggle);
                put("mixins/bugfixes/mixins.misc.smoothlighting.json", c -> UTConfigBugfixes.MISC.utAccurateSmoothLighting);
                put("mixins/bugfixes/mixins.misc.spectatormenu.json", c -> UTConfigBugfixes.MISC.utSpectatorMenuToggle);
                put("mixins/bugfixes/mixins.misc.startup.json", c -> UTConfigTweaks.PERFORMANCE.utFasterBackgroundStartupToggle);
                put("mixins/bugfixes/mixins.misc.itempickup.json", c -> UTConfigBugfixes.MISC.utItemPickupCulling);
                put("mixins/bugfixes/mixins.world.frustumculling.json", c -> UTConfigBugfixes.WORLD.utFrustumCullingToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins/tweaks/mixins.blocks.anvil.client.json", c -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins/tweaks/mixins.blocks.betterplacement.json", c -> UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle);
                put("mixins/tweaks/mixins.blocks.betterrailplacement.json", c -> UTConfigTweaks.BLOCKS.utBetterRailPlacementToggle);
                put("mixins/tweaks/mixins.blocks.hitdelay.json", c -> UTConfigTweaks.BLOCKS.utBlockHitDelay != 5);
                put("mixins/tweaks/mixins.entities.burning.player.json", c -> UTConfigTweaks.ENTITIES.utFirstPersonBurningOverlay != -0.3D);
                put("mixins/tweaks/mixins.entities.jumping.autojump.json", c -> UTConfigTweaks.ENTITIES.utAutoJumpToggle);
                put("mixins/tweaks/mixins.entities.playerdismount.json", c -> UTConfigTweaks.MISC.utUseSeparateDismountKey);
                put("mixins/tweaks/mixins.entities.playerf5.json", c -> UTConfigTweaks.ENTITIES.utThirdPersonIgnoresNonSolidBlocks);
                put("mixins/tweaks/mixins.entities.sprint.json", c -> UTConfigTweaks.ENTITIES.utSprintHungerThreshold != 6);
                put("mixins/tweaks/mixins.items.attackcooldown.client.json", c -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins/tweaks/mixins.items.itementities.client.json", c -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins/tweaks/mixins.misc.advancements.guisize.json", c -> UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle);
                put("mixins/tweaks/mixins.misc.buttons.anaglyph.json", c -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && !c.isModPresent("optifine"));
                put("mixins/tweaks/mixins.misc.buttons.anaglyph.optifine.json", c -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && c.isModPresent("optifine"));
                put("mixins/tweaks/mixins.misc.buttons.realms.json", c -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utRealmsButtonToggle && !c.isModPresent("randompatches"));
                put("mixins/tweaks/mixins.misc.buttons.snooper.client.json", c -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins/tweaks/mixins.misc.chat.bed.json", c -> UTConfigTweaks.MISC.CHAT.utKeepChatOpen);
                put("mixins/tweaks/mixins.misc.chat.compactmessage.json", c -> UTConfigTweaks.MISC.CHAT.utCompactMessagesToggle);
                put("mixins/tweaks/mixins.misc.chat.keepsentmessages.json", c -> UTConfigTweaks.MISC.CHAT.utKeepSentMessageHistory);
                put("mixins/tweaks/mixins.misc.chat.maximumlines.json", c -> UTConfigTweaks.MISC.CHAT.utChatLines != 100);
                put("mixins/tweaks/mixins.misc.credits.json", c -> UTConfigTweaks.MISC.utSkipCreditsToggle);
                put("mixins/tweaks/mixins.misc.glint.enchantedbook.json", c -> UTConfigTweaks.MISC.utDisableEnchantmentBookGlint);
                put("mixins/tweaks/mixins.misc.glint.potion.json", c -> UTConfigTweaks.MISC.utDisablePotionGlint);
                put("mixins/tweaks/mixins.misc.gui.defaultguitextcolor.json", c -> !UTConfigTweaks.MISC.utDefaultGuiTextColor.equals("404040"));
                put("mixins/tweaks/mixins.misc.gui.gamewindow.icon.json", c -> !c.inDev() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon16.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon32.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon256.isEmpty());
                put("mixins/tweaks/mixins.misc.gui.gamewindow.title.json", c -> !c.inDev() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowDisplayTitle.isEmpty());
                put("mixins/tweaks/mixins.misc.gui.keybindlistentry.json", c -> UTConfigTweaks.MISC.utPreventKeybindingEntryOverflow);
                put("mixins/tweaks/mixins.misc.gui.lanserverproperties.json", c -> UTConfigTweaks.MISC.utLANServerProperties);
                put("mixins/tweaks/mixins.misc.gui.modlist.json", c -> UTConfigTweaks.MISC.utForgeModListImprovements);
                put("mixins/tweaks/mixins.misc.gui.overlaymessage.json", c -> UTConfigTweaks.MISC.utOverlayMessageHeight != -4);
                put("mixins/tweaks/mixins.misc.gui.ping.json", c -> UTConfigTweaks.MISC.utBetterPing);
                put("mixins/tweaks/mixins.misc.gui.potionduration.json", c -> UTConfigTweaks.MISC.utPotionDurationToggle);
                put("mixins/tweaks/mixins.misc.gui.selecteditemtooltip.json", c -> UTConfigTweaks.MISC.utSelectedItemTooltipHeight != 59);
                put("mixins/tweaks/mixins.misc.gui.textshadow.json", c -> UTConfigTweaks.MISC.utDisableTextShadow);
                put("mixins/tweaks/mixins.misc.hotbarscroll.json", c -> UTConfigTweaks.MISC.utDisableHotbarScrollWrapping);
                put("mixins/tweaks/mixins.misc.lightning.flash.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle);
                put("mixins/tweaks/mixins.misc.gui.mainmenu.json", c -> UTConfigTweaks.MISC.utReturnToMainMenu);
                put("mixins/tweaks/mixins.misc.music.json", c -> UTConfigTweaks.MISC.MUSIC.utMusicControlToggle);
                put("mixins/tweaks/mixins.misc.narrator.json", c -> UTConfigTweaks.MISC.utDisableNarratorToggle);
                put("mixins/tweaks/mixins.misc.narratorkeybind.json", c -> UTConfigTweaks.MISC.utUseCustomNarratorKeybind);
                put("mixins/tweaks/mixins.misc.nightvisionflash.json", c -> UTConfigTweaks.MISC.utNightVisionFlashToggle);
                put("mixins/tweaks/mixins.misc.particlelimit.json", c -> UTConfigTweaks.MISC.utParticleLimit > 0);
                put("mixins/tweaks/mixins.misc.personalpotionparticles.json", c -> UTConfigTweaks.MISC.utPoVEffectParticles);
                put("mixins/tweaks/mixins.misc.recipebook.client.json", c -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins/tweaks/mixins.misc.smoothscrolling.json", c -> UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle);
                put("mixins/tweaks/mixins.misc.sound.pitch.json", c -> UTConfigTweaks.MISC.utUnlimitedSoundPitchRange);
                put("mixins/tweaks/mixins.misc.timeouts.client.json", c -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins/tweaks/mixins.misc.toastcontrol.json", c -> UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle);
                put("mixins/tweaks/mixins.misc.viewbobbing.json", c -> true);
                put("mixins/tweaks/mixins.performance.audioreload.json", c -> UTConfigTweaks.PERFORMANCE.utDisableAudioDebugToggle && !c.isModPresent("surge"));
                put("mixins/tweaks/mixins.performance.connectionspeed.json", c -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins/tweaks/mixins.performance.fps.json", c -> UTConfigTweaks.PERFORMANCE.utUncapFPSToggle);
                put("mixins/tweaks/mixins.performance.languageswitching.json", c -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins/tweaks/mixins.performance.missingmodel.json", c -> UTConfigTweaks.PERFORMANCE.utDisableFancyMissingModelToggle && !UTReflectionUtil.isClassLoaded("com.hbm.lib.RefStrings"));
                put("mixins/tweaks/mixins.performance.mobspawnerrender.json", c -> UTConfigTweaks.PERFORMANCE.utDisableMobSpawnerRendering);
                put("mixins/tweaks/mixins.performance.resourcemanager.json", c -> UTConfigTweaks.PERFORMANCE.utCheckAnimatedModelsToggle);
                put("mixins/tweaks/mixins.performance.textureatlas.json", c -> UTConfigTweaks.PERFORMANCE.utTextureAtlasToggle && !UTReflectionUtil.isClassLoaded("dev.redstudio.valkyrie.utils.ValkyrieUtils"));
                put("mixins/tweaks/mixins.performance.texturemapcheck.json", c -> UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle);
                put("mixins/tweaks/mixins.performance.weathereffects.json", c -> UTConfigTweaks.PERFORMANCE.utDisableRainParticles);
                put("mixins/tweaks/mixins.world.loading.client.json", c -> UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle);
                put("mixins/tweaks/mixins.world.voidfog.json", c -> UTConfigTweaks.WORLD.VOID_FOG.utVoidFogToggle);
            }
        }
    });

    static
    {
        if (UTConfigGeneral.DEBUG.utLoadingTimeToggle) launchTime = System.currentTimeMillis();
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes && UTConfigBugfixes.MISC.utLocaleToggle && Locale.getDefault().getLanguage().equals("tr"))
        {
            LOGGER.info("The locale is Turkish, which is unfortunately not supported by some mods. Changing to English...");
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
        List<String> configs = new ArrayList<>();
        if (isClient) configs.addAll(clientsideMixinConfigs.keySet());
        else configs.addAll(serversideMixinConfigs.keySet());
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(Context context)
    {
        Coremods.initFromContext(context);

        String mixinConfig = context.mixinConfig();
        Predicate<Context> sidedPredicate = UTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : serversideMixinConfigs.get(mixinConfig);
        Predicate<Context> commonPredicate = commonMixinConfigs.get(mixinConfig);
        return sidedPredicate != null ? sidedPredicate.test(context) : commonPredicate == null || commonPredicate.test(context);
    }
}