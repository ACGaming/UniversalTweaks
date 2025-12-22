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
                put("mixins.tweaks.misc.buttons.snooper.server.json", c -> UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins.tweaks.misc.difficulty.multiplayer.json", c -> true);
            }
        }
    });

    private static final Map<String, Predicate<Context>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins.vanilla.mod.accessors.json", c -> true);
            }

            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins.bugfixes.blocks.comparatortiming.json", c -> UTConfigBugfixes.BLOCKS.utComparatorTimingToggle);
                put("mixins.bugfixes.blocks.falling.json", c -> UTConfigBugfixes.BLOCKS.utFallingBlockDamageToggle);
                put("mixins.bugfixes.blocks.hopper.boundingbox.json", c -> UTConfigBugfixes.BLOCKS.utDietHopperToggle);
                put("mixins.bugfixes.blocks.hopper.tile.json", c -> UTConfigBugfixes.BLOCKS.utHopperInsertToggle);
                put("mixins.bugfixes.blocks.itemframevoid.json", c -> UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle);
                put("mixins.bugfixes.blocks.ladderflying.json", c -> UTConfigBugfixes.BLOCKS.utLadderFlyingToggle);
                put("mixins.bugfixes.blocks.miningglitch.server.json", c -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins.bugfixes.blocks.piston.retraction.json", c -> UTConfigBugfixes.BLOCKS.utPistonRetractionToggle);
                put("mixins.bugfixes.blocks.piston.tile.json", c -> UTConfigBugfixes.BLOCKS.utPistonTileToggle);
                put("mixins.bugfixes.entities.ai.json", c -> UTConfigBugfixes.ENTITIES.utEntityAITasksToggle);
                put("mixins.bugfixes.entities.attackradius.json", c -> UTConfigBugfixes.ENTITIES.utAttackRadiusToggle);
                put("mixins.bugfixes.entities.blockfire.json", c -> UTConfigBugfixes.ENTITIES.utBlockFireToggle);
                put("mixins.bugfixes.entities.boat.breaking.json", c -> UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight >= 0);
                put("mixins.bugfixes.entities.boat.offset.json", c -> UTConfigBugfixes.ENTITIES.utBoatOffsetToggle);
                put("mixins.bugfixes.entities.boundingbox.json", c -> UTConfigBugfixes.ENTITIES.utEntityAABBToggle);
                put("mixins.bugfixes.entities.deathtime.json", c -> UTConfigBugfixes.ENTITIES.utDeathTimeToggle);
                put("mixins.bugfixes.entities.destroypacket.json", c -> UTConfigBugfixes.ENTITIES.utDestroyPacketToggle);
                put("mixins.bugfixes.entities.desync.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle);
                put("mixins.bugfixes.entities.dimensionchange.json", c -> UTConfigBugfixes.ENTITIES.utDimensionChangeToggle);
                put("mixins.bugfixes.entities.entityid.json", c -> UTConfigBugfixes.ENTITIES.utEntityIDToggle);
                put("mixins.bugfixes.entities.entitylists.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle);
                put("mixins.bugfixes.entities.horsefalling.json", c -> UTConfigBugfixes.ENTITIES.utHorseFallingToggle);
                put("mixins.bugfixes.entities.maxhealth.json", c -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
                put("mixins.bugfixes.entities.minecart.json", c -> UTConfigBugfixes.ENTITIES.utMinecartAIToggle);
                put("mixins.bugfixes.entities.mount.json", c -> UTConfigBugfixes.ENTITIES.utMountDesyncToggle);
                put("mixins.bugfixes.entities.saturation.json", c -> UTConfigBugfixes.ENTITIES.utExhaustionToggle);
                put("mixins.bugfixes.entities.skeletonaim.json", c -> UTConfigBugfixes.ENTITIES.utSkeletonAimToggle);
                put("mixins.bugfixes.entities.sleeping.json", c -> UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle);
                put("mixins.bugfixes.entities.suffocation.json", c -> UTConfigBugfixes.ENTITIES.utEntitySuffocationToggle);
                put("mixins.bugfixes.entities.tracker.json", c -> UTConfigBugfixes.ENTITIES.utEntityTrackerToggle && !c.isModPresent("spongeforge"));
                put("mixins.bugfixes.entities.untippedarrowparticles.json", c -> UTConfigBugfixes.ENTITIES.utUntippedArrowParticlesToggle);
                put("mixins.bugfixes.misc.crafteditemstatistics.json", c -> UTConfigBugfixes.MISC.utCraftedItemStatisticsToggle);
                put("mixins.bugfixes.misc.packetsize.json", c -> UTConfigBugfixes.MISC.utPacketSize > 0x200000 && !c.isModPresent("spongeforge") && !c.isModPresent("randompatches"));
                put("mixins.bugfixes.misc.particlespawning.json", c -> UTConfigBugfixes.MISC.utParticleSpawningToggle);
                put("mixins.bugfixes.misc.durabilitycap.json", c -> UTConfigBugfixes.MISC.utExtendDurabilityCap);
                put("mixins.bugfixes.world.chunksaving.json", c -> UTConfigBugfixes.WORLD.utChunkSavingToggle && !c.isModPresent("spongeforge"));
                put("mixins.bugfixes.world.portal.json", c -> UTConfigBugfixes.WORLD.utPortalLocationLink);
                put("mixins.bugfixes.world.tileentities.json", c -> UTConfigBugfixes.WORLD.utTileEntityMap != UTConfigBugfixes.WorldCategory.EnumMaps.HASHMAP);
                put("mixins.bugfixes.world.village.json", c -> UTConfigBugfixes.WORLD.utVillageComponentPartsToggle);
                put("mixins.bugfixes.world.witchhut.json", c -> UTConfigBugfixes.WORLD.utWitchStructuresToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins.tweaks.blocks.anvil.json", c -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins.tweaks.blocks.barrier.json", c -> UTConfigTweaks.BLOCKS.utBarrierParticleDisplay);
                put("mixins.tweaks.blocks.bedobstruction.json", c -> UTConfigTweaks.BLOCKS.utBedObstructionToggle);
                put("mixins.tweaks.blocks.breakablebedrock.json", c -> UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle);
                put("mixins.tweaks.blocks.enchantmenttable.json", c -> UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle);
                put("mixins.tweaks.blocks.endcrystal.json", c -> UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementToggle);
                put("mixins.tweaks.blocks.endportal.json", c -> UTConfigTweaks.BLOCKS.utRenderEndPortalBottom);
                put("mixins.tweaks.blocks.explosion.json", c -> UTConfigTweaks.BLOCKS.utExplosionDropChance != 1.0D);
                put("mixins.tweaks.blocks.falling.json", c -> UTConfigTweaks.BLOCKS.utFallingBlockLifespan != 600);
                put("mixins.tweaks.blocks.golemstructure.json", c -> UTConfigTweaks.ENTITIES.utGolemPlacement);
                put("mixins.tweaks.blocks.growthsize.json", c -> UTConfigTweaks.BLOCKS.utCactusSize != 3 && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3 && UTConfigTweaks.BLOCKS.utVineSize != 0);
                put("mixins.tweaks.blocks.leafdecay.json", c -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
                put("mixins.tweaks.blocks.lenientpaths.json", c -> UTConfigTweaks.BLOCKS.utLenientPathsToggle);
                put("mixins.tweaks.blocks.observer.json", c -> UTConfigTweaks.BLOCKS.utPreventObserverActivatesOnPlacement);
                put("mixins.tweaks.blocks.overhaulbeacon.json", c -> UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconToggle);
                put("mixins.tweaks.blocks.piston.json", c -> UTConfigTweaks.BLOCKS.PISTON.utPistonBlockBlacklistToggle);
                put("mixins.tweaks.blocks.pumpkinplacing.json", c -> UTConfigTweaks.BLOCKS.utUnsupportedPumpkinPlacing);
                put("mixins.tweaks.blocks.sapling.json", c -> UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingBehaviorToggle);
                put("mixins.tweaks.blocks.witherstructure.json", c -> UTConfigTweaks.ENTITIES.utWitherPlacement);
                put("mixins.tweaks.entities.ai.json", c -> UTConfigTweaks.ENTITIES.utAIReplacementToggle);
                put("mixins.tweaks.entities.ai.saddledwandering.json", c -> UTConfigTweaks.ENTITIES.utSaddledWanderingToggle);
                put("mixins.tweaks.entities.ai.wither.json", c -> UTConfigTweaks.ENTITIES.utWitherAIToggle);
                put("mixins.tweaks.entities.armedarmorstands.json", c -> UTConfigTweaks.ENTITIES.utArmedArmorStandsToggle);
                put("mixins.tweaks.entities.burning.horse.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle);
                put("mixins.tweaks.entities.burning.mobs.json", c -> UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle);
                put("mixins.tweaks.entities.damage.arrow.json", c -> UTConfigTweaks.ENTITIES.utCriticalArrowDamage != -1);
                put("mixins.tweaks.entities.damage.falling.json", c -> UTConfigTweaks.ENTITIES.WATER_FALL_DAMAGE.utFallDamageToggle);
                put("mixins.tweaks.entities.damage.velocity.json", c -> UTConfigTweaks.ENTITIES.DAMAGE_VELOCITY.utDamageVelocityToggle);
                put("mixins.tweaks.entities.despawning.json", c -> UTConfigTweaks.ENTITIES.utMobDespawningToggle != UTConfigTweaks.EnumMobDespawning.DEFAULT);
                put("mixins.tweaks.entities.loot.json", c -> UTConfigTweaks.ENTITIES.utCreeperMusicDiscsToggle);
                put("mixins.tweaks.entities.minecart.json", c -> UTConfigTweaks.ENTITIES.utMinecartDropsType);
                put("mixins.tweaks.entities.exhaustion.regen.json", c -> UTConfigTweaks.ENTITIES.utRegenExhaustion != 6.0D);
                put("mixins.tweaks.entities.exhaustion.riding.json", c -> UTConfigTweaks.ENTITIES.utRidingExhaustion != 0.0D);
                put("mixins.tweaks.entities.sleeping.json", c -> UTConfigTweaks.ENTITIES.SLEEPING.utDisableSettingSpawnToggle);
                put("mixins.tweaks.entities.spawning.caps.json", c -> UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle);
                put("mixins.tweaks.entities.spawning.creeper.confetti.json", c -> UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance != 0.0D);
                put("mixins.tweaks.entities.spawning.golem.json", c -> UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGWitherToggle);
                put("mixins.tweaks.entities.spawning.husk.json", c -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins.tweaks.entities.spawning.portal.json", c -> UTConfigTweaks.ENTITIES.utPortalSpawningToggle);
                put("mixins.tweaks.entities.spawning.skeletontrap.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utSkeletonTrapSpawningToggle);
                put("mixins.tweaks.entities.spawning.stray.json", c -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins.tweaks.entities.speed.boat.json", c -> UTConfigTweaks.ENTITIES.utBoatSpeed != 0.04D);
                put("mixins.tweaks.entities.speed.cobweb.json", c -> UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessToggle);
                put("mixins.tweaks.entities.speed.player.json", c -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
                put("mixins.tweaks.entities.taming.horse.json", c -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle);
                put("mixins.tweaks.entities.trading.json", c -> UTConfigTweaks.ENTITIES.utVillagerTradeLevelingToggle || UTConfigTweaks.ENTITIES.utVillagerTradeRestockToggle);
                put("mixins.tweaks.entities.unsafesleeping.json", c -> UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utUnsafeSleepingToggle);
                put("mixins.tweaks.entities.villagerprofessions.json", c -> UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeRestriction.length > 0);
                put("mixins.tweaks.entities.voidteleport.json", c -> UTConfigTweaks.ENTITIES.VOID_TELEPORT.utVoidTeleportToggle);
                put("mixins.tweaks.items.attackcooldown.server.json", c -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins.tweaks.items.bottle.json", c -> UTConfigTweaks.ITEMS.utGlassBottlesConsumeWaterSource);
                put("mixins.tweaks.items.bucket.json", c -> UTConfigTweaks.ITEMS.utPreventBucketPlacingInPortal);
                put("mixins.tweaks.items.eating.json", c -> UTConfigTweaks.ITEMS.utAlwaysEatToggle || UTConfigTweaks.ITEMS.utSmartEatToggle);
                put("mixins.tweaks.items.hardcorebuckets.json", c -> UTConfigTweaks.ITEMS.utHardcoreBucketsToggle);
                put("mixins.tweaks.items.infinityallarrows.json", c -> UTConfigTweaks.ITEMS.INFINITY.utAllArrowsAreInfinite);
                put("mixins.tweaks.items.infinitymending.json", c -> UTConfigTweaks.ITEMS.INFINITY.utInfinityEnchantmentConflicts);
                put("mixins.tweaks.items.itementities.server.json", c -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins.tweaks.items.mobegg.json", c -> UTConfigTweaks.ITEMS.utPreventMobEggsFromChangingSpawner);
                put("mixins.tweaks.items.rarity.json", c -> UTConfigTweaks.ITEMS.utCustomRarities.length > 0);
                put("mixins.tweaks.items.repairing.json", c -> UTConfigTweaks.ITEMS.utCraftingRepairToggle);
                put("mixins.tweaks.items.xpbottle.json", c -> UTConfigTweaks.ITEMS.utXPBottleAmount != -1);
                put("mixins.tweaks.misc.advancements.json", c -> UTConfigTweaks.MISC.utDisableAdvancementsToggle);
                put("mixins.tweaks.misc.armorcurve.json", c -> !c.inDev() && UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle);
                put("mixins.tweaks.misc.armorswap.json", c -> UTConfigTweaks.MISC.utArmorSwap);
                put("mixins.tweaks.misc.bannerlayers.json", c -> UTConfigTweaks.MISC.utBannerLayers != 6);
                put("mixins.tweaks.misc.commands.seed.json", c -> UTConfigTweaks.MISC.utCopyWorldSeedToggle);
                put("mixins.tweaks.misc.difficulty.singleplayer.json", c -> true);
                put("mixins.tweaks.misc.incurablepotions.json", c -> UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle);
                put("mixins.tweaks.misc.lightning.damage.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningDamage != 5.0D || UTConfigTweaks.MISC.LIGHTNING.utLightningFireTicks != 8);
                put("mixins.tweaks.misc.lightning.fire.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle);
                put("mixins.tweaks.misc.recipebook.server.json", c -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins.tweaks.misc.sound.broadcast.dragon.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundDragon);
                put("mixins.tweaks.misc.sound.broadcast.endportal.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundEndPortal);
                put("mixins.tweaks.misc.sound.broadcast.wither.json", c -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundWither);
                put("mixins.tweaks.misc.timeouts.json", c -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins.tweaks.misc.xp.cap.json", c -> UTConfigTweaks.MISC.utXPLevelCap > -1);
                put("mixins.tweaks.misc.xp.drop.json", c -> UTConfigTweaks.MISC.utPlayerXPDropPercentage >= 0.0D);
                put("mixins.tweaks.misc.xp.linear.json", c -> UTConfigTweaks.MISC.utLinearXP > 0);
                put("mixins.tweaks.misc.xp.smelting.json", c -> UTConfigTweaks.MISC.utSmeltingXPToggle);
                put("mixins.tweaks.performance.advancements.logging.json", c -> UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle);
                put("mixins.tweaks.performance.advancements.triggers.json", c -> UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utFasterAdvancementTriggersToggle);
                put("mixins.tweaks.performance.autosave.json", c -> UTConfigTweaks.PERFORMANCE.utAutoSaveInterval != 900);
                put("mixins.tweaks.performance.craftingcache.json", c -> UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle);
                put("mixins.tweaks.performance.dyeblending.json", c -> UTConfigTweaks.PERFORMANCE.utDyeBlendingToggle);
                put("mixins.tweaks.performance.entityradiuscheck.lesscollisions.json", c -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle);
                put("mixins.tweaks.performance.entityradiuscheck.reducesearchsize.json", c -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle);
                put("mixins.tweaks.performance.pathfinding.json", c -> UTConfigTweaks.PERFORMANCE.utPathfindingChunkCacheFixToggle);
                put("mixins.tweaks.performance.prefixcheck.json", c -> UTConfigTweaks.PERFORMANCE.utPrefixCheckToggle);
                put("mixins.tweaks.performance.redstone.json", c -> UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle);
                put("mixins.tweaks.world.cavegen.json", c -> UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenToggle);
                put("mixins.tweaks.world.chunks.gen.json", c -> UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle);
                put("mixins.tweaks.world.chunks.spawn.json", c -> !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksGenToggle || !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksLoadingToggle);
                put("mixins.tweaks.world.sealevel.json", c -> UTConfigTweaks.WORLD.utSeaLevel != 63);
                put("mixins.tweaks.world.stronghold.json", c -> UTConfigTweaks.WORLD.utStrongholdToggle);
                put("mixins.tweaks.world.village.json", c -> UTConfigTweaks.WORLD.utVillageDistance != 32);
            }
        }
    });

    private static final Map<String, Predicate<Context>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins.bugfixes.blocks.banner.json", c -> UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle && !c.isModPresent("renderlib"));
                put("mixins.bugfixes.blocks.blockoverlay.json", c -> UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle);
                put("mixins.bugfixes.blocks.miningglitch.client.json", c -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins.bugfixes.entities.elytra.json", c -> UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle);
                put("mixins.bugfixes.entities.elytrarender.json", c -> UTConfigBugfixes.ENTITIES.utFixInvisiblePlayerModelWithElytra && !c.isModPresent("openmods"));
                put("mixins.bugfixes.entities.entitylists.client.json", c -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utWorldAdditionsToggle);
                put("mixins.bugfixes.entities.villagermantle.json", c -> UTConfigBugfixes.ENTITIES.utVillagerMantleToggle);
                put("mixins.bugfixes.misc.actionbar.json", c -> UTConfigBugfixes.MISC.utOverlayMessageFadeOut);
                put("mixins.bugfixes.misc.depthmask.json", c -> UTConfigBugfixes.MISC.utDepthMaskToggle);
                put("mixins.bugfixes.misc.potionamplifier.json", c -> UTConfigBugfixes.MISC.utPotionAmplifierVisibilityToggle);
                put("mixins.bugfixes.misc.smoothlighting.json", c -> UTConfigBugfixes.MISC.utAccurateSmoothLighting);
                put("mixins.bugfixes.misc.spectatormenu.json", c -> UTConfigBugfixes.MISC.utSpectatorMenuToggle);
                put("mixins.bugfixes.misc.startup.json", c -> UTConfigTweaks.PERFORMANCE.utFasterBackgroundStartupToggle);
                put("mixins.bugfixes.misc.itempickup.json", c -> UTConfigBugfixes.MISC.utItemPickupCulling);
                put("mixins.bugfixes.world.frustumculling.json", c -> UTConfigBugfixes.WORLD.utFrustumCullingToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins.tweaks.blocks.anvil.client.json", c -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins.tweaks.blocks.betterplacement.json", c -> UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle);
                put("mixins.tweaks.blocks.betterrailplacement.json", c -> UTConfigTweaks.BLOCKS.utBetterRailPlacementToggle);
                put("mixins.tweaks.blocks.hitdelay.json", c -> UTConfigTweaks.BLOCKS.utBlockHitDelay != 5);
                put("mixins.tweaks.entities.burning.player.json", c -> UTConfigTweaks.ENTITIES.utFirstPersonBurningOverlay != -0.3D);
                put("mixins.tweaks.entities.jumping.autojump.json", c -> UTConfigTweaks.ENTITIES.utAutoJumpToggle);
                put("mixins.tweaks.entities.playerdismount.json", c -> UTConfigTweaks.MISC.utUseSeparateDismountKey);
                put("mixins.tweaks.entities.playerf5.json", c -> UTConfigTweaks.ENTITIES.utThirdPersonIgnoresNonSolidBlocks);
                put("mixins.tweaks.entities.sprint.json", c -> UTConfigTweaks.ENTITIES.utSprintHungerThreshold != 6);
                put("mixins.tweaks.items.attackcooldown.client.json", c -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins.tweaks.items.itementities.client.json", c -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins.tweaks.misc.advancements.guisize.json", c -> UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle);
                put("mixins.tweaks.misc.buttons.anaglyph.json", c -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && !c.isModPresent("optifine"));
                put("mixins.tweaks.misc.buttons.anaglyph.optifine.json", c -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && c.isModPresent("optifine"));
                put("mixins.tweaks.misc.buttons.realms.json", c -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utRealmsButtonToggle && !c.isModPresent("randompatches"));
                put("mixins.tweaks.misc.buttons.snooper.client.json", c -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins.tweaks.misc.chat.bed.json", c -> UTConfigTweaks.MISC.CHAT.utKeepChatOpen);
                put("mixins.tweaks.misc.chat.compactmessage.json", c -> UTConfigTweaks.MISC.CHAT.utCompactMessagesToggle);
                put("mixins.tweaks.misc.chat.keepsentmessages.json", c -> UTConfigTweaks.MISC.CHAT.utKeepSentMessageHistory);
                put("mixins.tweaks.misc.chat.maximumlines.json", c -> UTConfigTweaks.MISC.CHAT.utChatLines != 100);
                put("mixins.tweaks.misc.credits.json", c -> UTConfigTweaks.MISC.utSkipCreditsToggle);
                put("mixins.tweaks.misc.glint.enchantedbook.json", c -> UTConfigTweaks.MISC.utDisableEnchantmentBookGlint);
                put("mixins.tweaks.misc.glint.potion.json", c -> UTConfigTweaks.MISC.utDisablePotionGlint);
                put("mixins.tweaks.misc.gui.defaultguitextcolor.json", c -> !UTConfigTweaks.MISC.utDefaultGuiTextColor.equals("404040"));
                put("mixins.tweaks.misc.gui.gamewindow.icon.json", c -> !c.inDev() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon16.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon32.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon256.isEmpty());
                put("mixins.tweaks.misc.gui.gamewindow.title.json", c -> !c.inDev() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowDisplayTitle.isEmpty());
                put("mixins.tweaks.misc.gui.keybindlistentry.json", c -> UTConfigTweaks.MISC.utPreventKeybindingEntryOverflow);
                put("mixins.tweaks.misc.gui.lanserverproperties.json", c -> UTConfigTweaks.MISC.utLANServerProperties);
                put("mixins.tweaks.misc.gui.modlist.json", c -> UTConfigTweaks.MISC.utForgeModListImprovements);
                put("mixins.tweaks.misc.gui.overlaymessage.json", c -> UTConfigTweaks.MISC.utOverlayMessageHeight != -4);
                put("mixins.tweaks.misc.gui.ping.json", c -> UTConfigTweaks.MISC.utBetterPing);
                put("mixins.tweaks.misc.gui.potionduration.json", c -> UTConfigTweaks.MISC.utPotionDurationToggle);
                put("mixins.tweaks.misc.gui.selecteditemtooltip.json", c -> UTConfigTweaks.MISC.utSelectedItemTooltipHeight != 59);
                put("mixins.tweaks.misc.gui.textshadow.json", c -> UTConfigTweaks.MISC.utDisableTextShadow);
                put("mixins.tweaks.misc.hotbarscroll.json", c -> UTConfigTweaks.MISC.utDisableHotbarScrollWrapping);
                put("mixins.tweaks.misc.lightning.flash.json", c -> UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle);
                put("mixins.tweaks.misc.gui.mainmenu.json", c -> UTConfigTweaks.MISC.utReturnToMainMenu);
                put("mixins.tweaks.misc.music.json", c -> UTConfigTweaks.MISC.MUSIC.utMusicControlToggle);
                put("mixins.tweaks.misc.narrator.json", c -> UTConfigTweaks.MISC.utDisableNarratorToggle);
                put("mixins.tweaks.misc.narratorkeybind.json", c -> UTConfigTweaks.MISC.utUseCustomNarratorKeybind);
                put("mixins.tweaks.misc.nightvisionflash.json", c -> UTConfigTweaks.MISC.utNightVisionFlashToggle);
                put("mixins.tweaks.misc.particlelimit.json", c -> UTConfigTweaks.MISC.utParticleLimit > 0);
                put("mixins.tweaks.misc.personalpotionparticles.json", c -> UTConfigTweaks.MISC.utPoVEffectParticles);
                put("mixins.tweaks.misc.recipebook.client.json", c -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins.tweaks.misc.smoothscrolling.json", c -> UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle);
                put("mixins.tweaks.misc.sound.pitch.json", c -> UTConfigTweaks.MISC.utUnlimitedSoundPitchRange);
                put("mixins.tweaks.misc.timeouts.client.json", c -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins.tweaks.misc.toastcontrol.json", c -> UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle);
                put("mixins.tweaks.misc.viewbobbing.json", c -> true);
                put("mixins.tweaks.performance.audioreload.json", c -> UTConfigTweaks.PERFORMANCE.utDisableAudioDebugToggle && !c.isModPresent("surge"));
                put("mixins.tweaks.performance.connectionspeed.json", c -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins.tweaks.performance.fps.json", c -> UTConfigTweaks.PERFORMANCE.utUncapFPSToggle);
                put("mixins.tweaks.performance.languageswitching.json", c -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins.tweaks.performance.missingmodel.json", c -> UTConfigTweaks.PERFORMANCE.utDisableFancyMissingModelToggle && !UTReflectionUtil.isClassLoaded("com.hbm.lib.RefStrings"));
                put("mixins.tweaks.performance.mobspawnerrender.json", c -> UTConfigTweaks.PERFORMANCE.utDisableMobSpawnerRendering);
                put("mixins.tweaks.performance.resourcemanager.json", c -> UTConfigTweaks.PERFORMANCE.utCheckAnimatedModelsToggle);
                put("mixins.tweaks.performance.textureatlas.json", c -> UTConfigTweaks.PERFORMANCE.utTextureAtlasToggle && !UTReflectionUtil.isClassLoaded("dev.redstudio.valkyrie.utils.ValkyrieUtils"));
                put("mixins.tweaks.performance.texturemapcheck.json", c -> UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle);
                put("mixins.tweaks.performance.weathereffects.json", c -> UTConfigTweaks.PERFORMANCE.utDisableRainParticles);
                put("mixins.tweaks.world.loading.client.json", c -> UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle);
                put("mixins.tweaks.world.voidfog.json", c -> UTConfigTweaks.WORLD.VOID_FOG.utVoidFogToggle);
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