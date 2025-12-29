package mod.acgaming.universaltweaks.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import zone.rong.mixinbooter.Context;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{

    private static final Map<String, Predicate<Context>> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            put("mixins/mods/mixins.randomthings.teleport.json", c -> c.isModPresent("randomthings") && UTConfigMods.RANDOM_THINGS.utTeleportStall);
            put("mixins/mods/mixins.quark.linkitems.json", c -> c.isModPresent("quark") && UTConfigMods.QUARK.utLinkItemsServer);
        }
    });

    private static final Map<String, Predicate<Context>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins/mods/mixins.actuallyadditions.itemparticle.json", c -> c.isModPresent("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utItemLaserParticlesGraphics > -1);
                put("mixins/mods/mixins.bibliocraft.lefthand.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utSwapDisplayHandWhenLeftHanded);
                put("mixins/mods/mixins.bibliocraft.sign.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utFancySignRotationToggle);
                put("mixins/mods/mixins.bibliocraft.version.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utDisableVersionCheckToggle);
                put("mixins/mods/mixins.cbmultipart.client.json", c -> c.isModPresent("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
                put("mixins/mods/mixins.compactmachines.memory.json", c -> c.isModPresent("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utMemoryLeakFixToggle);
                put("mixins/mods/mixins.compactmachines.render.json", c -> c.isModPresent("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utCMRenderFixToggle);
                put("mixins/mods/mixins.corpse.json", c -> c.isModPresent("corpse") && UTConfigMods.CORPSE.utOpeningGuisOffThreadFixToggle);
                put("mixins/mods/mixins.crafttweaker.json", c -> c.isModPresent("crafttweaker"));
                put("mixins/mods/mixins.electroblobswizardry.json", c -> c.isModPresent("ebwizardry") && c.isModPresent("conarm") && UTConfigMods.ELECTROBLOBS_WIZARDRY.utConstructsArmoryFixToggle);
                put("mixins/mods/mixins.enderio.itemrender.json", c -> c.isModPresent("enderio") && UTConfigMods.ENDER_IO.utReplaceItemRenderer);
                put("mixins/mods/mixins.fpsreducer.json", c -> c.isModPresent("fpsreducer") && UTConfigMods.FPS_REDUCER.utCorrectFpsValue);
                put("mixins/mods/mixins.hwyla.json", c -> c.isModPresent("waila"));
                put("mixins/mods/mixins.ironchests.json", c -> c.isModPresent("ironchest") && UTConfigMods.IRON_CHESTS.utReplaceItemRenderer);
                put("mixins/mods/mixins.modularmagic.nullingredient.json", c -> c.isModPresent("modularmagic") && UTConfigMods.MODULAR_MAGIC.utEnsureIngredientNotNull);
                put("mixins/mods/mixins.modularrouters.json", c -> c.isModPresent("modularrouters") && UTConfigMods.MODULAR_ROUTERS.utParticleThreadToggle);
                put("mixins/mods/mixins.roost.json", c -> c.isModPresent("roost") && c.isModPresent("contenttweaker"));
                put("mixins/mods/mixins.storagedrawers.client.json", c -> c.isModPresent("storagedrawers"));
                put("mixins/mods/mixins.tconstruct.client.json", c -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utParticleFixesToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins/tweaks/mixins.misc.recipebook.betweenlands.client.json", c -> c.isModPresent("thebetweenlands") && UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins/tweaks/mixins.misc.recipebook.buildcraft.client.json", c -> c.isModPresent("buildcraftcore") && UTConfigTweaks.MISC.utRecipeBookToggle);
            }
        }
    });

    private static final Map<String, Predicate<Context>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Predicate<Context>>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins/mods/mixins.abyssalcraft.json", c -> c.isModPresent("abyssalcraft"));
                put("mixins/mods/mixins.actuallyadditions.dupes.json", c -> c.isModPresent("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utDuplicationFixesToggle);
                put("mixins/mods/mixins.actuallyadditions.relayupgrade.json", c -> c.isModPresent("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utLaserUpgradeVoid);
                put("mixins/mods/mixins.agricraft.json", c -> c.isModPresent("agricraft") && UTConfigMods.AGRICRAFT.utEnderIOPluginFixToggle);
                put("mixins/mods/mixins.aoa3.json", c -> c.isModPresent("aoa3") && UTConfigMods.AOA.utImprovedPlayerTickToggle);
                put("mixins/mods/mixins.arcanearchives.dupes.json", c -> c.isModPresent("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle);
                put("mixins/mods/mixins.astralsorcery.json", c -> c.isModPresent("astralsorcery"));
                put("mixins/mods/mixins.astralsorcery.neromanticprime.json", c -> c.isModPresent("astralsorcery"));
                put("mixins/mods/mixins.astralsorcery.tool.json", c -> c.isModPresent("astralsorcery") && UTConfigMods.ASTRAL_SORCERY.utEmptyPropertiesZero);
                put("mixins/mods/mixins.backpack.json", c -> c.isModPresent("backpack") && UTConfigMods.BACKPACKS.utBPNoOffhandInteractionToggle);
                put("mixins/mods/mixins.bewitchment.json", c -> c.isModPresent("bewitchment") && UTConfigMods.BEWITCHMENT.utWitchesOvenFixToggle);
                put("mixins/mods/mixins.bewitchment.leaves.json", c -> c.isModPresent("bewitchment") && UTConfigMods.BEWITCHMENT.utLeavesChechFix);
                put("mixins/mods/mixins.bibliocraft.armor.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utArmorStandSlotFixToggle);
                put("mixins/mods/mixins.bibliocraft.armorbinding.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utArmorStandBindingCurseToggle);
                put("mixins/mods/mixins.bibliocraft.hand.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utFixHandConsumption);
                put("mixins/mods/mixins.bibliocraft.handler.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utEnsureIItemHandlerMethodToggle);
                put("mixins/mods/mixins.bibliocraft.itemstack.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utCopyItemStackCorrectlyToggle);
                put("mixins/mods/mixins.bibliocraft.printpress.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utPrintingPressAnyBlackDyeToggle);
                put("mixins/mods/mixins.bibliocraft.transfer.json", c -> c.isModPresent("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utFixItemTransferToggle);
                put("mixins/mods/mixins.biomesoplenty.json", c -> c.isModPresent("biomesoplenty"));
                put("mixins/mods/mixins.biomesoplenty.sealevel.json", c -> c.isModPresent("biomesoplenty") && UTConfigTweaks.WORLD.utSeaLevel != 63);
                put("mixins/mods/mixins.bloodmagic.boundtool.json", c -> c.isModPresent("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utBoundToolTweakToggle);
                put("mixins/mods/mixins.bloodmagic.dupes.json", c -> c.isModPresent("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle);
                put("mixins/mods/mixins.bloodmagic.fluidrouting.json", c -> c.isModPresent("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utFluidRoutingFixToggle);
                put("mixins/mods/mixins.bloodmagic.ritual.json", c -> c.isModPresent("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utBMRitualToggle);
                put("mixins/mods/mixins.bloodmagic.json", c -> c.isModPresent("bloodmagic"));
                put("mixins/mods/mixins.botania.dupes.json", c -> c.isModPresent("botania") && UTConfigMods.BOTANIA.utDuplicationFixesToggle);
                put("mixins/mods/mixins.botania.json", c -> c.isModPresent("botania"));
                put("mixins/mods/mixins.bwm.json", c -> c.isModPresent("betterwithmods") && UTConfigMods.BWM.utBeaconNBTLoadingFix);
                put("mixins/mods/mixins.cbmultipart.json", c -> c.isModPresent("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
                put("mixins/mods/mixins.ceramics.json", c -> c.isModPresent("ceramics"));
                put("mixins/mods/mixins.chisel.tcomplement.dupes.json", c -> c.isModPresent("chisel") && c.isModPresent("tcomplement") && UTConfigMods.CHISEL.utDuplicationFixesToggle);
                put("mixins/mods/mixins.codechickenlib.json", c -> c.isModPresent("codechickenlib") && UTConfigMods.CCL.utPacketLeakFixToggle);
                put("mixins/mods/mixins.cofhcore.json", c -> c.isModPresent("cofhcore"));
                put("mixins/mods/mixins.cofhworld.json", c -> c.isModPresent("cofhworld") && UTConfigMods.COFH_WORLD.utCoFHSuperflatToggle);
                put("mixins/mods/mixins.collective.json", c -> c.isModPresent("collective"));
                put("mixins/mods/mixins.compactmachines.spawns.json", c -> c.isModPresent("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utAllowedSpawnsImprovementToggle);
                put("mixins/mods/mixins.cookingforblockheads.json", c -> c.isModPresent("cookingforblockheads") && UTConfigMods.COOKING_FOR_BLOCKHEADS.utOvenFixToggle);
                put("mixins/mods/mixins.cqrepoured.json", c -> c.isModPresent("cqrepoured"));
                put("mixins/mods/mixins.cyclic.json", c -> c.isModPresent("cyclicmagic") && UTConfigMods.CYCLIC.utMemoryLeakFixToggle);
                put("mixins/mods/mixins.dankstorage.json", c -> c.isModPresent("dankstorage"));
                put("mixins/mods/mixins.divinerpg.aquamarine.json", c -> c.isModPresent("divinerpg") && UTConfigMods.DIVINE_RPG.utFixAquamarineStackSize);
                put("mixins/mods/mixins.divinerpg.armorset.json", c -> c.isModPresent("divinerpg") && UTConfigMods.DIVINE_RPG.utFixArmorSetCleanup);
                put("mixins/mods/mixins.divinerpg.hand.json", c -> c.isModPresent("divinerpg") && UTConfigMods.DIVINE_RPG.utFixHandConsumption);
                put("mixins/mods/mixins.divinerpg.waterspawning.json", c -> c.isModPresent("divinerpg") && UTConfigMods.DIVINE_RPG.utChangeWaterMobCreatureType);
                put("mixins/mods/mixins.effortlessbuilding.json", c -> c.isModPresent("effortlessbuilding") && UTConfigMods.EFFORTLESS_BUILDING.utEFTransmutationFixToggle);
                put("mixins/mods/mixins.elementarystaffs.json", c -> c.isModPresent("element"));
                put("mixins/mods/mixins.elenaidodge2.json", c -> c.isModPresent("elenaidodge2"));
                put("mixins/mods/mixins.enderio.chorus.json", c -> c.isModPresent("enderio") && UTConfigMods.ENDER_IO.utChorusStackOverflow);
                put("mixins/mods/mixins.enderio.cyclebutton.json", c -> c.isModPresent("enderio") && UTConfigMods.ENDER_IO.utSaveFilterCycleButtonProperly);
                put("mixins/mods/mixins.enderio.soulbinderjei.json", c -> c.isModPresent("enderio") && UTConfigMods.ENDER_IO.utFixSoulBinderJEI);
                put("mixins/mods/mixins.enderstorage.json", c -> c.isModPresent("enderstorage") && UTConfigMods.ENDER_STORAGE.utFrequencyTrackFixToggle);
                put("mixins/mods/mixins.epicsiegemod.json", c -> c.isModPresent("epicsiegemod"));
                put("mixins/mods/mixins.erebus.cabbage.json", c -> c.isModPresent("erebus") && UTConfigMods.EREBUS.utCabbageDropToggle);
                put("mixins/mods/mixins.erebus.json", c -> c.isModPresent("erebus"));
                put("mixins/mods/mixins.gaiadimension.restructurer.json", c -> c.isModPresent("gaiadimension") && UTConfigMods.GAIA_DIMENSION.utFixNPERestructurerRecipe);
                put("mixins/mods/mixins.erebus.quakehammer.json", c -> c.isModPresent("erebus") && UTConfigMods.EREBUS.utFixQuakeHammerTextureToggle);
                put("mixins/mods/mixins.evilcraft.vengeancespirit.regex.json", c -> c.isModPresent("evilcraft") && UTConfigMods.EVIL_CRAFT.utVengeanceSpiritCache);
                put("mixins/mods/mixins.evilcraft.vengeancespirit.random.json", c -> c.isModPresent("evilcraft") && UTConfigMods.EVIL_CRAFT.utVengeanceSpiritRandom);
                put("mixins/mods/mixins.extrautilities.breakcreativemill.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utFixCreativeMillHarvestability);
                put("mixins/mods/mixins.extrautilities.deepdarkstats.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats);
                put("mixins/mods/mixins.extrautilities.dupes.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDuplicationFixesToggle);
                put("mixins/mods/mixins.extrautilities.mutabledrops.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utMutableBlockDrops);
                put("mixins/mods/mixins.extrautilities.potionlogging.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDowngradePotionLogging);
                put("mixins/mods/mixins.extrautilities.radarexception.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utCatchRadarException);
                put("mixins/mods/mixins.extrautilities.radarloot.json", c -> c.isModPresent("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utRadarSkipsLoottables);
                put("mixins/mods/mixins.forestry.extratrees.json", c -> c.isModPresent("extratrees") && UTConfigMods.FORESTRY.utFOGatherWindfallToggle);
                put("mixins/mods/mixins.forestry.json", c -> c.isModPresent("forestry"));
                put("mixins/mods/mixins.immersiveengineering.toolevent.json", c -> c.isModPresent("immersiveengineering") && UTConfigMods.IMMERSIVE_ENGINEERING.utFireBreakEvent);
                put("mixins/mods/mixins.immersiveengineering.toolhand.json", c -> c.isModPresent("immersiveengineering") && UTConfigMods.IMMERSIVE_ENGINEERING.utFixIncorrectHandReplacement);
                put("mixins/mods/mixins.incontrol.json", c -> c.isModPresent("incontrol") && UTConfigMods.INCONTROL.utStatsFixToggle);
                put("mixins/mods/mixins.industrialcraft.dupes.json", c -> c.isModPresent("ic2") && UTConfigMods.INDUSTRIALCRAFT.utDuplicationFixesToggle);
                put("mixins/mods/mixins.industrialforegoing.dupes.json", c -> c.isModPresent("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utDuplicationFixesToggle);
                put("mixins/mods/mixins.industrialforegoing.rangeaddon.json", c -> c.isModPresent("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utRangeAddonNumberFix);
                put("mixins/mods/mixins.infernalmobs.json", c -> c.isModPresent("infernalmobs"));
                put("mixins/mods/mixins.ironbackpacks.dupes.json", c -> c.isModPresent("ironbackpacks") && UTConfigMods.IRON_BACKPACKS.utDuplicationFixesToggle);
                put("mixins/mods/mixins.itemfavorites.unixfix.json", c -> c.isModPresent("itemfav") && UTConfigMods.ITEM_FAVORITES.utUnixPathFix);
                put("mixins/mods/mixins.itemstages.json", c -> c.isModPresent("itemstages"));
                put("mixins/mods/mixins.jurassicreborn.json", c -> c.isModPresent("rebornmod"));
                put("mixins/mods/mixins.mekanism.dupes.json", c -> c.isModPresent("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle);
                put("mixins/mods/mixins.mekanism.fluidtank.json", c -> c.isModPresent("mekanism") && UTConfigMods.MEKANISM.utFluidTankExtraction);
                put("mixins/mods/mixins.moartinkers.json", c -> c.isModPresent("moartinkers") && UTConfigMods.MOAR_TINKERS.utBaublesCompatibility);
                put("mixins/mods/mixins.mobstages.json", c -> c.isModPresent("mobstages"));
                put("mixins/mods/mixins.mrtjpcore.json", c -> c.isModPresent("mrtjpcore") && UTConfigMods.MRTJPCORE.utMemoryLeakFixToggle);
                put("mixins/mods/mixins.netherchest.dupes.json", c -> c.isModPresent("netherchest") && UTConfigMods.NETHER_CHEST.utDuplicationFixesToggle);
                put("mixins/mods/mixins.netherrocks.json", c -> c.isModPresent("netherrocks"));
                put("mixins/mods/mixins.nuclearcraft.json", c -> c.isModPresent("nuclearcraft"));
                put("mixins/mods/mixins.openblocks.json", c -> regularOpenBlocksLoaded() && UTConfigMods.OPEN_BLOCKS.utLastStandFixToggle);
                put("mixins/mods/mixins.properpumpkins.json", c -> c.isModPresent("pumpking") && UTConfigMods.PROPER_PUMPKIN.utFacingFix);
                put("mixins/mods/mixins.quark.dupes.json", c -> c.isModPresent("quark") && UTConfigMods.QUARK.utDuplicationFixesToggle);
                put("mixins/mods/mixins.randomthings.anvil.json", c -> c.isModPresent("randomthings") && UTConfigMods.RANDOM_THINGS.utAnvilCraftFix);
                put("mixins/mods/mixins.randomthings.collector.json", c -> c.isModPresent("randomthings") && UTConfigMods.RANDOM_THINGS.utItemCollectorDupe);
                put("mixins/mods/mixins.requiousfrakto.json", c -> c.isModPresent("requious") && UTConfigMods.REQUIOUS_FRAKTO.utParticleFixesToggle);
                put("mixins/mods/mixins.reskillable.json", c -> c.isModPresent("reskillable"));
                put("mixins/mods/mixins.rftoolsdimensions.json", c -> c.isModPresent("rftoolsdim"));
                put("mixins/mods/mixins.roost.contenttweaker.json", c -> c.isModPresent("roost") && c.isModPresent("contenttweaker"));
                put("mixins/mods/mixins.roots.creativepouch.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utDisableCreativePouchGUI);
                put("mixins/mods/mixins.roots.disabledmodifier.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utFixDisableModifierVoiding);
                put("mixins/mods/mixins.roots.icicle.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utFixIcicleSaving);
                put("mixins/mods/mixins.roots.mortar.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utFixMortarSpellDust);
                put("mixins/mods/mixins.roots.shatter.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utPreventShatterOnUnbreakable);
                put("mixins/mods/mixins.roots.soil.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utPreventSoilNeighborUpdates);
                put("mixins/mods/mixins.roots.spiritdrops.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utFixSpiritDrops);
                put("mixins/mods/mixins.roots.summon.json", c -> c.isModPresent("roots") && UTConfigMods.ROOTS.utFixSummoningInfiniteDescent);
                put("mixins/mods/mixins.simpledifficulty.json", c -> c.isModPresent("simpledifficulty"));
                put("mixins/mods/mixins.spiceoflife.dupes.json", c -> c.isModPresent("spiceoflife") && UTConfigMods.SPICE_OF_LIFE.utDuplicationFixesToggle);
                put("mixins/mods/mixins.steamworld.json", c -> c.isModPresent("steamworld") && UTConfigMods.STEAMWORLD.utSkyOfOldFixToggle);
                put("mixins/mods/mixins.storagedrawers.json", c -> c.isModPresent("storagedrawers") && UTConfigMods.STORAGE_DRAWERS.utSDItemVoidingFixToggle);
                put("mixins/mods/mixins.tconstruct.json", c -> regularTConLoaded());
                put("mixins/mods/mixins.tconstruct.oredictcache.json", c -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle);
                put("mixins/mods/mixins.tconstruct.toolcustomization.json", c -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolCustomizationToggle);
                put("mixins/mods/mixins.tconstruct.toolcustomization.plustic.json", c -> regularTConLoaded() && c.isModPresent("plustic") && UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolCustomizationToggle);
                put("mixins/mods/mixins.techreborn.json", c -> c.isModPresent("techreborn"));
                put("mixins/mods/mixins.testdummy.copyarmor.json", c -> c.isModPresent("testdummy") && UTConfigMods.TEST_DUMMY.utCopyArmor);
                put("mixins/mods/mixins.thefarlanders.dupes.json", c -> c.isModPresent("farlanders") && UTConfigMods.THE_FARLANDERS.utDuplicationFixesToggle);
                put("mixins/mods/mixins.thermalexpansion.dupes.json", c -> c.isModPresent("thermalexpansion") && UTConfigMods.THERMAL_EXPANSION.utDuplicationFixesToggle);
                put("mixins/mods/mixins.thermalexpansion.json", c -> c.isModPresent("thermalexpansion"));
                put("mixins/mods/mixins.tinyprogressions.dupes.json", c -> c.isModPresent("tp") && UTConfigMods.TINY_PROGRESSIONS.utDuplicationFixesToggle);
                put("mixins/mods/mixins.woot.json", c -> c.isModPresent("woot") && UTConfigMods.WOOT.utCleanupSimulatedKillsToggle);
            }
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchTweaks)
            {
                put("mixins/tweaks/mixins.blocks.enchantmenttable.bookshelf.json", c -> c.isModPresent("bookshelf") && UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle);
            }
        }
    });

    public static boolean regularOpenBlocksLoaded()
    {
        if (Loader.isModLoaded("openblocks"))
        {
            return Loader.instance().getIndexedModList().get("openblocks").getName().equals("OpenBlocks");
        }
        return false;
    }

    public static boolean regularTConLoaded()
    {
        if (Loader.isModLoaded("tconstruct"))
        {
            return Loader.instance().getIndexedModList().get("tconstruct").getName().equals("Tinkers' Construct");
        }
        return false;
    }

    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        if (UTLoadingPlugin.isClient) configs.addAll(clientsideMixinConfigs.keySet());
        else configs.addAll(serversideMixinConfigs.keySet());
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(Context context)
    {
        String mixinConfig = context.mixinConfig();
        Predicate<Context> sidedPredicate = UTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : serversideMixinConfigs.get(mixinConfig);
        Predicate<Context> commonPredicate = commonMixinConfigs.get(mixinConfig);
        return sidedPredicate != null ? sidedPredicate.test(context) : commonPredicate == null || commonPredicate.test(context);
    }
}
