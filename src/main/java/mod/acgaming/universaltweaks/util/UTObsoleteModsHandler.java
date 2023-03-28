package mod.acgaming.universaltweaks.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfig;

public class UTObsoleteModsHandler
{
    public static List<String> obsoleteModsMessage()
    {
        List<String> messages = new ArrayList<>();
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning1").getFormattedText());
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning2").getFormattedText());
        messages.add("");
        if (Loader.isModLoaded("aiimprovements") && (UTConfig.TWEAKS_ENTITIES.utAIReplacementToggle || UTConfig.TWEAKS_ENTITIES.utAIRemovalToggle)) messages.add("AI Improvements");
        if (Loader.isModLoaded("attributefix") && UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributesToggle) messages.add("AttributeFix");
        if (Loader.isModLoaded("bedbreakbegone") && UTConfig.TWEAKS_BLOCKS.utBedObstructionToggle) messages.add("BedBreakBegone");
        if (Loader.isModLoaded("betterburning") && (UTConfig.TWEAKS_ENTITIES.BETTER_BURNING.utBBArrowsToggle || UTConfig.TWEAKS_ENTITIES.BETTER_BURNING.utBBCookedToggle || UTConfig.TWEAKS_ENTITIES.BETTER_BURNING.utBBExtinguishToggle || UTConfig.TWEAKS_ENTITIES.BETTER_BURNING.utBBOverlayToggle || UTConfig.TWEAKS_ENTITIES.BETTER_BURNING.utBBSpreadingToggle)) messages.add("Better Burning");
        if (Loader.isModLoaded("betterplacement") && UTConfig.TWEAKS_BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle) messages.add("Better Placement");
        if (Loader.isModLoaded("blockfire") && UTConfig.BUGFIXES_ENTITIES.utBlockFireToggle) messages.add("BlockFire");
        if (Loader.isModLoaded("blockoverlayfix") && UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) messages.add("Block Overlay Fix");
        if (Loader.isModLoaded("bottomsugarcanharvest") && UTConfig.TWEAKS_BLOCKS.utSugarCaneSize != 3) messages.add("Bottom Sugar Cane Harvest");
        if (Loader.isModLoaded("bowinfinityfix") && UTConfig.TWEAKS_ITEMS.utBowInfinityToggle) messages.add("Bow Infinity Fix");
        if (Loader.isModLoaded("cie") && UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utItemEntitiesToggle) messages.add("Configurable Item Entities (CIE)");
        if (Loader.isModLoaded("classiccombat") && UTConfig.TWEAKS_ITEMS.utAttackCooldownToggle) messages.add("Classic Combat");
        if (Loader.isModLoaded("cleardespawn") && UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEClearDespawnToggle) messages.add("Clear Despawn");
        if (Loader.isModLoaded("collisiondamage") && UTConfig.TWEAKS_ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle) messages.add("Collision Damage");
        if (Loader.isModLoaded("configurablecane") && UTConfig.TWEAKS_BLOCKS.utSugarCaneSize != 3) messages.add("Configurable Cane");
        if (Loader.isModLoaded("continousmusic") && UTConfig.TWEAKS_MISC.utInfiniteMusicToggle) messages.add("Infinite Music");
        if (Loader.isModLoaded("creeperconfetti") && UTConfig.TWEAKS_ENTITIES.utCreeperConfettiToggle) messages.add("Creeper Confetti");
        if (Loader.isModLoaded("damagetilt") && UTConfig.TWEAKS_MISC.utDamageTiltToggle) messages.add("Damage Tilt");
        if (Loader.isModLoaded("darkstone") && UTConfig.TWEAKS_MISC.utRedstoneLightingToggle) messages.add("Dark Redstone");
        if (Loader.isModLoaded("deuf") && UTConfig.BUGFIXES_ENTITIES.utEntityUUIDToggle) messages.add("Duplicate Entity UUID Fix (DEUF)");
        if (Loader.isModLoaded("diethopper") && UTConfig.BUGFIXES_BLOCKS.utDietHopperToggle) messages.add("Diet Hopper");
        if (Loader.isModLoaded("ding") && UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfig.TweaksMiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) messages.add("Ding");
        if (Loader.isModLoaded("drawerfps") && UTConfig.MOD_INTEGRATION.STORAGE_DRAWERS.utSDRenderRange > 0) messages.add("DrawerFPS");
        if (Loader.isModLoaded("easybreeding") && UTConfig.TWEAKS_ENTITIES.EASY_BREEDING.utEasyBreedingToggle) messages.add("Easy Breeding");
        if (Loader.isModLoaded("enablecheats") && UTConfig.TWEAKS_MISC.utToggleCheatsToggle) messages.add("Enable Cheats");
        if (Loader.isModLoaded("endportalparallax") && UTConfig.TWEAKS_MISC.utEndPortalParallaxToggle) messages.add("End Portal Parallax");
        if (Loader.isModLoaded("entity_desync_fix") && UTConfig.BUGFIXES_ENTITIES.utEntityDesyncToggle) messages.add("EntityDesyncFix");
        if (Loader.isModLoaded("experiencebugfix") && UTConfig.BUGFIXES_ENTITIES.utDimensionChangeToggle) messages.add("Fix Experience Bug");
        if (Loader.isModLoaded("fastbench") && UTConfig.TWEAKS_PERFORMANCE.utCraftingCacheToggle) messages.add("FastWorkbench");
        if (Loader.isModLoaded("fastleafdecay") && UTConfig.TWEAKS_BLOCKS.utLeafDecayToggle) messages.add("Fast Leaf Decay");
        if (Loader.isModLoaded("fencejumper") && UTConfig.TWEAKS_BLOCKS.utFenceWallJumpToggle) messages.add("Fence Jumper");
        if (Loader.isModLoaded("finite-fluid-control") && UTConfig.TWEAKS_BLOCKS.FINITE_WATER.utFiniteWaterToggle) messages.add("Finite Water Control");
        if (Loader.isModLoaded("framevoidpatch") && UTConfig.BUGFIXES_BLOCKS.utItemFrameVoidToggle) messages.add("Frame Void Patch");
        if (Loader.isModLoaded("getittogetherdrops") && UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utItemEntitiesToggle) messages.add("Get It Together, Drops!");
        if (Loader.isModLoaded("givemebackmyhp") && UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) messages.add("Give Me Back My HP");
        if (Loader.isModLoaded("gottagofast") && UTConfig.TWEAKS_ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("Gotta Go Fast");
        if (Loader.isModLoaded("helpfixer") && UTConfig.BUGFIXES_MISC.utHelpToggle) messages.add("HelpFixer");
        if (Loader.isModLoaded("horsestandstill") && UTConfig.TWEAKS_ENTITIES.utSaddledWanderingToggle) messages.add("Stupid Horse Stand Still");
        if (Loader.isModLoaded("ikwid") && UTConfig.TWEAKS_MISC.TOAST_CONTROL.utToastControlTutorialToggle) messages.add("I Know What I'm Doing");
        if (Loader.isModLoaded("leafdecay") && UTConfig.TWEAKS_BLOCKS.utLeafDecayToggle) messages.add("Leaf Decay Accelerator");
        if (Loader.isModLoaded("letmedespawn") && UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle) messages.add("Let Me Despawn");
        if (Loader.isModLoaded("loginhpfix") && UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) messages.add("Login HP Fix");
        if (Loader.isModLoaded("maxhealthfixer") && UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) messages.add("MaxHealthFixer");
        if (Loader.isModLoaded("mendingfix") && UTConfig.TWEAKS_ITEMS.MENDING.utMendingToggle) messages.add("Mending Fix");
        if (Loader.isModLoaded("movingquickly") && UTConfig.TWEAKS_ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("MovingQuickly");
        if (Loader.isModLoaded("mtqfix") && UTConfig.TWEAKS_ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) messages.add("MTQFix");
        if (Loader.isModLoaded("naturallychargedcreepers") && UTConfig.TWEAKS_ENTITIES.utChargedCreeperSpawnChance > 0) messages.add("Naturally Charged Creepers");
        if (Loader.isModLoaded("nanfix") && UTConfig.BUGFIXES_ENTITIES.utEntityNaNToggle) messages.add("NaN Entity Health Fix");
        if (Loader.isModLoaded("nobounce") && UTConfig.MOD_INTEGRATION.THAUMCRAFT.utTCStableThaumometerToggle) messages.add("Stable Thaumometer");
        if (Loader.isModLoaded("nonvflash") && UTConfig.TWEAKS_MISC.utNightVisionFlashToggle) messages.add("No Night Vision Flashing");
        if (Loader.isModLoaded("nopotionshift") && UTConfig.TWEAKS_MISC.utPotionShiftToggle) messages.add("No Potion Shift");
        if (Loader.isModLoaded("noprecipebook") && UTConfig.TWEAKS_MISC.utRecipeBookToggle) messages.add("Nop Recipe Book");
        if (Loader.isModLoaded("norecipebook") && UTConfig.TWEAKS_MISC.utRecipeBookToggle) messages.add("No Recipe Book");
        if (Loader.isModLoaded("overpowered_mending") && UTConfig.TWEAKS_ITEMS.MENDING.utMendingOPToggle) messages.add("Overpowered Mending");
        if (Loader.isModLoaded("parry") && UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle) messages.add("Shield Parry");
        if (Loader.isModLoaded("preventghost") && UTConfig.BUGFIXES_BLOCKS.utMiningGlitchToggle) messages.add("Prevent Ghost Blocks");
        if (Loader.isModLoaded("quickleafdecay") && UTConfig.TWEAKS_BLOCKS.utLeafDecayToggle) messages.add("Quick Leaf Decay");
        if (Loader.isModLoaded("rallyhealth") && UTConfig.TWEAKS_MISC.RALLY_HEALTH.utRallyHealthToggle) messages.add("Rally Health");
        if (Loader.isModLoaded("savemystronghold") && UTConfig.TWEAKS_WORLD.utStrongholdToggle) messages.add("Save My Stronghold!");
        if (Loader.isModLoaded("smooth-scrolling-everywhere") && UTConfig.TWEAKS_MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle) messages.add("Smooth Scrolling Everywhere");
        if (Loader.isModLoaded("stepupfix") && UTConfig.TWEAKS_ENTITIES.utAutoJumpToggle) messages.add("StepupFixer");
        if (Loader.isModLoaded("stg") && UTConfig.TWEAKS_MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) messages.add("SwingThroughGrass");
        if (Loader.isModLoaded("superhot") && UTConfig.TWEAKS_ITEMS.utSuperHotTorchToggle) messages.add("SuperHot");
        if (Loader.isModLoaded("surge")) messages.add("Surge");
        if (Loader.isModLoaded("tconfixes")) messages.add("TConFixes");
        if (Loader.isModLoaded("tidychunk") && UTConfig.TWEAKS_WORLD.utTidyChunkToggle) messages.add("TidyChunk");
        if (Loader.isModLoaded("tinkersoredictcache") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) messages.add("TinkersOreDictCache");
        if (Loader.isModLoaded("toastcontrol") && UTConfig.TWEAKS_MISC.TOAST_CONTROL.utToastControlToggle) messages.add("Toast Control");
        if (Loader.isModLoaded("unloader") && UTConfig.TWEAKS_WORLD.DIMENSION_UNLOAD.utUnloaderToggle) messages.add("Unloader");
        if (Loader.isModLoaded("villagermantlefix") && UTConfig.BUGFIXES_ENTITIES.utVillagerMantleToggle) messages.add("Villager Mantle Fix");
        if (Loader.isModLoaded("watercontrolextreme") && UTConfig.TWEAKS_BLOCKS.FINITE_WATER.utFiniteWaterToggle) messages.add("Water Control Extreme");
        try
        {
            if (UTConfig.BUGFIXES_MISC.utLocaleToggle)
            {
                Class.forName("io.github.jikuja.LocaleTweaker");
                messages.add("LocaleFixer");
            }
            if (UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle)
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