package mod.acgaming.universaltweaks.util.compat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTReflectionUtil;

public class UTObsoleteModsHandler
{
    private static final Map<String, Supplier<Boolean>> obsoleteModMap = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>()
    {
        {
            put("aiimprovements", () -> UTConfigTweaks.ENTITIES.utAIReplacementToggle || UTConfigTweaks.ENTITIES.utAIRemovalToggle);
            put("armorcurve", () -> UTConfigTweaks.MISC.ARMOR_CURVE.utArmorCurveToggle);
            put("attributefix", () -> UTConfigTweaks.ENTITIES.ATTRIBUTES.utAttributesToggle);
            put("bannerpatch", () -> UTConfigBugfixes.BLOCKS.utBannerBoundingBoxToggle);
            put("bedbreakbegone", () -> UTConfigTweaks.BLOCKS.utBedObstructionToggle);
            put("bedfix", () -> UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime != -1);
            put("bedsaynosleep", () -> UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle);
            put("betterburning", () -> UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBArrowsToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBCookedToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBExtinguishToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBOverlayToggle || UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBSpreadingToggle);
            put("betterpingdisplay", () -> UTConfigTweaks.MISC.utBetterPing);
            put("betterplacement", () -> UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle);
            put("biggerchathistory", () -> UTConfigTweaks.MISC.CHAT.utChatLines != 100);
            put("biggerpacketsplz", () -> UTConfigBugfixes.MISC.utPacketSize > 0x200000);
            put("blockdispenser", () -> UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle);
            put("blockfire", () -> UTConfigBugfixes.ENTITIES.utBlockFireToggle);
            put("blockoverlayfix", () -> UTConfigBugfixes.BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle);
            put("bottomsugarcanharvest", () -> UTConfigTweaks.BLOCKS.utSugarCaneSize != 3);
            put("bowinfinityfix", () -> UTConfigTweaks.ITEMS.INFINITY.utBowInfinityToggle);
            put("breedablekillerrabbit", () -> UTConfigTweaks.ENTITIES.utRabbitKillerChance > 0.0D);
            put("burnbabyburn", () -> UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle);
            put("chickensshed", () -> UTConfigTweaks.ENTITIES.CHICKEN_SHEDDING.utChickenSheddingToggle);
            put("cie", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
            put("classiccombat", () -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
            put("cleardespawn", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEClearDespawnToggle);
            put("collisiondamage", () -> UTConfigTweaks.ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle);
            put("configurablecane", () -> UTConfigTweaks.BLOCKS.utSugarCaneSize != 3);
            put("configurabledespawntimer", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
            put("continousmusic", () -> UTConfigTweaks.MISC.utInfiniteMusicToggle);
            put("creeperconfetti", () -> UTConfigTweaks.ENTITIES.CREEPER_CONFETTI.utCreeperConfettiChance > 0);
            put("damagetilt", () -> UTConfigTweaks.MISC.utDamageTiltToggle);
            put("darkstone", () -> UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle);
            put("deuf", () -> UTConfigBugfixes.ENTITIES.utEntityUUIDToggle);
            put("diethopper", () -> UTConfigBugfixes.BLOCKS.utDietHopperToggle);
            put("ding", () -> UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING);
            put("drawerfps", () -> UTConfigMods.STORAGE_DRAWERS.utSDRenderRange > 0);
            put("dupefixproject", () -> true);
            put("easybreeding", () -> UTConfigTweaks.ENTITIES.EASY_BREEDING.utEasyBreedingToggle);
            put("enablecheats", () -> UTConfigTweaks.MISC.utToggleCheatsToggle);
            put("endportalparallax", () -> UTConfigTweaks.MISC.utEndPortalParallaxToggle);
            put("entity_desync_fix", () -> UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle);
            put("erebusfix", () -> UTConfigMods.EREBUS.utEBPreservedBlocksToggle);
            put("experiencebugfix", () -> UTConfigBugfixes.ENTITIES.utDimensionChangeToggle);
            put("eyltrafix", () -> UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle);
            put("fastbench", () -> UTConfigTweaks.PERFORMANCE.utCraftingCacheToggle);
            put("fastleafdecay", () -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
            put("fencejumper", () -> UTConfigTweaks.BLOCKS.utFenceWallJumpToggle);
            put("finite-fluid-control", () -> UTConfigTweaks.BLOCKS.FINITE_WATER.utFiniteWaterToggle);
            put("forgivingvoid", () -> UTConfigTweaks.ENTITIES.VOID_TELEPORT.utVoidTeleportToggle);
            put("framevoidpatch", () -> UTConfigBugfixes.BLOCKS.utItemFrameVoidToggle);
            put("getittogetherdrops", () -> UTConfigTweaks.ITEMS.ITEM_ENTITIES.utItemEntitiesToggle);
            put("givemebackmyhp", () -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
            put("gottagofast", () -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
            put("helpfixer", () -> UTConfigBugfixes.MISC.utHelpToggle);
            put("hiddenrecipebook", () -> UTConfigTweaks.MISC.utRecipeBookToggle);
            put("horsefallfix", () -> UTConfigBugfixes.ENTITIES.utHorseFallingToggle);
            put("horsestandstill", () -> UTConfigTweaks.ENTITIES.utSaddledWanderingToggle);
            put("ikwid", () -> UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlTutorialToggle);
            put("infinityworkswithallarrows", () -> UTConfigTweaks.ITEMS.INFINITY.utAllArrowsAreInfinite);
            put("infwithmend", () -> UTConfigTweaks.ITEMS.INFINITY.utInfinityEnchantmentConflicts);
            put("insomniac", () -> UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle);
            put("inventoryspam", () -> UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle);
            put("keydescfix", () -> UTConfigTweaks.MISC.utPreventKeybindingEntryOverflow);
            put("lanserverproperties", () -> UTConfigTweaks.MISC.utLANServerProperties);
            put("leafdecay", () -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
            put("letmedespawn", () -> UTConfigTweaks.ENTITIES.utMobDespawnToggle);
            put("loginhpfix", () -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
            put("maxhealthfixer", () -> UTConfigBugfixes.ENTITIES.utMaxHealthToggle);
            put("mendingfix", () -> UTConfigTweaks.ITEMS.MENDING.utMendingToggle);
            put("movingquickly", () -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
            put("mtqfix", () -> UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle);
            put("mup", () -> true);
            put("muteuselesslogs", () -> UTConfigTweaks.PERFORMANCE.utPrefixCheckToggle || UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle);
            put("nanfix", () -> UTConfigBugfixes.ENTITIES.utEntityNaNToggle);
            put("nanpolice", () -> UTConfigBugfixes.ENTITIES.utEntityNaNToggle);
            put("naturallychargedcreepers", () -> UTConfigTweaks.ENTITIES.utCreeperChargedChance > 0);
            put("noadvancements", () -> UTConfigTweaks.MISC.utDisableAdvancementsToggle);
            put("nobounce", () -> UTConfigMods.THAUMCRAFT.utTCStableThaumometerToggle);
            put("nodoze", () -> UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle);
            put("nonvflash", () -> UTConfigTweaks.MISC.utNightVisionFlashToggle);
            put("nopotionshift", () -> UTConfigTweaks.MISC.utPotionShiftToggle);
            put("noprecipebook", () -> UTConfigTweaks.MISC.utRecipeBookToggle);
            put("norecipebook", () -> UTConfigTweaks.MISC.utRecipeBookToggle);
            put("oldcombat", () -> UTConfigTweaks.ITEMS.ATTACK_COOLDOWN.utAttackCooldownToggle);
            put("overpowered_mending", () -> UTConfigTweaks.ITEMS.MENDING.utMendingOPToggle);
            put("parry", () -> UTConfigTweaks.ITEMS.PARRY.utParryToggle);
            put("pathundergates", () -> UTConfigTweaks.BLOCKS.utLenientPathsToggle);
            put("pickupnotifier", () -> UTConfigTweaks.MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle);
            put("portaldupebegone", () -> UTConfigBugfixes.WORLD.utPortalTravelingDupeToggle);
            put("ppa", () -> UTConfigTweaks.BLOCKS.utUnsupportedPumpkinPlacing);
            put("preventghost", () -> UTConfigBugfixes.BLOCKS.MINING_GLITCH.utMiningGlitchToggle);
            put("quickleafdecay", () -> UTConfigTweaks.BLOCKS.utLeafDecayToggle);
            put("rallyhealth", () -> UTConfigTweaks.ENTITIES.RALLY_HEALTH.utRallyHealthToggle);
            put("rebind_narrator", () -> UTConfigTweaks.MISC.utUseCustomNarratorKeybind);
            put("salwayseat", () -> UTConfigTweaks.ITEMS.utAlwaysEatToggle);
            put("savemystronghold", () -> UTConfigTweaks.WORLD.utStrongholdToggle);
            put("sleepsooner", () -> UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime != -1);
            put("smooth-scrolling-everywhere", () -> UTConfigTweaks.MISC.SMOOTH_SCROLLING.utSmoothScrollingToggle);
            put("stepupfix", () -> UTConfigTweaks.ENTITIES.utAutoJumpToggle);
            put("stg", () -> UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle);
            put("superhot", () -> UTConfigTweaks.ITEMS.utSuperHotTorchToggle);
            put("surge", () -> true);
            put("tconfixes", () -> true);
            put("tidychunk", () -> UTConfigTweaks.WORLD.utTidyChunkToggle);
            put("tinkersoredictcache", () -> UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle);
            put("toastcontrol", () -> UTConfigTweaks.MISC.TOAST_CONTROL.utToastControlToggle);
            put("tramplestopper", () -> UTConfigTweaks.BLOCKS.utFarmlandTrample != UTConfigTweaks.TrampleOptions.DEFAULT);
            put("unloader", () -> UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderToggle);
            put("unridekeybind", () -> UTConfigTweaks.MISC.utUseSeparateDismountKey);
            put("villagermantlefix", () -> UTConfigBugfixes.ENTITIES.utVillagerMantleToggle);
            put("voidfog", () -> UTConfigTweaks.WORLD.VOID_FOG.utVoidFogToggle);
            put("watercontrolextreme", () -> UTConfigTweaks.BLOCKS.FINITE_WATER.utFiniteWaterToggle);
        }
    });

    public static boolean showObsoleteMods = true;

    public static List<String> obsoleteModsMessage()
    {
        List<String> messages = new ArrayList<>();
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning1").getFormattedText());
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning2").getFormattedText());
        messages.add("");

        Map<String, ModContainer> modIdMap = Loader.instance().getIndexedModList();
        for (String modId : obsoleteModMap.keySet())
        {
            if (Loader.isModLoaded(modId) && obsoleteModMap.get(modId).get())
            {
                messages.add(modIdMap.get(modId).getName());
            }
        }

        // Mods checked by class
        if (UTReflectionUtil.isClassLoaded("com.chocohead.biab.BornInABarn")) messages.add("Born in a Barn");
        if (UTReflectionUtil.isClassLoaded("io.github.jikuja.LocaleTweaker") && UTConfigBugfixes.MISC.utLocaleToggle) messages.add("LocaleFixer");
        if (UTReflectionUtil.isClassLoaded("com.cleanroommc.blockdelayremover.BlockDelayRemoverCore") && UTConfigTweaks.BLOCKS.utBlockHitDelay != 5) messages.add("Block Delay Remover");
        if (UTReflectionUtil.isClassLoaded("io.github.barteks2x.chunkgenlimiter.ChunkGenLimitMod") && UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle) messages.add("Chunk Generation Limiter");
        messages.add("");
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning3").getFormattedText());
        return messages;
    }

}