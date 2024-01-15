package mod.acgaming.universaltweaks.util.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTObsoleteModsHandler
{
    public static boolean showObsoleteMods = true;

    public static List<String> obsoleteModsMessage()
    {
        List<String> messages = new ArrayList<>();
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning1").getFormattedText());
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning2").getFormattedText());
        messages.add("");
        if (Loader.isModLoaded("aiimprovements") && (UTConfigTweaks.ENTITIES.utAIReplacementToggle || UTConfigTweaks.ENTITIES.utAIRemovalToggle)) messages.add("AI Improvements");
        if (Loader.isModLoaded("armorcurve") && UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle) messages.add("Armor Curve");
        if (Loader.isModLoaded("attributefix") && UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributesToggle) messages.add("AttributeFix");
        if (Loader.isModLoaded("bannerpatch") && UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle) messages.add("BannerPatch");
        if (Loader.isModLoaded("bedbreakbegone") && UTConfigTweaks.BLOCKS.utBedObstructionToggle) messages.add("BedBreakBegone");
        if (Loader.isModLoaded("bedfix") && UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime != -1) messages.add("BedFix");
        if (Loader.isModLoaded("bedsaynosleep") && UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle) messages.add("Sleepn't");
        if (Loader.isModLoaded("betterburning") && (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBArrowsToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBCookedToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBExtinguishToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBOverlayToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBSpreadingToggle)) messages.add("Better Burning");
        if (Loader.isModLoaded("betterplacement") && UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle) messages.add("Better Placement");
        if (Loader.isModLoaded("biggerpacketsplz") && UTConfigBugfixes.MISC.utPacketSize > 0x200000) messages.add("Bigger Packets Please");
        if (Loader.isModLoaded("blockdispenser") && UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle) messages.add("BlockDispenser");
        if (Loader.isModLoaded("blockfire") && UTConfigBugfixes.ENTITIES.utBlockFireToggle) messages.add("BlockFire");
        if (Loader.isModLoaded("blockoverlayfix") && UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) messages.add("Block Overlay Fix");
        if (Loader.isModLoaded("bottomsugarcanharvest") && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3) messages.add("Bottom Sugar Cane Harvest");
        if (Loader.isModLoaded("bowinfinityfix") && UTConfigTweaks.ITEMS.utBowInfinityToggle) messages.add("Bow Infinity Fix");
        if (Loader.isModLoaded("breedablekillerrabbit") && UTConfigTweaks.ENTITIES.utRabbitKillerChance > 0.0D) messages.add("Breedable Killer Rabbit");
        if (Loader.isModLoaded("burnbabyburn") && UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle) messages.add("BurnBabyBurn");
        if (Loader.isModLoaded("chickensshed") && UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingToggle) messages.add("ChickensShed");
        if (Loader.isModLoaded("cie") && UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle) messages.add("Configurable Item Entities (CIE)");
        if (Loader.isModLoaded("classiccombat") && UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle) messages.add("Classic Combat");
        if (Loader.isModLoaded("cleardespawn") && UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEClearDespawnToggle) messages.add("Clear Despawn");
        if (Loader.isModLoaded("collisiondamage") && UTConfigTweaks.ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle) messages.add("Collision Damage");
        if (Loader.isModLoaded("configurablecane") && UTConfigTweaks.BLOCKS.utSugarCaneSize != 3) messages.add("Configurable Cane");
        if (Loader.isModLoaded("configurabledespawntimer") && UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle) messages.add("Configurable Despawn Timer");
        if (Loader.isModLoaded("continousmusic") && UTConfigTweaks.MISC.utInfiniteMusicToggle) messages.add("Infinite Music");
        if (Loader.isModLoaded("creeperconfetti") && UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance > 0) messages.add("Creeper Confetti");
        if (Loader.isModLoaded("damagetilt") && UTConfigTweaks.MISC.utDamageTiltToggle) messages.add("Damage Tilt");
        if (Loader.isModLoaded("darkstone") && UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle) messages.add("Dark Redstone");
        if (Loader.isModLoaded("deuf") && UTConfigBugfixes.ENTITIES.utEntityUUIDToggle) messages.add("Duplicate Entity UUID Fix (DEUF)");
        if (Loader.isModLoaded("diethopper") && UTConfigBugfixes.BLOCKS.utDietHopperToggle) messages.add("Diet Hopper");
        if (Loader.isModLoaded("ding") && UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) messages.add("Ding");
        if (Loader.isModLoaded("drawerfps") && UTConfigMods.STORAGE_DRAWERS.utSDRenderRange > 0) messages.add("DrawerFPS");
        if (Loader.isModLoaded("dupefixproject")) messages.add("DupeFix Project");
        if (Loader.isModLoaded("easybreeding") && UTConfigTweaks.ENTITIES.EASY_BREEDING.utEasyBreedingToggle) messages.add("Easy Breeding");
        if (Loader.isModLoaded("enablecheats") && UTConfigTweaks.MISC.utToggleCheatsToggle) messages.add("Enable Cheats");
        if (Loader.isModLoaded("endportalparallax") && UTConfigTweaks.MISC.utEndPortalParallaxToggle) messages.add("End Portal Parallax");
        if (Loader.isModLoaded("entity_desync_fix") && UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle) messages.add("EntityDesyncFix");
        if (Loader.isModLoaded("erebusfix") && UTConfigMods.EREBUS.utEBPreservedBlocksToggle) messages.add("Erebus Fix");
        if (Loader.isModLoaded("experiencebugfix") && UTConfigBugfixes.ENTITIES.utDimensionChangeToggle) messages.add("Fix Experience Bug");
        if (Loader.isModLoaded("eyltrafix") && UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle) messages.add("Elytra-Fix");
        if (Loader.isModLoaded("fastbench") && UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle) messages.add("FastWorkbench");
        if (Loader.isModLoaded("fastleafdecay") && UTConfigTweaks.BLOCKS.utLeafDecayToggle) messages.add("Fast Leaf Decay");
        if (Loader.isModLoaded("fencejumper") && UTConfigTweaks.BLOCKS.utFenceWallJumpToggle) messages.add("Fence Jumper");
        if (Loader.isModLoaded("finite-fluid-control") && UTConfigTweaks.BLOCKS.FINITE_WATER.utFiniteWaterToggle) messages.add("Finite Water Control");
        if (Loader.isModLoaded("framevoidpatch") && UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle) messages.add("Frame Void Patch");
        if (Loader.isModLoaded("getittogetherdrops") && UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle) messages.add("Get It Together, Drops!");
        if (Loader.isModLoaded("givemebackmyhp") && UTConfigBugfixes.ENTITIES.utMaxHealthToggle) messages.add("Give Me Back My HP");
        if (Loader.isModLoaded("gottagofast") && UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("Gotta Go Fast");
        if (Loader.isModLoaded("helpfixer") && UTConfigBugfixes.MISC.utHelpToggle) messages.add("HelpFixer");
        if (Loader.isModLoaded("hiddenrecipebook") && UTConfigTweaks.MISC.utRecipeBookToggle) messages.add("Hidden Recipe Book");
        if (Loader.isModLoaded("horsefallfix") && UTConfigBugfixes.ENTITIES.utHorseFallingToggle) messages.add("HorseFallFix");
        if (Loader.isModLoaded("horsestandstill") && UTConfigTweaks.ENTITIES.utSaddledWanderingToggle) messages.add("Stupid Horse Stand Still");
        if (Loader.isModLoaded("ikwid") && UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlTutorialToggle) messages.add("I Know What I'm Doing");
        if (Loader.isModLoaded("insomniac") && UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle) messages.add("Insomniac");
        if (Loader.isModLoaded("inventoryspam") && UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle) messages.add("Inventory Spam");
        if (Loader.isModLoaded("leafdecay") && UTConfigTweaks.BLOCKS.utLeafDecayToggle) messages.add("Leaf Decay Accelerator");
        if (Loader.isModLoaded("letmedespawn") && UTConfigTweaks.ENTITIES.utMobDespawnToggle) messages.add("Let Me Despawn");
        if (Loader.isModLoaded("loginhpfix") && UTConfigBugfixes.ENTITIES.utMaxHealthToggle) messages.add("Login HP Fix");
        if (Loader.isModLoaded("maxhealthfixer") && UTConfigBugfixes.ENTITIES.utMaxHealthToggle) messages.add("MaxHealthFixer");
        if (Loader.isModLoaded("mendingfix") && UTConfigTweaks.ITEMS.MENDING.utMendingToggle) messages.add("Mending Fix");
        if (Loader.isModLoaded("movingquickly") && UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("MovingQuickly");
        if (Loader.isModLoaded("mtqfix") && UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("MTQFix");
        if (Loader.isModLoaded("naturallychargedcreepers") && UTConfigTweaks.ENTITIES.utCreeperChargedChance > 0) messages.add("Naturally Charged Creepers");
        if (Loader.isModLoaded("nanfix") && UTConfigBugfixes.ENTITIES.utEntityNaNToggle) messages.add("NaN Entity Health Fix");
        if (Loader.isModLoaded("nanpolice") && UTConfigBugfixes.ENTITIES.utEntityNaNToggle) messages.add("NaNPolice");
        if (Loader.isModLoaded("nobounce") && UTConfigMods.THAUMCRAFT.utTCStableThaumometerToggle) messages.add("Stable Thaumometer");
        if (Loader.isModLoaded("nodoze") && UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle) messages.add("No Doze");
        if (Loader.isModLoaded("nonvflash") && UTConfigTweaks.MISC.utNightVisionFlashToggle) messages.add("No Night Vision Flashing");
        if (Loader.isModLoaded("nopotionshift") && UTConfigTweaks.MISC.utPotionShiftToggle) messages.add("No Potion Shift");
        if (Loader.isModLoaded("noprecipebook") && UTConfigTweaks.MISC.utRecipeBookToggle) messages.add("Nop Recipe Book");
        if (Loader.isModLoaded("norecipebook") && UTConfigTweaks.MISC.utRecipeBookToggle) messages.add("No Recipe Book");
        if (Loader.isModLoaded("oldcombat") && UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle) messages.add("Old Combat Mechanics");
        if (Loader.isModLoaded("overpowered_mending") && UTConfigTweaks.ITEMS.MENDING.utMendingOPToggle) messages.add("Overpowered Mending");
        if (Loader.isModLoaded("parry") && UTConfigTweaks.ITEMS.PARRY.utParryToggle) messages.add("Shield Parry");
        if (Loader.isModLoaded("pathundergates") && UTConfigTweaks.BLOCKS.utLenientPathsToggle) messages.add("Path Under Gates");
        if (Loader.isModLoaded("pickupnotifier") && UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle) messages.add("Pick Up Notifier");
        if (Loader.isModLoaded("portaldupebegone") && UTConfigBugfixes.WORLD.utPortalTravelingDupeToggle) messages.add("PortalDupeBegone");
        if (Loader.isModLoaded("preventghost") && UTConfigBugfixes.BLOCKS.utMiningGlitchToggle) messages.add("Prevent Ghost Blocks");
        if (Loader.isModLoaded("quickleafdecay") && UTConfigTweaks.BLOCKS.utLeafDecayToggle) messages.add("Quick Leaf Decay");
        if (Loader.isModLoaded("rallyhealth") && UTConfigTweaks.ENTITIES.RALLY_HEALTH.utRallyHealthToggle) messages.add("Rally Health");
        if (Loader.isModLoaded("salwayseat") && UTConfigTweaks.ITEMS.utAlwaysEatToggle) messages.add("AlwaysEat");
        if (Loader.isModLoaded("savemystronghold") && UTConfigTweaks.WORLD.utStrongholdToggle) messages.add("Save My Stronghold!");
        if (Loader.isModLoaded("sleepsooner") && UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime != -1) messages.add("Sleep Sooner");
        if (Loader.isModLoaded("smooth-scrolling-everywhere") && UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle) messages.add("Smooth Scrolling Everywhere");
        if (Loader.isModLoaded("stepupfix") && UTConfigTweaks.ENTITIES.utAutoJumpToggle) messages.add("StepupFixer");
        if (Loader.isModLoaded("stg") && UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) messages.add("SwingThroughGrass");
        if (Loader.isModLoaded("superhot") && UTConfigTweaks.ITEMS.utSuperHotTorchToggle) messages.add("SuperHot");
        if (Loader.isModLoaded("surge")) messages.add("Surge");
        if (Loader.isModLoaded("tconfixes")) messages.add("TConFixes");
        if (Loader.isModLoaded("tidychunk") && UTConfigTweaks.WORLD.utTidyChunkToggle) messages.add("TidyChunk");
        if (Loader.isModLoaded("tinkersoredictcache") && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) messages.add("TinkersOreDictCache");
        if (Loader.isModLoaded("toastcontrol") && UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle) messages.add("Toast Control");
        if (Loader.isModLoaded("unloader") && UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderToggle) messages.add("Unloader");
        if (Loader.isModLoaded("villagermantlefix") && UTConfigBugfixes.ENTITIES.utVillagerMantleToggle) messages.add("Villager Mantle Fix");
        if (Loader.isModLoaded("watercontrolextreme") && UTConfigTweaks.BLOCKS.FINITE_WATER.utFiniteWaterToggle) messages.add("Water Control Extreme");
        try
        {
            Class.forName("com.chocohead.biab.BornInABarn");
            messages.add("Born in a Barn");
            if (UTConfigBugfixes.MISC.utLocaleToggle)
            {
                Class.forName("io.github.jikuja.LocaleTweaker");
                messages.add("LocaleFixer");
            }
            if (UTConfigTweaks.BLOCKS.utBlockHitDelay != 5)
            {
                Class.forName("com.cleanroommc.blockdelayremover.BlockDelayRemoverCore");
                messages.add("Block Delay Remover");
            }
            if (UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle)
            {
                Class.forName("io.github.barteks2x.chunkgenlimiter.ChunkGenLimitMod");
                messages.add("Chunk Generation Limiter");
            }
        }
        catch (ClassNotFoundException ignored) {}
        messages.add("");
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning3").getFormattedText());
        return messages;
    }
}