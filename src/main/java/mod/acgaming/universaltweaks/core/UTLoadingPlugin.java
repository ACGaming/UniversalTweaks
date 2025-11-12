package mod.acgaming.universaltweaks.core;

import java.util.*;
import java.util.function.BooleanSupplier;
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

    private static final Map<String, BooleanSupplier> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins.tweaks.misc.buttons.snooper.server.json", () -> UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins.tweaks.misc.difficulty.multiplayer.json", () -> true);
            }
        }
    });

    private static final Map<String, BooleanSupplier> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins.bugfixes.blocks.comparatortiming.json", () -> UTConfigBugfixes.BLOCKS.utComparatorTimingToggle);
                put("mixins.bugfixes.blocks.falling.json", () -> UTConfigBugfixes.BLOCKS.utFallingBlockDamageToggle);
                put("mixins.bugfixes.blocks.hopper.boundingbox.json", () -> UTConfigBugfixes.BLOCKS.utDietHopperToggle);
                put("mixins.bugfixes.blocks.hopper.tile.json", () -> UTConfigBugfixes.BLOCKS.utHopperInsertToggle);
                put("mixins.bugfixes.blocks.itemframevoid.json", () -> UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle);
                put("mixins.bugfixes.blocks.ladderflying.json", () -> UTConfigBugfixes.BLOCKS.utLadderFlyingToggle);
                put("mixins.bugfixes.blocks.miningglitch.server.json", () -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins.bugfixes.blocks.piston.retraction.json", () -> UTConfigBugfixes.BLOCKS.utPistonRetractionToggle);
                put("mixins.bugfixes.blocks.piston.tile.json", () -> UTConfigBugfixes.BLOCKS.utPistonTileToggle);
                put("mixins.bugfixes.entities.ai.json", () -> UTConfigBugfixes.ENTITIES.utEntityAITasksToggle);
                put("mixins.bugfixes.entities.attackradius.json", () -> UTConfigBugfixes.ENTITIES.utAttackRadiusToggle);
                put("mixins.bugfixes.entities.blockfire.json", () -> UTConfigBugfixes.ENTITIES.utBlockFireToggle);
                put("mixins.bugfixes.entities.boat.breaking.json", () -> UTConfigBugfixes.ENTITIES.utBoatBreakingFallHeight >= 0);
                put("mixins.bugfixes.entities.boat.offset.json", () -> UTConfigBugfixes.ENTITIES.utBoatOffsetToggle);
                put("mixins.bugfixes.entities.boundingbox.json", () -> UTConfigBugfixes.ENTITIES.utEntityAABBToggle);
                put("mixins.bugfixes.entities.deathtime.json", () -> UTConfigBugfixes.ENTITIES.utDeathTimeToggle);
                put("mixins.bugfixes.entities.destroypacket.json", () -> UTConfigBugfixes.ENTITIES.utDestroyPacketToggle);
                put("mixins.bugfixes.entities.desync.json", () -> UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle);
                put("mixins.bugfixes.entities.dimensionchange.json", () -> UTConfigBugfixes.ENTITIES.utDimensionChangeToggle);
                put("mixins.bugfixes.entities.entityid.json", () -> UTConfigBugfixes.ENTITIES.utEntityIDToggle);
                put("mixins.bugfixes.entities.entitylists.json", () -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle);
                put("mixins.bugfixes.entities.horsefalling.json", () -> UTConfigBugfixes.ENTITIES.utHorseFallingToggle);
                put("mixins.bugfixes.entities.maxhealth.json", () -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
                put("mixins.bugfixes.entities.minecart.json", () -> UTConfigBugfixes.ENTITIES.utMinecartAIToggle);
                put("mixins.bugfixes.entities.mount.json", () -> UTConfigBugfixes.ENTITIES.utMountDesyncToggle);
                put("mixins.bugfixes.entities.saturation.json", () -> UTConfigBugfixes.ENTITIES.utExhaustionToggle);
                put("mixins.bugfixes.entities.skeletonaim.json", () -> UTConfigBugfixes.ENTITIES.utSkeletonAimToggle);
                put("mixins.bugfixes.entities.sleeping.json", () -> UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle);
                put("mixins.bugfixes.entities.suffocation.json", () -> UTConfigBugfixes.ENTITIES.utEntitySuffocationToggle);
                put("mixins.bugfixes.entities.tracker.json", () -> UTConfigBugfixes.ENTITIES.utEntityTrackerToggle && !Coremods.SPONGEFORGE.isLoaded());
                put("mixins.bugfixes.entities.untippedarrowparticles.json", () -> UTConfigBugfixes.ENTITIES.utUntippedArrowParticlesToggle);
                put("mixins.bugfixes.misc.crafteditemstatistics.json", () -> UTConfigBugfixes.MISC.utCraftedItemStatisticsToggle);
                put("mixins.bugfixes.misc.packetsize.json", () -> UTConfigBugfixes.MISC.utPacketSize > 0x200000 && !Coremods.SPONGEFORGE.isLoaded() && !Coremods.RANDOM_PATCHES.isLoaded());
                put("mixins.bugfixes.misc.particlespawning.json", () -> UTConfigBugfixes.MISC.utParticleSpawningToggle);
                put("mixins.bugfixes.misc.durabilitycap.json", () -> UTConfigBugfixes.MISC.utExtendDurabilityCap);
                put("mixins.bugfixes.world.chunksaving.json", () -> UTConfigBugfixes.WORLD.utChunkSavingToggle && !Coremods.SPONGEFORGE.isLoaded());
                put("mixins.bugfixes.world.portal.json", () -> UTConfigBugfixes.WORLD.utPortalLocationLink);
                put("mixins.bugfixes.world.tileentities.json", () -> UTConfigBugfixes.WORLD.utTileEntityMap != UTConfigBugfixes.WorldCategory.EnumMaps.HASHMAP);
                put("mixins.bugfixes.world.village.json", () -> UTConfigBugfixes.WORLD.utVillageComponentPartsToggle);
                put("mixins.bugfixes.world.witchhut.json", () -> UTConfigBugfixes.WORLD.utWitchStructuresToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins.tweaks.blocks.anvil.json", () -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins.tweaks.blocks.barrier.json", () -> UTConfigTweaks.BLOCKS.utBarrierParticleDisplay);
                put("mixins.tweaks.blocks.bedobstruction.json", () -> UTConfigTweaks.BLOCKS.utBedObstructionToggle);
                put("mixins.tweaks.blocks.breakablebedrock.json", () -> UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle);
                put("mixins.tweaks.blocks.enchantmenttable.json", () -> UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle);
                put("mixins.tweaks.blocks.endcrystal.json", () -> UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementToggle);
                put("mixins.tweaks.blocks.endportal.json", () -> UTConfigTweaks.BLOCKS.utRenderEndPortalBottom);
                put("mixins.tweaks.blocks.explosion.json", () -> UTConfigTweaks.BLOCKS.utExplosionDropChance != 1.0D);
                put("mixins.tweaks.blocks.falling.json", () -> UTConfigTweaks.BLOCKS.utFallingBlockLifespan != 600);
                put("mixins.tweaks.blocks.golemstructure.json", () -> UTConfigTweaks.ENTITIES.utGolemPlacement);
                put("mixins.tweaks.blocks.growthsize.json", () -> UTConfigTweaks.BLOCKS.utCactusSize != 3 && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3 && UTConfigTweaks.BLOCKS.utVineSize != 0);
                put("mixins.tweaks.blocks.leafdecay.json", () -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
                put("mixins.tweaks.blocks.lenientpaths.json", () -> UTConfigTweaks.BLOCKS.utLenientPathsToggle);
                put("mixins.tweaks.blocks.observer.json", () -> UTConfigTweaks.BLOCKS.utPreventObserverActivatesOnPlacement);
                put("mixins.tweaks.blocks.overhaulbeacon.json", () -> UTConfigTweaks.BLOCKS.OVERHAUL_BEACON.utOverhaulBeaconToggle);
                put("mixins.tweaks.blocks.piston.json", () -> UTConfigTweaks.BLOCKS.PISTON.utPistonBlockBlacklistToggle);
                put("mixins.tweaks.blocks.pumpkinplacing.json", () -> UTConfigTweaks.BLOCKS.utUnsupportedPumpkinPlacing);
                put("mixins.tweaks.blocks.sapling.json", () -> UTConfigTweaks.BLOCKS.SAPLING_BEHAVIOR.utSaplingBehaviorToggle);
                put("mixins.tweaks.blocks.witherstructure.json", () -> UTConfigTweaks.ENTITIES.utWitherPlacement);
                put("mixins.tweaks.entities.ai.json", () -> UTConfigTweaks.ENTITIES.utAIReplacementToggle);
                put("mixins.tweaks.entities.ai.saddledwandering.json", () -> UTConfigTweaks.ENTITIES.utSaddledWanderingToggle);
                put("mixins.tweaks.entities.ai.wither.json", () -> UTConfigTweaks.ENTITIES.utWitherAIToggle);
                put("mixins.tweaks.entities.armedarmorstands.json", () -> UTConfigTweaks.ENTITIES.utArmedArmorStandsToggle);
                put("mixins.tweaks.entities.burning.horse.json", () -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utBurningUndeadHorsesToggle);
                put("mixins.tweaks.entities.burning.mobs.json", () -> UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle);
                put("mixins.tweaks.entities.damage.arrow.json", () -> UTConfigTweaks.ENTITIES.utCriticalArrowDamage != -1);
                put("mixins.tweaks.entities.damage.falling.json", () -> UTConfigTweaks.ENTITIES.WATER_FALL_DAMAGE.utFallDamageToggle);
                put("mixins.tweaks.entities.damage.velocity.json", () -> UTConfigTweaks.ENTITIES.DAMAGE_VELOCITY.utDamageVelocityToggle);
                put("mixins.tweaks.entities.despawning.json", () -> UTConfigTweaks.ENTITIES.utMobDespawnToggle);
                put("mixins.tweaks.entities.loot.json", () -> UTConfigTweaks.ENTITIES.utCreeperMusicDiscsToggle);
                put("mixins.tweaks.entities.minecart.json", () -> UTConfigTweaks.ENTITIES.utMinecartDropsType);
                put("mixins.tweaks.entities.exhaustion.regen.json", () -> UTConfigTweaks.ENTITIES.utRegenExhaustion != 6.0D);
                put("mixins.tweaks.entities.exhaustion.riding.json", () -> UTConfigTweaks.ENTITIES.utRidingExhaustion != 0.0D);
                put("mixins.tweaks.entities.sleeping.json", () -> UTConfigTweaks.ENTITIES.SLEEPING.utDisableSettingSpawnToggle);
                put("mixins.tweaks.entities.spawning.caps.json", () -> UTConfigTweaks.ENTITIES.SPAWN_CAPS.utSpawnCapsToggle);
                put("mixins.tweaks.entities.spawning.creeper.confetti.json", () -> UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance != 0.0D);
                put("mixins.tweaks.entities.spawning.golem.json", () -> UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGIronGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGSnowGolemToggle || UTConfigTweaks.ENTITIES.NO_GOLEMS.utNGWitherToggle);
                put("mixins.tweaks.entities.spawning.husk.json", () -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins.tweaks.entities.spawning.portal.json", () -> UTConfigTweaks.ENTITIES.utPortalSpawningToggle);
                put("mixins.tweaks.entities.spawning.skeletontrap.json", () -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utSkeletonTrapSpawningToggle);
                put("mixins.tweaks.entities.spawning.stray.json", () -> UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle);
                put("mixins.tweaks.entities.speed.boat.json", () -> UTConfigTweaks.ENTITIES.utBoatSpeed != 0.04D);
                put("mixins.tweaks.entities.speed.cobweb.json", () -> UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessToggle);
                put("mixins.tweaks.entities.speed.player.json", () -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
                put("mixins.tweaks.entities.taming.horse.json", () -> UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle);
                put("mixins.tweaks.entities.trading.json", () -> UTConfigTweaks.ENTITIES.utVillagerTradeLevelingToggle || UTConfigTweaks.ENTITIES.utVillagerTradeRestockToggle);
                put("mixins.tweaks.entities.unsafesleeping.json", () -> UTConfigTweaks.ENTITIES.UNSAFE_SLEEPING.utUnsafeSleepingToggle);
                put("mixins.tweaks.entities.villagerprofessions.json", () -> UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeRestriction.length > 0);
                put("mixins.tweaks.entities.voidteleport.json", () -> UTConfigTweaks.ENTITIES.VOID_TELEPORT.utVoidTeleportToggle);
                put("mixins.tweaks.items.attackcooldown.server.json", () -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins.tweaks.items.bottle.json", () -> UTConfigTweaks.ITEMS.utGlassBottlesConsumeWaterSource);
                put("mixins.tweaks.items.bucket.json", () -> UTConfigTweaks.ITEMS.utPreventBucketPlacingInPortal);
                put("mixins.tweaks.items.eating.json", () -> UTConfigTweaks.ITEMS.utAlwaysEatToggle || UTConfigTweaks.ITEMS.utSmartEatToggle);
                put("mixins.tweaks.items.hardcorebuckets.json", () -> UTConfigTweaks.ITEMS.utHardcoreBucketsToggle);
                put("mixins.tweaks.items.infinityallarrows.json", () -> UTConfigTweaks.ITEMS.INFINITY.utAllArrowsAreInfinite);
                put("mixins.tweaks.items.infinitymending.json", () -> UTConfigTweaks.ITEMS.INFINITY.utInfinityEnchantmentConflicts);
                put("mixins.tweaks.items.itementities.server.json", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins.tweaks.items.mobegg.json", () -> UTConfigTweaks.ITEMS.utPreventMobEggsFromChangingSpawner);
                put("mixins.tweaks.items.rarity.json", () -> UTConfigTweaks.ITEMS.utCustomRarities.length > 0);
                put("mixins.tweaks.items.repairing.json", () -> UTConfigTweaks.ITEMS.utCraftingRepairToggle);
                put("mixins.tweaks.items.xpbottle.json", () -> UTConfigTweaks.ITEMS.utXPBottleAmount != -1);
                put("mixins.tweaks.misc.advancements.json", () -> UTConfigTweaks.MISC.utDisableAdvancementsToggle);
                put("mixins.tweaks.misc.armorcurve.json", () -> UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle);
                put("mixins.tweaks.misc.armorswap.json", () -> UTConfigTweaks.MISC.utArmorSwap);
                put("mixins.tweaks.misc.bannerlayers.json", () -> UTConfigTweaks.MISC.utBannerLayers != 6);
                put("mixins.tweaks.misc.commands.seed.json", () -> UTConfigTweaks.MISC.utCopyWorldSeedToggle);
                put("mixins.tweaks.misc.difficulty.singleplayer.json", () -> true);
                put("mixins.tweaks.misc.incurablepotions.json", () -> UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle);
                put("mixins.tweaks.misc.lightning.damage.json", () -> UTConfigTweaks.MISC.LIGHTNING.utLightningDamage != 5.0D || UTConfigTweaks.MISC.LIGHTNING.utLightningFireTicks != 8);
                put("mixins.tweaks.misc.lightning.fire.json", () -> UTConfigTweaks.MISC.LIGHTNING.utLightningFireToggle);
                put("mixins.tweaks.misc.recipebook.server.json", () -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins.tweaks.misc.sound.broadcast.dragon.json", () -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundDragon);
                put("mixins.tweaks.misc.sound.broadcast.endportal.json", () -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundEndPortal);
                put("mixins.tweaks.misc.sound.broadcast.wither.json", () -> !UTConfigTweaks.MISC.BROADCAST_SOUNDS.utBroadcastSoundWither);
                put("mixins.tweaks.misc.timeouts.json", () -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins.tweaks.misc.xp.cap.json", () -> UTConfigTweaks.MISC.utXPLevelCap > -1);
                put("mixins.tweaks.misc.xp.drop.json", () -> UTConfigTweaks.MISC.utPlayerXPDropPercentage >= 0.0D);
                put("mixins.tweaks.misc.xp.linear.json", () -> UTConfigTweaks.MISC.utLinearXP > 0);
                put("mixins.tweaks.misc.xp.smelting.json", () -> UTConfigTweaks.MISC.utSmeltingXPToggle);
                put("mixins.tweaks.performance.advancements.logging.json", () -> UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle);
                put("mixins.tweaks.performance.advancements.triggers.json", () -> UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utFasterAdvancementTriggersToggle);
                put("mixins.tweaks.performance.autosave.json", () -> UTConfigTweaks.PERFORMANCE.utAutoSaveInterval != 900);
                put("mixins.tweaks.performance.craftingcache.json", () -> UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle);
                put("mixins.tweaks.performance.dyeblending.json", () -> UTConfigTweaks.PERFORMANCE.utDyeBlendingToggle);
                put("mixins.tweaks.performance.entityradiuscheck.lesscollisions.json", () -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle);
                put("mixins.tweaks.performance.entityradiuscheck.reducesearchsize.json", () -> UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle);
                put("mixins.tweaks.performance.pathfinding.json", () -> UTConfigTweaks.PERFORMANCE.utPathfindingChunkCacheFixToggle);
                put("mixins.tweaks.performance.prefixcheck.json", () -> UTConfigTweaks.PERFORMANCE.utPrefixCheckToggle);
                put("mixins.tweaks.performance.redstone.json", () -> UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle);
                put("mixins.tweaks.world.cavegen.json", () -> UTConfigTweaks.WORLD.CAVE_GEN.utCaveGenToggle);
                put("mixins.tweaks.world.chunks.gen.json", () -> UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle);
                put("mixins.tweaks.world.chunks.spawn.json", () -> !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksGenToggle || !UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksLoadingToggle);
                put("mixins.tweaks.world.sealevel.json", () -> UTConfigTweaks.WORLD.utSeaLevel != 63);
                put("mixins.tweaks.world.stronghold.json", () -> UTConfigTweaks.WORLD.utStrongholdToggle);
                put("mixins.tweaks.world.village.json", () -> UTConfigTweaks.WORLD.utVillageDistance != 32);
            }
        }
    });

    private static final Map<String, BooleanSupplier> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
            {
                put("mixins.bugfixes.blocks.banner.json", () -> UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle && !Coremods.RENDERLIB.isLoaded());
                put("mixins.bugfixes.blocks.blockoverlay.json", () -> UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle);
                put("mixins.bugfixes.blocks.miningglitch.client.json", () -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
                put("mixins.bugfixes.entities.elytra.json", () -> UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle);
                put("mixins.bugfixes.entities.elytrarender.json", () -> UTConfigBugfixes.ENTITIES.utFixInvisiblePlayerModelWithElytra && !Coremods.OPENMODS.isLoaded());
                put("mixins.bugfixes.entities.entitylists.client.json", () -> UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utWorldAdditionsToggle);
                put("mixins.bugfixes.entities.villagermantle.json", () -> UTConfigBugfixes.ENTITIES.utVillagerMantleToggle);
                put("mixins.bugfixes.misc.actionbar.json", () -> UTConfigBugfixes.MISC.utOverlayMessageFadeOut);
                put("mixins.bugfixes.misc.depthmask.json", () -> UTConfigBugfixes.MISC.utDepthMaskToggle);
                put("mixins.bugfixes.misc.potionamplifier.json", () -> UTConfigBugfixes.MISC.utPotionAmplifierVisibilityToggle);
                put("mixins.bugfixes.misc.smoothlighting.json", () -> UTConfigBugfixes.MISC.utAccurateSmoothLighting);
                put("mixins.bugfixes.misc.spectatormenu.json", () -> UTConfigBugfixes.MISC.utSpectatorMenuToggle);
                put("mixins.bugfixes.misc.startup.json", () -> UTConfigTweaks.PERFORMANCE.utFasterBackgroundStartupToggle);
                put("mixins.bugfixes.misc.itempickup.json", () -> UTConfigBugfixes.MISC.utItemPickupCulling);
                put("mixins.bugfixes.world.frustumculling.json", () -> UTConfigBugfixes.WORLD.utFrustumCullingToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins.tweaks.blocks.anvil.client.json", () -> UTConfigTweaks.BLOCKS.ANVIL.utAnvilXPLevelCap != 40);
                put("mixins.tweaks.blocks.betterplacement.json", () -> UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle);
                put("mixins.tweaks.blocks.betterrailplacement.json", () -> UTConfigTweaks.BLOCKS.utBetterRailPlacementToggle);
                put("mixins.tweaks.blocks.hitdelay.json", () -> UTConfigTweaks.BLOCKS.utBlockHitDelay != 5);
                put("mixins.tweaks.entities.burning.player.json", () -> UTConfigTweaks.ENTITIES.utFirstPersonBurningOverlay != -0.3D);
                put("mixins.tweaks.entities.jumping.autojump.json", () -> UTConfigTweaks.ENTITIES.utAutoJumpToggle);
                put("mixins.tweaks.entities.playerdismount.json", () -> UTConfigTweaks.MISC.utUseSeparateDismountKey);
                put("mixins.tweaks.entities.playerf5.json", () -> UTConfigTweaks.ENTITIES.utThirdPersonIgnoresNonSolidBlocks);
                put("mixins.tweaks.entities.sprint.json", () -> UTConfigTweaks.ENTITIES.utSprintHungerThreshold != 6);
                put("mixins.tweaks.items.attackcooldown.client.json", () -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
                put("mixins.tweaks.items.itementities.client.json", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
                put("mixins.tweaks.misc.advancements.guisize.json", () -> UTConfigTweaks.MISC.ADVANCEMENTS.utAdvancementsToggle);
                put("mixins.tweaks.misc.buttons.anaglyph.json", () -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && !Coremods.OPTIFINE.isLoaded());
                put("mixins.tweaks.misc.buttons.anaglyph.optifine.json", () -> UTConfigTweaks.MISC.ut3DAnaglyphButtonToggle && Coremods.OPTIFINE.isLoaded());
                put("mixins.tweaks.misc.buttons.realms.json", () -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utRealmsButtonToggle && !Coremods.RANDOM_PATCHES.isLoaded());
                put("mixins.tweaks.misc.buttons.snooper.client.json", () -> SystemUtils.IS_JAVA_1_8 && UTConfigTweaks.MISC.utSnooperToggle);
                put("mixins.tweaks.misc.chat.compactmessage.json", () -> UTConfigTweaks.MISC.CHAT.utCompactMessagesToggle);
                put("mixins.tweaks.misc.chat.keepsentmessages.json", () -> UTConfigTweaks.MISC.CHAT.utKeepSentMessageHistory);
                put("mixins.tweaks.misc.chat.maximumlines.json", () -> UTConfigTweaks.MISC.CHAT.utChatLines != 100);
                put("mixins.tweaks.misc.credits.json", () -> UTConfigTweaks.MISC.utSkipCreditsToggle);
                put("mixins.tweaks.misc.glint.enchantedbook.json", () -> UTConfigTweaks.MISC.utDisableEnchantmentBookGlint);
                put("mixins.tweaks.misc.glint.potion.json", () -> UTConfigTweaks.MISC.utDisablePotionGlint);
                put("mixins.tweaks.misc.gui.defaultguitextcolor.json", () -> !UTConfigTweaks.MISC.utDefaultGuiTextColor.equals("404040"));
                put("mixins.tweaks.misc.gui.gamewindow.icon.json", () -> !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon16.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon32.isEmpty() && !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowIcon256.isEmpty());
                put("mixins.tweaks.misc.gui.gamewindow.title.json", () -> !UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowDisplayTitle.isEmpty());
                put("mixins.tweaks.misc.gui.keybindlistentry.json", () -> UTConfigTweaks.MISC.utPreventKeybindingEntryOverflow);
                put("mixins.tweaks.misc.gui.lanserverproperties.json", () -> UTConfigTweaks.MISC.utLANServerProperties);
                put("mixins.tweaks.misc.gui.modlist.json", () -> UTConfigTweaks.MISC.utForgeModListImprovements);
                put("mixins.tweaks.misc.gui.overlaymessage.json", () -> UTConfigTweaks.MISC.utOverlayMessageHeight != -4);
                put("mixins.tweaks.misc.gui.ping.json", () -> UTConfigTweaks.MISC.utBetterPing);
                put("mixins.tweaks.misc.gui.potionduration.json", () -> UTConfigTweaks.MISC.utPotionDurationToggle);
                put("mixins.tweaks.misc.gui.selecteditemtooltip.json", () -> UTConfigTweaks.MISC.utSelectedItemTooltipHeight != 59);
                put("mixins.tweaks.misc.gui.textshadow.json", () -> UTConfigTweaks.MISC.utDisableTextShadow);
                put("mixins.tweaks.misc.hotbarscroll.json", () -> UTConfigTweaks.MISC.utDisableHotbarScrollWrapping);
                put("mixins.tweaks.misc.lightning.flash.json", () -> UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle);
                put("mixins.tweaks.misc.gui.mainmenu.json", () -> UTConfigTweaks.MISC.utReturnToMainMenu);
                put("mixins.tweaks.misc.music.json", () -> UTConfigTweaks.MISC.MUSIC.utMusicControlToggle);
                put("mixins.tweaks.misc.narrator.json", () -> UTConfigTweaks.MISC.utDisableNarratorToggle);
                put("mixins.tweaks.misc.narratorkeybind.json", () -> UTConfigTweaks.MISC.utUseCustomNarratorKeybind);
                put("mixins.tweaks.misc.nightvisionflash.json", () -> UTConfigTweaks.MISC.utNightVisionFlashToggle);
                put("mixins.tweaks.misc.particlelimit.json", () -> UTConfigTweaks.MISC.utParticleLimit > 0);
                put("mixins.tweaks.misc.personalpotionparticles.json", () -> UTConfigTweaks.MISC.utPoVEffectParticles);
                put("mixins.tweaks.misc.recipebook.client.json", () -> UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins.tweaks.misc.smoothscrolling.json", () -> UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle);
                put("mixins.tweaks.misc.sound.pitch.json", () -> UTConfigTweaks.MISC.utUnlimitedSoundPitchRange);
                put("mixins.tweaks.misc.timeouts.client.json", () -> UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle);
                put("mixins.tweaks.misc.toastcontrol.json", () -> UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle);
                put("mixins.tweaks.misc.viewbobbing.json", () -> true);
                put("mixins.tweaks.performance.audioreload.json", () -> UTConfigTweaks.PERFORMANCE.utDisableAudioDebugToggle && !Coremods.SURGE.isLoaded());
                put("mixins.tweaks.performance.connectionspeed.json", () -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins.tweaks.performance.fps.json", () -> UTConfigTweaks.PERFORMANCE.utUncapFPSToggle);
                put("mixins.tweaks.performance.languageswitching.json", () -> UTConfigTweaks.PERFORMANCE.utImproveLanguageSwitchingSpeed);
                put("mixins.tweaks.performance.missingmodel.json", () -> UTConfigTweaks.PERFORMANCE.utDisableFancyMissingModelToggle && !UTReflectionUtil.isClassLoaded("com.hbm.lib.RefStrings"));
                put("mixins.tweaks.performance.mobspawnerrender.json", () -> UTConfigTweaks.PERFORMANCE.utDisableMobSpawnerRendering);
                put("mixins.tweaks.performance.resourcemanager.json", () -> UTConfigTweaks.PERFORMANCE.utCheckAnimatedModelsToggle);
                put("mixins.tweaks.performance.textureatlas.json", () -> UTConfigTweaks.PERFORMANCE.utTextureAtlasToggle && !UTReflectionUtil.isClassLoaded("dev.redstudio.valkyrie.utils.ValkyrieUtils"));
                put("mixins.tweaks.performance.texturemapcheck.json", () -> UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle);
                put("mixins.tweaks.performance.weathereffects.json", () -> UTConfigTweaks.PERFORMANCE.utDisableRainParticles);
                put("mixins.tweaks.world.loading.client.json", () -> UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle);
                put("mixins.tweaks.world.voidfog.json", () -> UTConfigTweaks.WORLD.VOID_FOG.utVoidFogToggle);
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
        if (context.inDev())
        {
            return !mixinConfig.equals("mixins.tweaks.misc.armorcurve.json") // Causes crashes in dev env only
                && !mixinConfig.equals("mixins.tweaks.misc.gui.gamewindow.icon.json") // No icon
                && !mixinConfig.equals("mixins.tweaks.misc.gui.gamewindow.title.json"); // No title
        }
        BooleanSupplier sidedSupplier = UTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : serversideMixinConfigs.get(mixinConfig);
        BooleanSupplier commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.getAsBoolean() : commonSupplier == null || commonSupplier.getAsBoolean();
    }
}