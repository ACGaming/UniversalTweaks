package mod.acgaming.universaltweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.bugfixes.entities.UTVanillaEvents;
import mod.acgaming.universaltweaks.bugfixes.entities.consumption.UTDoubleConsumption;
import mod.acgaming.universaltweaks.bugfixes.entities.desync.UTEntityDesync;
import mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange.UTDimensionChange;
import mod.acgaming.universaltweaks.bugfixes.entities.disconnectdupe.UTDisconnectDupe;
import mod.acgaming.universaltweaks.bugfixes.entities.nan.UTEntityNaN;
import mod.acgaming.universaltweaks.bugfixes.entities.uuid.UTEntityUUID;
import mod.acgaming.universaltweaks.bugfixes.misc.help.UTHelp;
import mod.acgaming.universaltweaks.bugfixes.world.portal.UTPortalLocationLink;
import mod.acgaming.universaltweaks.bugfixes.world.portal.UTPortalTravelingDupe;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.core.UTMixinLoader;
import mod.acgaming.universaltweaks.mods.arcanearchives.UTArcaneArchivesEvents;
import mod.acgaming.universaltweaks.mods.astralsorcery.UTClearOnChange;
import mod.acgaming.universaltweaks.mods.bloodmagic.UTBloodMagicEvents;
import mod.acgaming.universaltweaks.mods.botania.UTBotaniaFancySkybox;
import mod.acgaming.universaltweaks.mods.collective.UTCollectiveEvents;
import mod.acgaming.universaltweaks.mods.cqrepoured.UTGoldenFeatherEvent;
import mod.acgaming.universaltweaks.mods.elenaidodge2.UTED2Burning;
import mod.acgaming.universaltweaks.mods.elenaidodge2.UTED2Sprinting;
import mod.acgaming.universaltweaks.mods.mekanism.dupes.UTMekanismFixes;
import mod.acgaming.universaltweaks.mods.projectred.UTProjectRedWorldEvents;
import mod.acgaming.universaltweaks.mods.simplyjetpacks.UTSimplyJetpacksEvents;
import mod.acgaming.universaltweaks.mods.simplyjetpacks.network.message.MessageClientStatesReset;
import mod.acgaming.universaltweaks.mods.tconstruct.UTTConstructEvents;
import mod.acgaming.universaltweaks.mods.tconstruct.UTTConstructMaterials;
import mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.UTOreDictCache;
import mod.acgaming.universaltweaks.mods.woot.UTWootTicketManager;
import mod.acgaming.universaltweaks.tweaks.blocks.anvil.UTRepairableAnvil;
import mod.acgaming.universaltweaks.tweaks.blocks.betterharvest.UTBetterHarvest;
import mod.acgaming.universaltweaks.tweaks.blocks.betterplacement.UTBetterPlacement;
import mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.UTBreakableBedrock;
import mod.acgaming.universaltweaks.tweaks.blocks.dispenser.UTBlockDispenser;
import mod.acgaming.universaltweaks.tweaks.blocks.endcrystal.UTEndCrystalPlacement;
import mod.acgaming.universaltweaks.tweaks.blocks.farmlandtrample.UTFarmlandTrample;
import mod.acgaming.universaltweaks.tweaks.blocks.fencewalljump.UTFenceWallJump;
import mod.acgaming.universaltweaks.tweaks.blocks.finitewater.UTFiniteWater;
import mod.acgaming.universaltweaks.tweaks.blocks.piston.UTPistonBlockBlacklist;
import mod.acgaming.universaltweaks.tweaks.blocks.slimeblock.UTSlimeBlockProjectiles;
import mod.acgaming.universaltweaks.tweaks.entities.ai.UTRemoveAI;
import mod.acgaming.universaltweaks.tweaks.entities.ai.wither.UTWitherAI;
import mod.acgaming.universaltweaks.tweaks.entities.attributes.UTAttributes;
import mod.acgaming.universaltweaks.tweaks.entities.breeding.UTEasyBreeding;
import mod.acgaming.universaltweaks.tweaks.entities.burning.UTBetterBurning;
import mod.acgaming.universaltweaks.tweaks.entities.burning.UTBetterIgnition;
import mod.acgaming.universaltweaks.tweaks.entities.burning.UTSuperHotTorch;
import mod.acgaming.universaltweaks.tweaks.entities.chickenshedding.UTChickenShedding;
import mod.acgaming.universaltweaks.tweaks.entities.jumping.coyotetime.UTCoyoteTimeJumping;
import mod.acgaming.universaltweaks.tweaks.entities.knockback.UTModernKnockback;
import mod.acgaming.universaltweaks.tweaks.entities.rallyhealth.UTRallyHealth;
import mod.acgaming.universaltweaks.tweaks.entities.sleeping.UTSleeping;
import mod.acgaming.universaltweaks.tweaks.entities.soulboundvexes.UTSoulboundVexes;
import mod.acgaming.universaltweaks.tweaks.entities.spawning.creeper.charged.UTChargedCreepers;
import mod.acgaming.universaltweaks.tweaks.entities.spawning.rabbit.UTRabbits;
import mod.acgaming.universaltweaks.tweaks.entities.trading.UTVillagerProfessionRestriction;
import mod.acgaming.universaltweaks.tweaks.items.autoswitch.UTAutoSwitch;
import mod.acgaming.universaltweaks.tweaks.items.bowinfinity.UTBowInfinity;
import mod.acgaming.universaltweaks.tweaks.items.dragonbreath.UTLeftoverDragonBreath;
import mod.acgaming.universaltweaks.tweaks.items.itementities.UTEntityItemInteract;
import mod.acgaming.universaltweaks.tweaks.items.mending.UTMending;
import mod.acgaming.universaltweaks.tweaks.items.parry.UTParry;
import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import mod.acgaming.universaltweaks.tweaks.misc.advancements.screenshot.UTAdvancementScreenshot;
import mod.acgaming.universaltweaks.tweaks.misc.armorcurve.UTArmorCurve;
import mod.acgaming.universaltweaks.tweaks.misc.buttons.cheats.UTToggleCheats;
import mod.acgaming.universaltweaks.tweaks.misc.damagetilt.UTDamageTilt;
import mod.acgaming.universaltweaks.tweaks.misc.endportal.UTEndPortalParallax;
import mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.UTLanServerProperties;
import mod.acgaming.universaltweaks.tweaks.misc.incurablepotions.UTIncurablePotions;
import mod.acgaming.universaltweaks.tweaks.misc.lightning.damage.UTLightningItemDestruction;
import mod.acgaming.universaltweaks.tweaks.misc.loadsound.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.misc.music.UTMusicType;
import mod.acgaming.universaltweaks.tweaks.misc.offhand.mixin.UTOffhand;
import mod.acgaming.universaltweaks.tweaks.misc.pickupnotification.UTPickupNotificationOverlay;
import mod.acgaming.universaltweaks.tweaks.misc.potionshift.UTPotionShift;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrass;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.UTTutorialToast;
import mod.acgaming.universaltweaks.tweaks.misc.xp.drop.UTAdaptiveXPDrops;
import mod.acgaming.universaltweaks.tweaks.performance.autosave.UTAutoSaveOFCompat;
import mod.acgaming.universaltweaks.tweaks.performance.craftingcache.UTCraftingCache;
import mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.UTEntityRadiusCheck;
import mod.acgaming.universaltweaks.tweaks.world.chunks.gen.UTChunkGenLimit;
import mod.acgaming.universaltweaks.tweaks.world.chunks.tidy.mixin.UTTidyChunk;
import mod.acgaming.universaltweaks.tweaks.world.loading.UTUnloader;
import mod.acgaming.universaltweaks.tweaks.world.voidfog.UTVoidFog;
import mod.acgaming.universaltweaks.util.UTCommands;
import mod.acgaming.universaltweaks.util.UTKeybindings;
import mod.acgaming.universaltweaks.util.UTPacketHandler;
import mod.acgaming.universaltweaks.util.UTReflectionUtil;
import mod.acgaming.universaltweaks.util.compat.UTObsoleteModsHandler;
import mods.railcraft.common.core.BetaMessageTickHandler;
import net.tardis.mod.proxy.ClientProxy;
import tonius.simplyjetpacks.network.NetworkHandler;

@Mod(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME, version = UniversalTweaks.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = UniversalTweaks.DEPENDENCIES)
public class UniversalTweaks
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:mixinbooter@[10.5,);required-after:configanytime@[3.0,);" + "after:actuallyadditions;" + "after:aoa3;" + "after:arcanearchives;" + "after:astralsorcery;" + "after:biomesoplenty;" + "after:bloodmagic;" + "after:botania;" + "after:buildcraftcore;" + "after:ceramics;" + "after:chisel;" + "after:cofhcore;" + "after:collective;" + "after:compactmachines3;" + "after:contenttweaker;" + "after:cqrepoured;" + "after:crafttweaker;" + "after:effortlessbuilding;" + "after:element;" + "after:elenaidodge2;" + "after:enderio;" + "after:epicsiegemod;" + "after:erebus;" + "after:extratrees;" + "after:extrautils2;" + "after:farlanders;" + "after:forestry;" + "after:forgemultipartcbe;" + "after:ic2;" + "after:industrialforegoing;" + "after:infernalmobs;" + "after:ironbackpacks;" + "after:itemstages;" + "after:mekanism;" + "after:mobstages;" + "after:modularrouters;" + "after:mrtjpcore;" + "after:netherchest;" + "after:netherrocks;" + "after:nuclearcraft;" + "after:openblocks;" + "after:plustic;" + "after:projectred-exploration;" + "after:quark;" + "after:railcraft;" + "after:reskillable;" + "after:rftoolsdim;" + "after:roost;" + "after:simpledifficulty;" + "after:simplyjetpacks;" + "after:spiceoflife;" + "after:storagedrawers;" + "after:tardis;" + "after:tcomplement;" + "after:tconstruct;" + "after:techreborn;" + "after:thebetweenlands;" + "after:thermalexpansion;" + "after:tp;" + "after:waila";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        UTPacketHandler.init();
        UTAutoSaveOFCompat.updateOFConfig();

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (UTMixinLoader.regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.preInit();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributesToggle) UTAttributes.utSetAttributes();
            if (UTConfigTweaks.MISC.utSkipRegistryScreenToggle) System.setProperty("fml.queryResult", "confirm");
        }

        if (UTLoadingPlugin.isClient) UTMusicType.init();

        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            MinecraftForge.EVENT_BUS.register(UTVanillaEvents.class);
            if (UTConfigBugfixes.ENTITIES.utDimensionChangeToggle) MinecraftForge.EVENT_BUS.register(UTDimensionChange.class);
            if (UTConfigBugfixes.ENTITIES.utDoubleConsumptionToggle) MinecraftForge.EVENT_BUS.register(UTDoubleConsumption.class);
            if (UTConfigBugfixes.ENTITIES.utEntityNaNToggle) MinecraftForge.EVENT_BUS.register(UTEntityNaN.class);
            if (UTConfigBugfixes.ENTITIES.utEntityUUIDToggle) MinecraftForge.EVENT_BUS.register(UTEntityUUID.class);
            if (UTConfigBugfixes.WORLD.utPortalLocationLink) MinecraftForge.EVENT_BUS.register(UTPortalLocationLink.class);
            if (UTConfigBugfixes.WORLD.utPortalTravelingDupeToggle) MinecraftForge.EVENT_BUS.register(UTPortalTravelingDupe.class);
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (Loader.isModLoaded("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTArcaneArchivesEvents());
            if (Loader.isModLoaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTBloodMagicEvents());
            if (Loader.isModLoaded("collective") && UTConfigMods.COLLECTIVE.utMemoryLeakFixToggle) MinecraftForge.EVENT_BUS.register(new UTCollectiveEvents());
            if (Loader.isModLoaded("cqrepoured") && UTConfigMods.CHOCOLATE_QUEST.utCQRGoldenFeatherToggle) MinecraftForge.EVENT_BUS.register(new UTGoldenFeatherEvent());
            if (Loader.isModLoaded("elenaidodge2") && UTConfigMods.ELENAI_DODGE_2.utED2ExtinguishingDodgeChance > 0) MinecraftForge.EVENT_BUS.register(new UTED2Burning());
            if (Loader.isModLoaded("elenaidodge2") && UTConfigMods.ELENAI_DODGE_2.utED2SprintingFeatherConsumption > 0) MinecraftForge.EVENT_BUS.register(new UTED2Sprinting());
            if (Loader.isModLoaded("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle) UTMekanismFixes.fixBinRecipes();
            if (Loader.isModLoaded("projectred-exploration") && UTConfigMods.PROJECTRED.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTProjectRedWorldEvents());
            // Unregister reason: disable beta warning.
            if (Loader.isModLoaded("railcraft") && UTConfigMods.RAILCRAFT.utNoBetaWarningToggle && UTReflectionUtil.isClassLoaded("mods.railcraft.common.core.BetaMessageTickHandler"))
            {
                MinecraftForge.EVENT_BUS.unregister(BetaMessageTickHandler.INSTANCE);
            }
            if (Loader.isModLoaded("simplyjetpacks") && UTConfigMods.SIMPLY_JETPACKS.utMemoryLeakFixToggle)
            {
                MinecraftForge.EVENT_BUS.register(new UTSimplyJetpacksEvents());
                NetworkHandler.instance.registerMessage(MessageClientStatesReset.class, MessageClientStatesReset.class, NetworkHandler.nextID(), Side.CLIENT);
            }
            // Unregister reason: event handler adds to an unused map that is never cleared.
            if (Loader.isModLoaded("tardis") && UTConfigMods.TARDIS.utMemoryLeakFixToggle) MinecraftForge.EVENT_BUS.unregister(ClientProxy.class);
            if (UTMixinLoader.regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTTConstructEvents());
            if (Loader.isModLoaded("woot") && UTConfigMods.WOOT.utCleanupSimulatedKillsToggle) UTWootTicketManager.init();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            MinecraftForge.EVENT_BUS.register(UTBetterBurning.class);
            MinecraftForge.EVENT_BUS.register(UTFarmlandTrample.class);
            if (UTConfigTweaks.BLOCKS.ANVIL.utRepairableAnvilToggle) MinecraftForge.EVENT_BUS.register(UTRepairableAnvil.class);
            if (UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) MinecraftForge.EVENT_BUS.register(UTBreakableBedrock.class);
            if (UTConfigTweaks.BLOCKS.FINITE_WATER.utFiniteWaterToggle) MinecraftForge.EVENT_BUS.register(UTFiniteWater.class);
            if (UTConfigTweaks.BLOCKS.utBetterHarvestToggle) MinecraftForge.EVENT_BUS.register(UTBetterHarvest.class);
            if (UTConfigTweaks.BLOCKS.utSlimeBlockProjectiles) MinecraftForge.EVENT_BUS.register(UTSlimeBlockProjectiles.class);
            if (UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingToggle) MinecraftForge.EVENT_BUS.register(UTChickenShedding.class);
            if (UTConfigTweaks.ENTITIES.EASY_BREEDING.utEasyBreedingToggle) MinecraftForge.EVENT_BUS.register(UTEasyBreeding.class);
            if (UTConfigTweaks.ENTITIES.RALLY_HEALTH.utRallyHealthToggle) MinecraftForge.EVENT_BUS.register(UTRallyHealth.class);
            if (UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle) MinecraftForge.EVENT_BUS.register(UTSleeping.class);
            if (UTConfigTweaks.ENTITIES.utAdaptiveXPFactor > 0) MinecraftForge.EVENT_BUS.register(UTAdaptiveXPDrops.class);
            if (UTConfigTweaks.ENTITIES.utAIRemovalToggle) MinecraftForge.EVENT_BUS.register(UTRemoveAI.class);
            if (UTConfigTweaks.ENTITIES.utBetterIgnitionToggle) MinecraftForge.EVENT_BUS.register(UTBetterIgnition.class);
            if (UTConfigTweaks.ENTITIES.utCreeperChargedChance > 0) MinecraftForge.EVENT_BUS.register(UTChargedCreepers.class);
            if (UTConfigTweaks.ENTITIES.utModernKnockbackToggle) MinecraftForge.EVENT_BUS.register(UTModernKnockback.class);
            if (UTConfigTweaks.ENTITIES.utRabbitToastChance > 0 || UTConfigTweaks.ENTITIES.utRabbitKillerChance > 0) MinecraftForge.EVENT_BUS.register(UTRabbits.class);
            if (UTConfigTweaks.ENTITIES.utSoulboundVexesToggle) MinecraftForge.EVENT_BUS.register(UTSoulboundVexes.class);
            if (UTConfigTweaks.ENTITIES.utWitherAIToggle) MinecraftForge.EVENT_BUS.register(UTWitherAI.class);
            if (UTConfigTweaks.ITEMS.INFINITY.utBowInfinityToggle) MinecraftForge.EVENT_BUS.register(UTBowInfinity.class);
            if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEAutomaticPickupToggle) MinecraftForge.EVENT_BUS.register(UTEntityItemInteract.class);
            if (UTConfigTweaks.ITEMS.MENDING.utMendingToggle) MinecraftForge.EVENT_BUS.register(UTMending.class);
            if (UTConfigTweaks.ITEMS.PARRY.utParryToggle) MinecraftForge.EVENT_BUS.register(UTParry.class);
            if (UTConfigTweaks.ITEMS.utCustomUseDurations.length > 0) MinecraftForge.EVENT_BUS.register(UTCustomUseDuration.class);
            if (UTConfigTweaks.ITEMS.utSuperHotTorchToggle) MinecraftForge.EVENT_BUS.register(UTSuperHotTorch.class);
            if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle) UTArmorCurve.initExpressions();
            if (UTConfigTweaks.MISC.LIGHTNING.utLightningItemDestructionToggle) MinecraftForge.EVENT_BUS.register(UTLightningItemDestruction.class);
            if (UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) MinecraftForge.EVENT_BUS.register(UTSwingThroughGrass.class);
            if (UTConfigTweaks.MISC.utDamageTiltToggle) MinecraftForge.EVENT_BUS.register(UTDamageTilt.class);
            if (UTConfigTweaks.MISC.utOffhandToggle) MinecraftForge.EVENT_BUS.register(UTOffhand.class);
            if (UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderToggle) MinecraftForge.EVENT_BUS.register(UTUnloader.class);
            if (UTConfigTweaks.WORLD.utTidyChunkToggle) MinecraftForge.EVENT_BUS.register(UTTidyChunk.class);
        }

        LOGGER.info(NAME + " initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void initClient(FMLInitializationEvent event)
    {
        UTKeybindings.initialize();

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            if (UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (Loader.isModLoaded("astralsorcery") && UTConfigMods.ASTRAL_SORCERY.utClearEffectsOnDimensionChange) MinecraftForge.EVENT_BUS.register(new UTClearOnChange());
            if (Loader.isModLoaded("botania")) MinecraftForge.EVENT_BUS.register(UTBotaniaFancySkybox.class);
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle) MinecraftForge.EVENT_BUS.register(UTBetterPlacement.class);
            if (UTConfigTweaks.BLOCKS.utFenceWallJumpToggle) MinecraftForge.EVENT_BUS.register(UTFenceWallJump.class);
            if (UTConfigTweaks.ENTITIES.utCoyoteTimeJumpingToggle) MinecraftForge.EVENT_BUS.register(UTCoyoteTimeJumping.class);
            if (UTConfigTweaks.ITEMS.utAutoSwitchToggle) MinecraftForge.EVENT_BUS.register(UTAutoSwitch.class);
            if (UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotToggle) MinecraftForge.EVENT_BUS.register(UTAdvancementScreenshot.class);
            if (UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) MinecraftForge.EVENT_BUS.register(UTLoadSound.class);
            if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle) UTPickupNotificationOverlay.init();
            if (UTConfigTweaks.MISC.utEndPortalParallaxToggle) UTEndPortalParallax.initRenderer();
            if (UTConfigTweaks.MISC.utLANServerProperties) MinecraftForge.EVENT_BUS.register(UTLanServerProperties.class);
            if (UTConfigTweaks.MISC.utPotionShiftToggle) MinecraftForge.EVENT_BUS.register(UTPotionShift.class);
            if (UTConfigTweaks.MISC.utToggleCheatsToggle) MinecraftForge.EVENT_BUS.register(UTToggleCheats.class);
        }

        LOGGER.info(NAME + " client initialized");
    }

    @SideOnly(Side.SERVER)
    @Mod.EventHandler
    public void initServer(FMLInitializationEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            if (UTConfigBugfixes.ENTITIES.utDisconnectDupeToggle) MinecraftForge.EVENT_BUS.register(UTDisconnectDupe.class);
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {

        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            if (UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle) UTEntityDesync.initBlacklistedEntityEntries();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (UTMixinLoader.regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utTConMaterialBlacklist.length > 0) UTTConstructMaterials.utHandleBlacklistedMaterials();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.BLOCKS.ANVIL.utRepairableAnvilToggle) UTRepairableAnvil.initRepairItemsList();
            if (UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle) UTBlockDispenser.initBlockList();
            if (UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) UTBreakableBedrock.initToolList();
            if (UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementToggle) UTEndCrystalPlacement.initBlockList();
            if (UTConfigTweaks.BLOCKS.PISTON.utPistonBlockBlacklistToggle) UTPistonBlockBlacklist.initBlockBlacklist();
            if (UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeRestriction.length > 0) UTVillagerProfessionRestriction.initBiomeRestrictions();
            if (UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotToggle) UTAdvancementScreenshot.initAdvancementList();
            if (UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
            if (UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle) UTIncurablePotions.initPotionList();
            if (UTConfigTweaks.ITEMS.utLeftoverBreathBottleToggle) UTLeftoverDragonBreath.postInit();
            if (UTConfigTweaks.ITEMS.utCustomRarities.length > 0) UTCustomRarity.initItemRarityMap();
            if (UTConfigTweaks.ITEMS.utCustomUseDurations.length > 0) UTCustomUseDuration.initItemUseMaps();
            if (UTConfigTweaks.ITEMS.PARRY.utParryToggle) UTParry.initProjectileList();
            if (UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle) UTChunkGenLimit.initDimensionList();
            if (UTConfigTweaks.WORLD.VOID_FOG.utVoidFogToggle) UTVoidFog.initDimensionList();
        }

        LOGGER.info(NAME + " post-initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInitClient(FMLPostInitializationEvent event)
    {
        UTCommands.initClientCommands();

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (Loader.isModLoaded("botania")) UTBotaniaFancySkybox.initDimList();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
            if (UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlTutorialToggle) UTTutorialToast.utTutorialToast();
        }

        LOGGER.info(NAME + " client post-initialized");
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            if (UTConfigBugfixes.MISC.utHelpToggle) UTHelp.onServerStarting(event);
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle) UTCraftingCache.resetCache();
        }
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {
            if (UTConfigBugfixes.MISC.utHelpToggle) UTHelp.onServerStarted(event);
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {

        }
    }

    @Mod.EventHandler
    public void onServerStopped(FMLServerStoppedEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (Loader.isModLoaded("woot")) UTWootTicketManager.resetTicket();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {

        }
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchBugfixes)
        {

        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
        {
            if (UTMixinLoader.regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.onLoadComplete();
        }

        if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
        {
            if (UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle) UTEntityRadiusCheck.onLoadComplete();
        }

        if (UTConfigGeneral.DEBUG.utLoadingTimeToggle) LOGGER.info("The game loaded in approximately {} seconds", (System.currentTimeMillis() - UTLoadingPlugin.launchTime) / 1000F);

        if (UTObsoleteModsHandler.hasObsoleteModsMessage())
        {
            for (String line : UTObsoleteModsHandler.obsoleteModsMessage())
            {
                if (!line.isEmpty()) UniversalTweaks.LOGGER.warn(line);
            }
        }
    }
}
