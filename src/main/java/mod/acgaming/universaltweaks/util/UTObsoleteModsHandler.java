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
        if (Loader.isModLoaded("aiimprovements") && (UTConfig.tweaks.utAIReplacementToggle || UTConfig.tweaks.utAIRemovalToggle)) messages.add("AI Improvements");
        if (Loader.isModLoaded("attributefix") && UTConfig.tweaks.utAttributesToggle) messages.add("AttributeFix");
        if (Loader.isModLoaded("bedbreakbegone") && UTConfig.tweaks.utBedObstructionToggle) messages.add("BedBreakBegone");
        if (Loader.isModLoaded("betterplacement") && UTConfig.tweaks.utBPClickDelay != 4) messages.add("Better Placement");
        if (Loader.isModLoaded("blockfire") && UTConfig.bugfixes.utBlockFireToggle) messages.add("BlockFire");
        if (Loader.isModLoaded("blockoverlayfix") && UTConfig.bugfixes.utBlockOverlayToggle) messages.add("Block Overlay Fix");
        if (Loader.isModLoaded("bottomsugarcanharvest") && UTConfig.tweaks.utSugarCaneSize != 3) messages.add("Bottom Sugar Cane Harvest");
        if (Loader.isModLoaded("bowinfinityfix") && UTConfig.tweaks.utBowInfinityToggle) messages.add("Bow Infinity Fix");
        if (Loader.isModLoaded("chunkgenlimit") && UTConfig.tweaks.utChunkGenLimitToggle) messages.add("Chunk Generation Limiter");
        if (Loader.isModLoaded("classiccombat") && UTConfig.tweaks.utAttackCooldownToggle) messages.add("Classic Combat");
        if (Loader.isModLoaded("collisiondamage") && UTConfig.tweaks.utCollisionDamageToggle) messages.add("Collision Damage");
        if (Loader.isModLoaded("configurablecane") && UTConfig.tweaks.utSugarCaneSize != 3) messages.add("Configurable Cane");
        if (Loader.isModLoaded("continousmusic") && UTConfig.tweaks.utInfiniteMusicToggle) messages.add("Infinite Music");
        if (Loader.isModLoaded("creeperconfetti") && UTConfig.tweaks.utCreeperConfettiToggle) messages.add("Creeper Confetti");
        if (Loader.isModLoaded("damagetilt") && UTConfig.tweaks.utDamageTiltToggle) messages.add("Damage Tilt");
        if (Loader.isModLoaded("darkstone") && UTConfig.tweaks.utRedstoneLightingToggle) messages.add("Dark Redstone");
        if (Loader.isModLoaded("diethopper") && UTConfig.bugfixes.utDietHopperToggle) messages.add("Diet Hopper");
        if (Loader.isModLoaded("ding") && UTConfig.tweaks.utLoadSoundMode != 0) messages.add("Ding");
        if (Loader.isModLoaded("drawerfps") && UTConfig.mods.utSDRenderRange > 0) messages.add("DrawerFPS");
        if (Loader.isModLoaded("deuf") && UTConfig.bugfixes.utEntityUUIDToggle) messages.add("Duplicate Entity UUID Fix (DEUF)");
        if (Loader.isModLoaded("easybreeding") && UTConfig.tweaks.utEasyBreedingToggle) messages.add("Easy Breeding");
        if (Loader.isModLoaded("enablecheats") && UTConfig.tweaks.utToggleCheatsToggle) messages.add("Enable Cheats");
        if (Loader.isModLoaded("entity_desync_fix") && UTConfig.bugfixes.utEntityDesyncToggle) messages.add("EntityDesyncFix");
        if (Loader.isModLoaded("experiencebugfix") && UTConfig.bugfixes.utDimensionChangeToggle) messages.add("Fix Experience Bug");
        if (Loader.isModLoaded("fastleafdecay") && UTConfig.tweaks.utLeafDecayToggle) messages.add("Fast Leaf Decay");
        if (Loader.isModLoaded("fencejumper") && UTConfig.tweaks.utFenceWallJumpToggle) messages.add("Fence Jumper");
        if (Loader.isModLoaded("finite-fluid-control") && UTConfig.tweaks.utFiniteWaterToggle) messages.add("Finite Water Control");
        if (Loader.isModLoaded("framevoidpatch") && UTConfig.bugfixes.utItemFrameVoidToggle) messages.add("Frame Void Patch");
        if (Loader.isModLoaded("givemebackmyhp") && UTConfig.bugfixes.utMaxHealthToggle) messages.add("Give Me Back My HP");
        if (Loader.isModLoaded("helpfixer") && UTConfig.bugfixes.utHelpToggle) messages.add("HelpFixer");
        if (Loader.isModLoaded("horsestandstill") && UTConfig.tweaks.utSaddledWanderingToggle) messages.add("Stupid Horse Stand Still");
        if (Loader.isModLoaded("leafdecay") && UTConfig.tweaks.utLeafDecayToggle) messages.add("Leaf Decay Accelerator");
        if (Loader.isModLoaded("letmedespawn") && UTConfig.tweaks.utMobDespawnToggle) messages.add("Let Me Despawn");
        if (Loader.isModLoaded("loginhpfix") && UTConfig.bugfixes.utMaxHealthToggle) messages.add("Login HP Fix");
        if (Loader.isModLoaded("mendingfix") && UTConfig.tweaks.utMendingToggle) messages.add("Mending Fix");
        if (Loader.isModLoaded("nanfix") && UTConfig.bugfixes.utEntityNaNToggle) messages.add("NaN Entity Health Fix");
        if (Loader.isModLoaded("nobounce") && UTConfig.mods.utTCStableThaumometerToggle) messages.add("Stable Thaumometer");
        if (Loader.isModLoaded("nonvflash") && UTConfig.tweaks.utNightVisionFlashToggle) messages.add("No Night Vision Flashing");
        if (Loader.isModLoaded("nopotionshift") && UTConfig.tweaks.utPotionShiftToggle) messages.add("No Potion Shift");
        if (Loader.isModLoaded("norecipebook") && UTConfig.tweaks.utRecipeBookToggle) messages.add("No Recipe Book");
        if (Loader.isModLoaded("overpowered_mending") && UTConfig.tweaks.utMendingOPToggle) messages.add("Overpowered Mending");
        if (Loader.isModLoaded("quickleafdecay") && UTConfig.tweaks.utLeafDecayToggle) messages.add("Quick Leaf Decay");
        if (Loader.isModLoaded("savemystronghold") && UTConfig.tweaks.utStrongholdToggle) messages.add("Save My Stronghold!");
        if (Loader.isModLoaded("smooth-scrolling-everywhere") && UTConfig.tweaks.utSmoothScrollingToggle) messages.add("Smooth Scrolling Everywhere");
        if (Loader.isModLoaded("stepupfix") && UTConfig.tweaks.utAutoJumpToggle) messages.add("StepupFixer");
        if (Loader.isModLoaded("stg") && UTConfig.tweaks.utSwingThroughGrassToggle) messages.add("SwingThroughGrass");
        if (Loader.isModLoaded("superhot") && UTConfig.tweaks.utSuperHotTorchToggle) messages.add("SuperHot");
        if (Loader.isModLoaded("surge")) messages.add("Surge");
        if (Loader.isModLoaded("tconfixes")) messages.add("TConFixes");
        if (Loader.isModLoaded("tidychunk") && UTConfig.tweaks.utTidyChunkToggle) messages.add("TidyChunk");
        if (Loader.isModLoaded("unloader") && UTConfig.tweaks.utUnloaderToggle) messages.add("Unloader");
        if (Loader.isModLoaded("villagermantlefix") && UTConfig.bugfixes.utVillagerMantleToggle) messages.add("Villager Mantle Fix");
        if (Loader.isModLoaded("watercontrolextreme") && UTConfig.tweaks.utFiniteWaterToggle) messages.add("Water Control Extreme");
        try
        {
            if (UTConfig.bugfixes.utLocaleToggle)
            {
                Class.forName("io.github.jikuja.LocaleTweaker");
                messages.add("LocaleFixer");
            }
        }
        catch (ClassNotFoundException ignored) {}
        messages.add("");
        messages.add(new TextComponentTranslation("msg.universaltweaks.obsoletemods.warning3").getFormattedText());
        return messages;
    }
}