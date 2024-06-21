package mod.acgaming.universaltweaks.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{
    private static final Map<String, Supplier<Boolean>> clientsideMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>()
    {
        {
            put("mixins.mods.cbmultipart.client.json", () -> loaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
            put("mixins.mods.compactmachines.json", () -> loaded("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utCMRenderFixToggle);
            put("mixins.mods.crafttweaker.json", () -> loaded("crafttweaker"));
            put("mixins.mods.hwyla.json", () -> loaded("waila"));
            put("mixins.mods.modularrouters.json", () -> loaded("modularrouters") && UTConfigMods.MODULAR_ROUTERS.utParticleThreadToggle);
            put("mixins.mods.roost.json", () -> loaded("roost") && loaded("contenttweaker"));
            put("mixins.mods.storagedrawers.client.json", () -> loaded("storagedrawers"));
            put("mixins.mods.tconstruct.client.json", () -> loaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utParticleFixesToggle);
            put("mixins.mods.thaumcraft.entities.client.json", () -> loaded("thaumcraft"));
        }
    });

    private static final Map<String, Supplier<Boolean>> commonMixinConfigs = ImmutableMap.copyOf(new HashMap<String, Supplier<Boolean>>()
    {
        {
            put("mixins.mods.abyssalcraft.json", () -> loaded("abyssalcraft"));
            put("mixins.mods.actuallyadditions.dupes.json", () -> loaded("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utDuplicationFixesToggle);
            put("mixins.mods.actuallyadditions.json", () -> loaded("actuallyadditions"));
            put("mixins.mods.aoa3.json", () -> loaded("aoa3") && UTConfigMods.AOA.utImprovedPlayerTickToggle);
            put("mixins.mods.arcanearchives.dupes.json", () -> loaded("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle);
            put("mixins.mods.astralsorcery.json", () -> loaded("astralsorcery"));
            put("mixins.mods.biomesoplenty.json", () -> loaded("biomesoplenty"));
            put("mixins.mods.biomesoplenty.sealevel.json", () -> loaded("biomesoplenty") && UTConfigTweaks.WORLD.utSeaLevel != 63);
            put("mixins.mods.bloodmagic.dupes.json", () -> loaded("bloodmagic"));
            put("mixins.mods.bloodmagic.json", () -> loaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle);
            put("mixins.mods.botania.dupes.json", () -> loaded("botania"));
            put("mixins.mods.botania.json", () -> loaded("botania") && UTConfigMods.BOTANIA.utDuplicationFixesToggle);
            put("mixins.mods.cbmultipart.json", () -> loaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle);
            put("mixins.mods.ceramics.json", () -> loaded("ceramics"));
            put("mixins.mods.chisel.tcomplement.dupes.json", () -> loaded("chisel") && loaded("tcomplement") && UTConfigMods.CHISEL.utDuplicationFixesToggle);
            put("mixins.mods.codechickenlib.json", () -> loaded("codechickenlib") && UTConfigMods.CCL.utPacketLeakFixToggle);
            put("mixins.mods.cofhcore.json", () -> loaded("cofhcore"));
            put("mixins.mods.collective.json", () -> loaded("collective"));
            put("mixins.mods.cqrepoured.json", () -> loaded("cqrepoured"));
            put("mixins.mods.effortlessbuilding.json", () -> loaded("effortlessbuilding"));
            put("mixins.mods.elementarystaffs.json", () -> loaded("element"));
            put("mixins.mods.elenaidodge2.json", () -> loaded("elenaidodge2"));
            put("mixins.mods.enderstorage.json", () -> loaded("enderstorage") && UTConfigMods.ENDER_STORAGE.utFrequencyTrackFixToggle);
            put("mixins.mods.epicsiegemod.json", () -> loaded("epicsiegemod"));
            put("mixins.mods.erebus.cabbage.json", () -> loaded("erebus") && UTConfigMods.EREBUS.utCabbageDrop);
            put("mixins.mods.erebus.json", () -> loaded("erebus"));
            put("mixins.mods.erebus.quakehammer.json", () -> loaded("erebus") && UTConfigMods.EREBUS.utFixQuakeHammerTexture);
            put("mixins.mods.extrautilities.breakcreativemill.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utFixCreativeMillHarvestability);
            put("mixins.mods.extrautilities.deepdarkstats.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDeepDarkStats);
            put("mixins.mods.extrautilities.dupes.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDuplicationFixesToggle);
            put("mixins.mods.extrautilities.mutabledrops.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utMutableBlockDrops);
            put("mixins.mods.extrautilities.radar.json", () -> loaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utCatchRadarException);
            put("mixins.mods.forestry.cocoa.json", () -> loaded("forestry") && UTConfigMods.FORESTRY.utFOCocoaBeansToggle);
            put("mixins.mods.forestry.dupes.json", () -> loaded("forestry") && UTConfigMods.FORESTRY.utDuplicationFixesToggle);
            put("mixins.mods.forestry.extratrees.json", () -> loaded("extratrees"));
            put("mixins.mods.forestry.json", () -> loaded("forestry"));
            put("mixins.mods.industrialcraft.dupes.json", () -> loaded("ic2") && UTConfigMods.INDUSTRIALCRAFT.utDuplicationFixesToggle);
            put("mixins.mods.industrialforegoing.dupes.json", () -> loaded("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utDuplicationFixesToggle);
            put("mixins.mods.industrialforegoing.rangeaddon.json", () -> loaded("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utRangeAddonNumberFix);
            put("mixins.mods.infernalmobs.json", () -> loaded("infernalmobs"));
            put("mixins.mods.ironbackpacks.dupes.json", () -> loaded("ironbackpacks") && UTConfigMods.IRON_BACKPACKS.utDuplicationFixesToggle);
            put("mixins.mods.itemstages.json", () -> loaded("itemstages"));
            put("mixins.mods.mekanism.dupes.json", () -> loaded("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle);
            put("mixins.mods.mobstages.json", () -> loaded("mobstages"));
            put("mixins.mods.mrtjpcore.json", () -> loaded("mrtjpcore") && UTConfigMods.MRTJPCORE.utMemoryLeakFixToggle);
            put("mixins.mods.netherchest.dupes.json", () -> loaded("netherchest") && UTConfigMods.NETHER_CHEST.utDuplicationFixesToggle);
            put("mixins.mods.netherrocks.json", () -> loaded("netherrocks"));
            put("mixins.mods.nuclearcraft.json", () -> loaded("nuclearcraft"));
            put("mixins.mods.openblocks.json", () -> loaded("openblocks") && UTConfigMods.OPEN_BLOCKS.utLastStandFixToggle);
            put("mixins.mods.opencomputers.json", () -> loaded("opencomputers") && UTConfigMods.OPEN_COMPUTERS.utPacketLeakFixToggle);
            put("mixins.mods.quark.dupes.json", () -> loaded("quark") && UTConfigMods.QUARK.utDuplicationFixesToggle);
            put("mixins.mods.requiousfrakto.json", () -> loaded("requious") && UTConfigMods.REQUIOUS_FRAKTO.utParticleFixesToggle);
            put("mixins.mods.reskillable.json", () -> loaded("reskillable"));
            put("mixins.mods.rftoolsdimensions.json", () -> loaded("rftoolsdim"));
            put("mixins.mods.roost.contenttweaker.json", () -> loaded("contenttweaker"));
            put("mixins.mods.simpledifficulty.json", () -> loaded("simpledifficulty"));
            put("mixins.mods.steamworld.json", () -> loaded("steamworld") && UTConfigMods.STEAMWORLD.utSkyOfOldFixToggle);
            put("mixins.mods.spiceoflife.dupes.json", () -> loaded("spiceoflife") && UTConfigMods.SPICE_OF_LIFE.utDuplicationFixesToggle);
            put("mixins.mods.tconstruct.json", () -> loaded("tconstruct"));
            put("mixins.mods.tconstruct.oredictcache.json", () -> loaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle);
            put("mixins.mods.tconstruct.toolcustomization.json", () -> loaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConToolCustomizationToggle);
            put("mixins.mods.tconstruct.toolcustomization.plustic.json", () -> loaded("tconstruct") && loaded("plustic") && UTConfigMods.TINKERS_CONSTRUCT.utTConToolCustomizationToggle);
            put("mixins.mods.techreborn.json", () -> loaded("techreborn"));
            put("mixins.mods.thaumcraft.dupes.json", () -> loaded("thaumcraft") && UTConfigMods.THAUMCRAFT.utDuplicationFixesToggle);
            put("mixins.mods.thaumcraft.enderio.dupes.json", () -> loaded("thaumcraft") && loaded("enderio") && UTConfigMods.THAUMCRAFT.utDuplicationFixesToggle);
            put("mixins.mods.thaumcraft.entities.server.json", () -> loaded("thaumcraft"));
            put("mixins.mods.thaumcraft.foci.focuseffects.json", () -> loaded("thaumcraft"));
            put("mixins.mods.thaumcraft.foci.focusmediums.json", () -> loaded("thaumcraft"));
            put("mixins.mods.thaumcraft.json", () -> loaded("thaumcraft"));
            put("mixins.mods.thaumicwonders.dupes.json", () -> loaded("thaumicwonders") && UTConfigMods.THAUMIC_WONDERS.utDuplicationFixesToggle);
            put("mixins.mods.thefarlanders.dupes.json", () -> loaded("farlanders") && UTConfigMods.THE_FARLANDERS.utDuplicationFixesToggle);
            put("mixins.mods.thermalexpansion.dupes.json", () -> loaded("thermalexpansion"));
            put("mixins.mods.thermalexpansion.json", () -> loaded("thermalexpansion") && UTConfigMods.THERMAL_EXPANSION.utDuplicationFixesToggle);
            put("mixins.mods.tinyprogressions.dupes.json", () -> loaded("tp") && UTConfigMods.TINY_PROGRESSIONS.utDuplicationFixesToggle);
        }
    });

    private static boolean loaded(String modid)
    {
        return Loader.isModLoaded(modid);
    }

    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        if (UTLoadingPlugin.isClient)
        {
            configs.addAll(clientsideMixinConfigs.keySet());
        }
        configs.addAll(commonMixinConfigs.keySet());
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        Supplier<Boolean> sidedSupplier = UTLoadingPlugin.isClient ? clientsideMixinConfigs.get(mixinConfig) : null;
        Supplier<Boolean> commonSupplier = commonMixinConfigs.get(mixinConfig);
        return sidedSupplier != null ? sidedSupplier.get() : commonSupplier == null || commonSupplier.get();
    }
}
