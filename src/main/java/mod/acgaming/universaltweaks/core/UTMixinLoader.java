package mod.acgaming.universaltweaks.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;

import com.google.common.collect.ImmutableMap;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import zone.rong.mixinbooter.Context;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{

    private static final Map<String, BooleanSupplier> serversideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            put("mixins.mods.randomthings.teleport.json", () -> loaded("randomthings") && UTConfigMods.RANDOM_THINGS.utTeleportStall);
            put("mixins.mods.quark.linkitems.json", () -> loaded("quark") && UTConfigMods.QUARK.utLinkItemsServer);
        }
    });

    private static final Map<String, BooleanSupplier> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins.mods.actuallyadditions.itemparticle.json", () -> loaded("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utItemLaserParticlesGraphics > -1);
                put("mixins.mods.bibliocraft.json", () -> loaded("bibliocraft") && UTConfigMods.BIBLIOCRAFT.utDisableVersionCheckToggle);
                put("mixins.mods.cbmultipart.client.json", () -> loaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
                put("mixins.mods.compactmachines.memory.json", () -> loaded("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utMemoryLeakFixToggle);
                put("mixins.mods.compactmachines.render.json", () -> loaded("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utCMRenderFixToggle);
                put("mixins.mods.corpse.json", () -> loaded("corpse") && UTConfigMods.CORPSE.utOpeningGuisOffThreadFixToggle);
                put("mixins.mods.crafttweaker.json", () -> loaded("crafttweaker"));
                put("mixins.mods.electroblobswizardry.json", () -> loaded("ebwizardry") && loaded("conarm") && UTConfigMods.ELECTROBLOBS_WIZARDRY.utConstructsArmoryFixToggle);
                put("mixins.mods.enderio.itemrender.json", () -> loaded("enderio") && UTConfigMods.ENDER_IO.utReplaceItemRenderer);
                put("mixins.mods.fpsreducer.json", () -> loaded("fpsreducer") && UTConfigMods.FPS_REDUCER.utCorrectFpsValue);
                put("mixins.mods.hwyla.json", () -> loaded("waila"));
                put("mixins.mods.ironchests.json", () -> loaded("ironchest") && UTConfigMods.IRON_CHESTS.utReplaceItemRenderer);
                put("mixins.mods.modularmagic.nullingredient.json", () -> loaded("modularmagic") && UTConfigMods.MODULAR_MAGIC.utEnsureIngredientNotNull);
                put("mixins.mods.modularrouters.json", () -> loaded("modularrouters") && UTConfigMods.MODULAR_ROUTERS.utParticleThreadToggle);
                put("mixins.mods.roost.json", () -> loaded("roost") && loaded("contenttweaker"));
                put("mixins.mods.storagedrawers.client.json", () -> loaded("storagedrawers"));
                put("mixins.mods.tconstruct.client.json", () -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utParticleFixesToggle);
                put("mixins.tweaks.misc.recipebook.betweenlands.client.json", () -> loaded("thebetweenlands") && UTConfigTweaks.MISC.utRecipeBookToggle);
                put("mixins.tweaks.misc.recipebook.buildcraft.client.json", () -> loaded("buildcraftcore") && UTConfigTweaks.MISC.utRecipeBookToggle);
            }
        }
    });

    private static final Map<String, BooleanSupplier> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, BooleanSupplier>()
    {
        {
            if (UTConfigGeneral.MASTER_SWITCHES.utMasterSwitchModIntegration)
            {
                put("mixins.mods.abyssalcraft.json", () -> loaded("abyssalcraft"));
                put("mixins.mods.actuallyadditions.dupes.json", () -> loaded("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utDuplicationFixesToggle);
                put("mixins.mods.actuallyadditions.relayupgrade.json", () -> loaded("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utLaserUpgradeVoid);
                put("mixins.mods.agricraft.json", () -> loaded("agricraft") && UTConfigMods.AGRICRAFT.utEnderIOPluginFixToggle);
                put("mixins.mods.aoa3.json", () -> loaded("aoa3") && UTConfigMods.AOA.utImprovedPlayerTickToggle);
                put("mixins.mods.arcanearchives.dupes.json", () -> loaded("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle);
                put("mixins.mods.astralsorcery.json", () -> loaded("astralsorcery"));
                put("mixins.mods.astralsorcery.neromanticprime.json", () -> loaded("astralsorcery"));
                put("mixins.mods.astralsorcery.tool.json", () -> loaded("astralsorcery") && UTConfigMods.ASTRAL_SORCERY.utEmptyPropertiesZero);
                put("mixins.mods.backpack.json", () -> loaded("backpack") && UTConfigMods.BACKPACKS.utBPNoOffhandInteractionToggle);
                put("mixins.mods.bewitchment.json", () -> loaded("bewitchment") && UTConfigMods.BEWITCHMENT.utWitchesOvenFixToggle);
                put("mixins.mods.biomesoplenty.json", () -> loaded("biomesoplenty"));
                put("mixins.mods.biomesoplenty.sealevel.json", () -> loaded("biomesoplenty") && UTConfigTweaks.WORLD.utSeaLevel != 63);
                put("mixins.mods.bloodmagic.boundtool.json", () -> loaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utBoundToolTweakToggle);
                put("mixins.mods.bloodmagic.dupes.json", () -> loaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle);
                put("mixins.mods.bloodmagic.fluidrouting.json", () -> loaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utFluidRoutingFixToggle);
                put("mixins.mods.bloodmagic.ritual.json", () -> loaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utBMRitualToggle);
                put("mixins.mods.bloodmagic.json", () -> loaded("bloodmagic"));
                put("mixins.mods.botania.dupes.json", () -> loaded("botania") && UTConfigMods.BOTANIA.utDuplicationFixesToggle);
                put("mixins.mods.botania.json", () -> loaded("botania"));
                put("mixins.mods.bwm.json", () -> loaded("betterwithmods") && UTConfigMods.BWM.utBeaconNBTLoadingFix);
                put("mixins.mods.cbmultipart.json", () -> loaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
                put("mixins.mods.ceramics.json", () -> loaded("ceramics"));
                put("mixins.mods.chisel.tcomplement.dupes.json", () -> loaded("chisel") && loaded("tcomplement") && UTConfigMods.CHISEL.utDuplicationFixesToggle);
                put("mixins.mods.codechickenlib.json", () -> loaded("codechickenlib") && UTConfigMods.CCL.utPacketLeakFixToggle);
                put("mixins.mods.cofhcore.json", () -> loaded("cofhcore"));
                put("mixins.mods.cofhworld.json", () -> loaded("cofhworld") && UTConfigMods.COFH_WORLD.utCoFHSuperflatToggle);
                put("mixins.mods.collective.json", () -> loaded("collective"));
                put("mixins.mods.compactmachines.spawns.json", () -> loaded("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utAllowedSpawnsImprovementToggle);
                put("mixins.mods.cookingforblockheads.json", () -> loaded("cookingforblockheads") && UTConfigMods.COOKING_FOR_BLOCKHEADS.utOvenFixToggle);
                put("mixins.mods.cqrepoured.json", () -> loaded("cqrepoured"));
                put("mixins.mods.dankstorage.json", () -> loaded("dankstorage"));
                put("mixins.mods.divinerpg.aquamarine.json", () -> loaded("divinerpg") && UTConfigMods.DIVINE_RPG.utFixAquamarineStackSize);
                put("mixins.mods.divinerpg.armorset.json", () -> loaded("divinerpg") && UTConfigMods.DIVINE_RPG.utFixArmorSetCleanup);
                put("mixins.mods.divinerpg.hand.json", () -> loaded("divinerpg") && UTConfigMods.DIVINE_RPG.utFixHandConsumption);
                put("mixins.mods.divinerpg.waterspawning.json", () -> loaded("divinerpg") && UTConfigMods.DIVINE_RPG.utChangeWaterMobCreatureType);
                put("mixins.mods.effortlessbuilding.json", () -> loaded("effortlessbuilding") && UTConfigMods.EFFORTLESS_BUILDING.utEFTransmutationFixToggle);
                put("mixins.mods.elementarystaffs.json", () -> loaded("element"));
                put("mixins.mods.elenaidodge2.json", () -> loaded("elenaidodge2"));
                put("mixins.mods.enderio.chorus.json", () -> loaded("enderio") && UTConfigMods.ENDER_IO.utChorusStackOverflow);
                put("mixins.mods.enderio.cyclebutton.json", () -> loaded("enderio") && UTConfigMods.ENDER_IO.utSaveFilterCycleButtonProperly);
                put("mixins.mods.enderio.soulbinderjei.json", () -> loaded("enderio") && UTConfigMods.ENDER_IO.utFixSoulBinderJEI);
                put("mixins.mods.enderstorage.json", () -> loaded("enderstorage") && UTConfigMods.ENDER_STORAGE.utFrequencyTrackFixToggle);
                put("mixins.mods.epicsiegemod.json", () -> loaded("epicsiegemod"));
                put("mixins.mods.erebus.cabbage.json", () -> loaded("erebus") && UTConfigMods.EREBUS.utCabbageDrop);
                put("mixins.mods.erebus.json", () -> loaded("erebus"));
                put("mixins.mods.gaiadimension.restructurer.json", () -> loaded("gaiadimension") && UTConfigMods.GAIA_DIMENSION.utFixNPERestructurerRecipe);
                put("mixins.mods.erebus.quakehammer.json", () -> loaded("erebus") && UTConfigMods.EREBUS.utFixQuakeHammerTexture);
                put("mixins.mods.evilcraft.vengeancespirit.regex.json", () -> loaded("evilcraft") && UTConfigMods.EVIL_CRAFT.utVengeanceSpiritCache);
                put("mixins.mods.evilcraft.vengeancespirit.random.json", () -> loaded("evilcraft") && UTConfigMods.EVIL_CRAFT.utVengeanceSpiritRandom);
                put("mixins.mods.extrautilities.breakcreativemill.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utFixCreativeMillHarvestability);
                put("mixins.mods.extrautilities.deepdarkstats.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats);
                put("mixins.mods.extrautilities.dupes.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDuplicationFixesToggle);
                put("mixins.mods.extrautilities.mutabledrops.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utMutableBlockDrops);
                put("mixins.mods.extrautilities.potionlogging.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDowngradePotionLogging);
                put("mixins.mods.extrautilities.radar.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utCatchRadarException);
                put("mixins.mods.forestry.extratrees.json", () -> loaded("extratrees") && UTConfigMods.FORESTRY.utFOGatherWindfallToggle);
                put("mixins.mods.forestry.json", () -> loaded("forestry"));
                put("mixins.mods.immersiveengineering.toolevent.json", () -> loaded("immersiveengineering") && UTConfigMods.IMMERSIVE_ENGINEERING.utFireBreakEvent);
                put("mixins.mods.immersiveengineering.toolhand.json", () -> loaded("immersiveengineering") && UTConfigMods.IMMERSIVE_ENGINEERING.utFixIncorrectHandReplacement);
                put("mixins.mods.incontrol.json", () -> loaded("incontrol") && UTConfigMods.INCONTROL.utStatsFixToggle);
                put("mixins.mods.industrialcraft.dupes.json", () -> loaded("ic2") && UTConfigMods.INDUSTRIALCRAFT.utDuplicationFixesToggle);
                put("mixins.mods.industrialforegoing.dupes.json", () -> loaded("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utDuplicationFixesToggle);
                put("mixins.mods.industrialforegoing.rangeaddon.json", () -> loaded("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utRangeAddonNumberFix);
                put("mixins.mods.infernalmobs.json", () -> loaded("infernalmobs"));
                put("mixins.mods.ironbackpacks.dupes.json", () -> loaded("ironbackpacks") && UTConfigMods.IRON_BACKPACKS.utDuplicationFixesToggle);
                put("mixins.mods.itemfavorites.unixfix.json", () -> loaded("itemfav") && UTConfigMods.ITEM_FAVORITES.utUnixPathFix);
                put("mixins.mods.itemstages.json", () -> loaded("itemstages"));
                put("mixins.mods.jurassicreborn.json", () -> loaded("rebornmod"));
                put("mixins.mods.mekanism.dupes.json", () -> loaded("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle);
                put("mixins.mods.mekanism.fluidtank.json", () -> loaded("mekanism") && UTConfigMods.MEKANISM.utFluidTankExtraction);
                put("mixins.mods.moartinkers.json", () -> loaded("moartinkers") && UTConfigMods.MOAR_TINKERS.utBaublesCompatibility);
                put("mixins.mods.mobstages.json", () -> loaded("mobstages"));
                put("mixins.mods.mrtjpcore.json", () -> loaded("mrtjpcore") && UTConfigMods.MRTJPCORE.utMemoryLeakFixToggle);
                put("mixins.mods.netherchest.dupes.json", () -> loaded("netherchest") && UTConfigMods.NETHER_CHEST.utDuplicationFixesToggle);
                put("mixins.mods.netherrocks.json", () -> loaded("netherrocks"));
                put("mixins.mods.nuclearcraft.json", () -> loaded("nuclearcraft"));
                put("mixins.mods.openblocks.json", () -> loaded("openblocks") && UTConfigMods.OPEN_BLOCKS.utLastStandFixToggle);
                put("mixins.mods.properpumpkins.json", () -> loaded("pumpking") && UTConfigMods.PROPER_PUMPKIN.utFacingFix);
                put("mixins.mods.quark.dupes.json", () -> loaded("quark") && UTConfigMods.QUARK.utDuplicationFixesToggle);
                put("mixins.mods.randomthings.anvil.json", () -> loaded("randomthings") && UTConfigMods.RANDOM_THINGS.utAnvilCraftFix);
                put("mixins.mods.randomthings.collector.json", () -> loaded("randomthings") && UTConfigMods.RANDOM_THINGS.utItemCollectorDupe);
                put("mixins.mods.requiousfrakto.json", () -> loaded("requious") && UTConfigMods.REQUIOUS_FRAKTO.utParticleFixesToggle);
                put("mixins.mods.reskillable.json", () -> loaded("reskillable"));
                put("mixins.mods.rftoolsdimensions.json", () -> loaded("rftoolsdim"));
                put("mixins.mods.roost.contenttweaker.json", () -> loaded("roost") && loaded("contenttweaker"));
                put("mixins.mods.simpledifficulty.json", () -> loaded("simpledifficulty"));
                put("mixins.mods.spiceoflife.dupes.json", () -> loaded("spiceoflife") && UTConfigMods.SPICE_OF_LIFE.utDuplicationFixesToggle);
                put("mixins.mods.steamworld.json", () -> loaded("steamworld") && UTConfigMods.STEAMWORLD.utSkyOfOldFixToggle);
                put("mixins.mods.storagedrawers.json", () -> loaded("storagedrawers") && UTConfigMods.STORAGE_DRAWERS.utSDItemVoidingFixToggle);
                put("mixins.mods.tconstruct.json", () -> regularTConLoaded());
                put("mixins.mods.tconstruct.oredictcache.json", () -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle);
                put("mixins.mods.tconstruct.toolcustomization.json", () -> regularTConLoaded() && UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolCustomizationToggle);
                put("mixins.mods.tconstruct.toolcustomization.plustic.json", () -> regularTConLoaded() && loaded("plustic") && UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolCustomizationToggle);
                put("mixins.mods.techreborn.json", () -> loaded("techreborn"));
                put("mixins.mods.testdummy.copyarmor.json", () -> loaded("testdummy") && UTConfigMods.TEST_DUMMY.utCopyArmor);
                put("mixins.mods.thefarlanders.dupes.json", () -> loaded("farlanders") && UTConfigMods.THE_FARLANDERS.utDuplicationFixesToggle);
                put("mixins.mods.thermalexpansion.dupes.json", () -> loaded("thermalexpansion") && UTConfigMods.THERMAL_EXPANSION.utDuplicationFixesToggle);
                put("mixins.mods.thermalexpansion.json", () -> loaded("thermalexpansion"));
                put("mixins.mods.tinyprogressions.dupes.json", () -> loaded("tp") && UTConfigMods.TINY_PROGRESSIONS.utDuplicationFixesToggle);
                put("mixins.mods.woot.json", () -> loaded("woot") && UTConfigMods.WOOT.utCleanupSimulatedKillsToggle);
                put("mixins.tweaks.blocks.enchantmenttable.bookshelf.json", () -> loaded("bookshelf") && UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle);
            }
        }
    });

    private static boolean loaded(String modid)
    {
        return Loader.isModLoaded(modid);
    }

    public static boolean regularTConLoaded()
    {
        if (loaded("tconstruct"))
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
        BooleanSupplier sidedSupplier = UTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : serversideMixinConfigs.get(mixinConfig);
        BooleanSupplier commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.getAsBoolean() : commonSupplier == null || commonSupplier.getAsBoolean();
    }
}
