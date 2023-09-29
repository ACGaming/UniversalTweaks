package mod.acgaming.universaltweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.bugfixes.entities.desync.UTEntityDesync;
import mod.acgaming.universaltweaks.bugfixes.misc.help.UTHelp;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.mods.abyssalcraft.UTAbyssalCraftEvents;
import mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata.UTWorldDataCapability;
import mod.acgaming.universaltweaks.mods.arcanearchives.UTArcaneArchivesEvents;
import mod.acgaming.universaltweaks.mods.bloodmagic.UTBloodMagicEvents;
import mod.acgaming.universaltweaks.mods.botania.UTBotaniaFancySkybox;
import mod.acgaming.universaltweaks.mods.cqrepoured.UTGoldenFeatherEvent;
import mod.acgaming.universaltweaks.mods.elenaidodge2.UTED2Sprinting;
import mod.acgaming.universaltweaks.mods.mekanism.UTMekanismFixes;
import mod.acgaming.universaltweaks.mods.projectred.UTProjectRedWorldEvents;
import mod.acgaming.universaltweaks.mods.simplyjetpacks.UTSimplyJetpacksEvents;
import mod.acgaming.universaltweaks.mods.simplyjetpacks.network.message.MessageClientStatesReset;
import mod.acgaming.universaltweaks.mods.tconstruct.UTTConstructEvents;
import mod.acgaming.universaltweaks.mods.tconstruct.UTTConstructMaterials;
import mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.UTOreDictCache;
import mod.acgaming.universaltweaks.mods.thaumcraft.UTThaumcraftEvents;
import mod.acgaming.universaltweaks.tweaks.blocks.betterplacement.UTBetterPlacement;
import mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.UTBreakableBedrock;
import mod.acgaming.universaltweaks.tweaks.blocks.dispenser.UTBlockDispenser;
import mod.acgaming.universaltweaks.tweaks.entities.attributes.UTAttributes;
import mod.acgaming.universaltweaks.tweaks.items.dragonbreath.UTLeftoverDragonBreath;
import mod.acgaming.universaltweaks.tweaks.items.parry.UTParry;
import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import mod.acgaming.universaltweaks.tweaks.misc.armorcurve.UTArmorCurve;
import mod.acgaming.universaltweaks.tweaks.misc.endportal.UTEndPortalParallax;
import mod.acgaming.universaltweaks.tweaks.misc.incurablepotions.UTIncurablePotions;
import mod.acgaming.universaltweaks.tweaks.misc.loadsound.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.misc.pickupnotification.UTPickupNotificationOverlay;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.UTTutorialToast;
import mod.acgaming.universaltweaks.tweaks.performance.autosave.UTAutoSaveOFCompat;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.UTStronghold;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.worldgen.SafeStrongholdWorldGenerator;
import mod.acgaming.universaltweaks.util.UTPacketHandler;
import mod.acgaming.universaltweaks.util.compat.UTObsoleteModsHandler;
import net.tardis.mod.proxy.ClientProxy;
import tonius.simplyjetpacks.network.NetworkHandler;

@Mod(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME, version = UniversalTweaks.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = UniversalTweaks.DEPENDENCIES)
public class UniversalTweaks
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    public static final String DEPENDENCIES = "required-after:mixinbooter@[8.0,);required-after:configanytime;"
        + "after:abyssalcraft;"
        + "after:actuallyadditions;"
        + "after:aoa3;"
        + "after:arcanearchives;"
        + "after:biomesoplenty;"
        + "after:bloodmagic;"
        + "after:botania;"
        + "after:ceramics;"
        + "after:chisel;"
        + "after:cofhcore;"
        + "after:collective;"
        + "after:contenttweaker;"
        + "after:element;"
        + "after:elenaidodge2;"
        + "after:enderio;"
        + "after:epicsiegemod;"
        + "after:erebus;"
        + "after:extratrees;"
        + "after:extrautils2;"
        + "after:farlanders;"
        + "after:forestry;"
        + "after:ic2;"
        + "after:industrialforegoing;"
        + "after:infernalmobs;"
        + "after:ironbackpacks;"
        + "after:mekanism;"
        + "after:netherchest;"
        + "after:netherrocks;"
        + "after:nuclearcraft;"
        + "after:projectred-exploration;"
        + "after:quark;"
        + "after:roost;"
        + "after:simplyjetpacks;"
        + "after:spiceoflife;"
        + "after:storagedrawers;"
        + "after:tardis;"
        + "after:tcomplement;"
        + "after:tconstruct;"
        + "after:thaumcraft;"
        + "after:thaumicwonders;"
        + "after:thermalexpansion;"
        + "after:tp";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        UTPacketHandler.init();
        if (UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributesToggle) UTAttributes.utSetAttributes();
        UTAutoSaveOFCompat.updateOFConfig();
        if (UTConfigTweaks.WORLD.utStrongholdToggle) GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
        if (Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.preInit();
        if (Loader.isModLoaded("abyssalcraft") && UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) UTWorldDataCapability.register();
        if (UTConfigTweaks.MISC.utSkipRegistryScreenToggle) System.setProperty("fml.queryResult", "confirm");
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle) UTArmorCurve.initExpressions();
        if (UTConfigTweaks.WORLD.utStrongholdToggle) MinecraftForge.TERRAIN_GEN_BUS.register(new UTStronghold());
        if (Loader.isModLoaded("abyssalcraft") && UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) MinecraftForge.EVENT_BUS.register(new UTAbyssalCraftEvents());
        if (Loader.isModLoaded("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTArcaneArchivesEvents());
        if (Loader.isModLoaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTBloodMagicEvents());
        if (Loader.isModLoaded("cqrepoured") && UTConfigMods.CHOCOLATE_QUEST.utCQRGoldenFeatherToggle) MinecraftForge.EVENT_BUS.register(new UTGoldenFeatherEvent());
        if (Loader.isModLoaded("elenaidodge2") && UTConfigMods.ELENAI_DODGE_2.utED2SprintingFeatherConsumption > 0) MinecraftForge.EVENT_BUS.register(new UTED2Sprinting());
        if (Loader.isModLoaded("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle) UTMekanismFixes.fixBinRecipes();
        if (Loader.isModLoaded("projectred-exploration") && UTConfigMods.PROJECTRED.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTProjectRedWorldEvents());
        if (Loader.isModLoaded("simplyjetpacks") && UTConfigMods.SIMPLY_JETPACKS.utMemoryLeakFixToggle)
        {
            MinecraftForge.EVENT_BUS.register(new UTSimplyJetpacksEvents());
            NetworkHandler.instance.registerMessage(MessageClientStatesReset.class, MessageClientStatesReset.class, NetworkHandler.nextID(), Side.CLIENT);
        }
        // Unregister reason: event handler adds to an unused map that is never cleared.
        if (Loader.isModLoaded("tardis") && UTConfigMods.TARDIS.utMemoryLeakFixToggle) MinecraftForge.EVENT_BUS.unregister(ClientProxy.class);
        if (Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTTConstructEvents());
        if (Loader.isModLoaded("thaumcraft") && UTConfigMods.THAUMCRAFT.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTThaumcraftEvents());
        LOGGER.info(NAME + " initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void initClient(FMLInitializationEvent event)
    {
        if (UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
        if (UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle) MinecraftForge.EVENT_BUS.register(UTBetterPlacement.class);
        if (UTConfigTweaks.MISC.utEndPortalParallaxToggle) UTEndPortalParallax.initRenderer();
        if (UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle) UTPickupNotificationOverlay.init();
        if (Loader.isModLoaded("botania")) MinecraftForge.EVENT_BUS.register(UTBotaniaFancySkybox.class);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle) UTEntityDesync.initBlacklistedEntityEntries();
        if (UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle) UTBlockDispenser.initBlockList();
        if (UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) UTBreakableBedrock.initToolList();
        if (UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
        if (UTConfigTweaks.MISC.INCURABLE_POTIONS.utIncurablePotionsToggle) UTIncurablePotions.initPotionList();
        if (UTConfigTweaks.ITEMS.utLeftoverBreathBottleToggle) UTLeftoverDragonBreath.postInit();
        if (UTConfigTweaks.ITEMS.utCustomRarities.length > 0) UTCustomRarity.initItemRarityMap();
        if (UTConfigTweaks.ITEMS.utCustomUseDurations.length > 0) UTCustomUseDuration.initItemUseMaps();
        if (UTConfigTweaks.ITEMS.PARRY.utParryToggle) UTParry.initProjectileList();
        if (Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConMaterialBlacklist.length > 0) UTTConstructMaterials.utHandleBlacklistedMaterials();
        LOGGER.info(NAME + " post-initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInitClient(FMLPostInitializationEvent event)
    {
        if (UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
        if (UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlTutorialToggle) UTTutorialToast.utTutorialToast();
        if (Loader.isModLoaded("botania")) UTBotaniaFancySkybox.initDimList();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfigBugfixes.MISC.utHelpToggle) UTHelp.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfigBugfixes.MISC.utHelpToggle) UTHelp.onServerStarted(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        if (Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.onLoadComplete();
        if (UTConfigGeneral.DEBUG.utLoadingTimeToggle) LOGGER.info("The game loaded in approximately {} seconds", (System.currentTimeMillis() - UTLoadingPlugin.launchTime) / 1000F);
        if (UTObsoleteModsHandler.showObsoleteMods && UTObsoleteModsHandler.obsoleteModsMessage().size() > 5 && !UTConfigGeneral.DEBUG.utBypassIncompatibilityToggle)
        {
            for (String line : UTObsoleteModsHandler.obsoleteModsMessage())
            {
                if (!line.equals("")) UniversalTweaks.LOGGER.warn(line);
            }
        }
    }
}
