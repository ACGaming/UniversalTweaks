package mod.acgaming.universaltweaks.core;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{
    @Override
    public List<String> getMixinConfigs()
    {
        List<String> configs = new ArrayList<>();
        // CLIENT ONLY
        if (UTLoadingPlugin.isClient)
        {
            configs.add("mixins.mods.aoa3.json");
            configs.add("mixins.mods.cbmultipart.client.json");
            configs.add("mixins.mods.compactmachines.json");
            configs.add("mixins.mods.crafttweaker.json");
            configs.add("mixins.mods.roost.json");
            configs.add("mixins.mods.storagedrawers.client.json");
            configs.add("mixins.mods.thaumcraft.entities.client.json");
            configs.add("mixins.mods.thaumicwonders.client.json");
        }
        // COMMON
        configs.add("mixins.mods.abyssalcraft.json");
        configs.add("mixins.mods.actuallyadditions.dupes.json");
        configs.add("mixins.mods.arcanearchives.dupes.json");
        configs.add("mixins.mods.biomesoplenty.json");
        configs.add("mixins.mods.bloodmagic.dupes.json");
        configs.add("mixins.mods.bloodmagic.json");
        configs.add("mixins.mods.botania.dupes.json");
        configs.add("mixins.mods.botania.json");
        configs.add("mixins.mods.cbmultipart.json");
        configs.add("mixins.mods.ceramics.json");
        configs.add("mixins.mods.chisel.tcomplement.dupes.json");
        configs.add("mixins.mods.cofhcore.json");
        configs.add("mixins.mods.collective.json");
        configs.add("mixins.mods.cqrepoured.json");
        configs.add("mixins.mods.elementarystaffs.json");
        configs.add("mixins.mods.elenaidodge2.json");
        configs.add("mixins.mods.epicsiegemod.json");
        configs.add("mixins.mods.erebus.json");
        configs.add("mixins.mods.extrautilities.dupes.json");
        configs.add("mixins.mods.forestry.cocoa.json");
        configs.add("mixins.mods.forestry.dupes.json");
        configs.add("mixins.mods.forestry.extratrees.json");
        configs.add("mixins.mods.forestry.json");
        configs.add("mixins.mods.industrialcraft.dupes.json");
        configs.add("mixins.mods.industrialforegoing.dupes.json");
        configs.add("mixins.mods.infernalmobs.json");
        configs.add("mixins.mods.ironbackpacks.dupes.json");
        configs.add("mixins.mods.itemstages.json");
        configs.add("mixins.mods.mekanism.dupes.json");
        configs.add("mixins.mods.mobstages.json");
        configs.add("mixins.mods.mrtjpcore.json");
        configs.add("mixins.mods.netherchest.dupes.json");
        configs.add("mixins.mods.netherrocks.json");
        configs.add("mixins.mods.nuclearcraft.json");
        configs.add("mixins.mods.quark.dupes.json");
        configs.add("mixins.mods.reskillable.json");
        configs.add("mixins.mods.roost.contenttweaker.json");
        configs.add("mixins.mods.simpledifficulty.json");
        configs.add("mixins.mods.spiceoflife.dupes.json");
        configs.add("mixins.mods.tconstruct.json");
        configs.add("mixins.mods.tconstruct.toolcustomization.json");
        configs.add("mixins.mods.tconstruct.toolcustomization.plustic.json");
        configs.add("mixins.mods.tconstruct.oredictcache.json");
        configs.add("mixins.mods.techreborn.json");
        configs.add("mixins.mods.thaumcraft.dupes.json");
        configs.add("mixins.mods.thaumcraft.enderio.dupes.json");
        configs.add("mixins.mods.thaumcraft.entities.server.json");
        configs.add("mixins.mods.thaumcraft.foci.focuseffects.json");
        configs.add("mixins.mods.thaumcraft.foci.focusmediums.json");
        configs.add("mixins.mods.thaumcraft.json");
        configs.add("mixins.mods.thaumicwonders.dupes.json");
        configs.add("mixins.mods.thefarlanders.dupes.json");
        configs.add("mixins.mods.thermalexpansion.dupes.json");
        configs.add("mixins.mods.thermalexpansion.json");
        configs.add("mixins.mods.tinyprogressions.dupes.json");
        return configs;
    }

    @Override
    public boolean shouldMixinConfigQueue(String mixinConfig)
    {
        if (UTLoadingPlugin.isClient)
        {
            switch (mixinConfig)
            {
                case "mixins.mods.aoa3.json":
                    return Loader.isModLoaded("aoa3") && (Loader.isModLoaded("fluxnetworks") || Loader.isModLoaded("nuclearcraft"));
                case "mixins.mods.cbmultipart.client.json":
                    return Loader.isModLoaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle;
                case "mixins.mods.compactmachines.json":
                    return Loader.isModLoaded("compactmachines3") && UTConfigMods.COMPACT_MACHINES.utCMRenderFixToggle;
                case "mixins.mods.crafttweaker.json":
                    return Loader.isModLoaded("crafttweaker");
                case "mixins.mods.roost.json":
                    return Loader.isModLoaded("roost") && Loader.isModLoaded("contenttweaker");
                case "mixins.mods.storagedrawers.client.json":
                    return Loader.isModLoaded("storagedrawers");
                case "mixins.mods.thaumcraft.entities.client.json":
                    return Loader.isModLoaded("thaumcraft");
                case "mixins.mods.thaumicwonders.client.json":
                    return Loader.isModLoaded("thaumicwonders") && UTConfigMods.THAUMIC_WONDERS.utMemoryLeakFixToggle;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.mods.abyssalcraft.json":
                return Loader.isModLoaded("abyssalcraft");
            case "mixins.mods.actuallyadditions.dupes.json":
                return Loader.isModLoaded("actuallyadditions") && UTConfigMods.ACTUALLY_ADDITIONS.utDuplicationFixesToggle;
            case "mixins.mods.arcanearchives.dupes.json":
                return Loader.isModLoaded("arcanearchives") && UTConfigMods.ARCANE_ARCHIVES.utDuplicationFixesToggle;
            case "mixins.mods.biomesoplenty.json":
                return Loader.isModLoaded("biomesoplenty");
            case "mixins.mods.bloodmagic.json":
                return Loader.isModLoaded("bloodmagic");
            case "mixins.mods.bloodmagic.dupes.json":
                return Loader.isModLoaded("bloodmagic") && UTConfigMods.BLOOD_MAGIC.utDuplicationFixesToggle;
            case "mixins.mods.botania.json":
                return Loader.isModLoaded("botania");
            case "mixins.mods.botania.dupes.json":
                return Loader.isModLoaded("botania") && UTConfigMods.BOTANIA.utDuplicationFixesToggle;
            case "mixins.mods.cbmultipart.json":
                return Loader.isModLoaded("forgemultipartcbe") && UTConfigMods.CB_MULTIPART.utMemoryLeakFixToggle;
            case "mixins.mods.ceramics.json":
                return Loader.isModLoaded("ceramics");
            case "mixins.mods.cofhcore.json":
                return Loader.isModLoaded("cofhcore");
            case "mixins.mods.collective.json":
                return Loader.isModLoaded("collective");
            case "mixins.mods.cqrepoured.json":
                return Loader.isModLoaded("cqrepoured");
            case "mixins.mods.elementarystaffs.json":
                return Loader.isModLoaded("element");
            case "mixins.mods.elenaidodge2.json":
                return Loader.isModLoaded("elenaidodge2");
            case "mixins.mods.epicsiegemod.json":
                return Loader.isModLoaded("epicsiegemod");
            case "mixins.mods.erebus.json":
                return Loader.isModLoaded("erebus");
            case "mixins.mods.extrautilities.dupes.json":
                return Loader.isModLoaded("extrautils2") && UTConfigMods.EXTRA_UTILITIES.utDuplicationFixesToggle;
            case "mixins.mods.forestry.json":
                return Loader.isModLoaded("forestry");
            case "mixins.mods.forestry.cocoa.json":
                return Loader.isModLoaded("forestry") && UTConfigMods.FORESTRY.utFOCocoaBeansToggle;
            case "mixins.mods.forestry.dupes.json":
                return Loader.isModLoaded("forestry") && UTConfigMods.FORESTRY.utDuplicationFixesToggle;
            case "mixins.mods.forestry.extratrees.json":
                return Loader.isModLoaded("extratrees");
            case "mixins.mods.industrialcraft.dupes.json":
                return Loader.isModLoaded("ic2") && UTConfigMods.INDUSTRIALCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.industrialforegoing.dupes.json":
                return Loader.isModLoaded("industrialforegoing") && UTConfigMods.INDUSTRIAL_FOREGOING.utDuplicationFixesToggle;
            case "mixins.mods.infernalmobs.json":
                return Loader.isModLoaded("infernalmobs");
            case "mixins.mods.ironbackpacks.dupes.json":
                return Loader.isModLoaded("ironbackpacks") && UTConfigMods.IRON_BACKPACKS.utDuplicationFixesToggle;
            case "mixins.mods.itemstages.json":
                return Loader.isModLoaded("itemstages");
            case "mixins.mods.mekanism.dupes.json":
                return Loader.isModLoaded("mekanism") && UTConfigMods.MEKANISM.utDuplicationFixesToggle;
            case "mixins.mods.mobstages.json":
                return Loader.isModLoaded("mobstages");
            case "mixins.mods.mrtjpcore.json":
                return Loader.isModLoaded("mrtjpcore") && UTConfigMods.MRTJPCORE.utMemoryLeakFixToggle;
            case "mixins.mods.netherchest.dupes.json":
                return Loader.isModLoaded("netherchest") && UTConfigMods.NETHER_CHEST.utDuplicationFixesToggle;
            case "mixins.mods.netherrocks.json":
                return Loader.isModLoaded("netherrocks");
            case "mixins.mods.nuclearcraft.json":
                return Loader.isModLoaded("nuclearcraft");
            case "mixins.mods.quark.dupes.json":
                return Loader.isModLoaded("quark") && UTConfigMods.QUARK.utDuplicationFixesToggle;
            case "mixins.mods.reskillable.json":
                return Loader.isModLoaded("reskillable");
            case "mixins.mods.roost.contenttweaker.json":
                return Loader.isModLoaded("contenttweaker");
            case "mixins.mods.simpledifficulty.json":
                return Loader.isModLoaded("simpledifficulty");
            case "mixins.mods.spiceoflife.dupes.json":
                return Loader.isModLoaded("spiceoflife") && UTConfigMods.SPICE_OF_LIFE.utDuplicationFixesToggle;
            case "mixins.mods.chisel.tcomplement.dupes.json":
                return Loader.isModLoaded("chisel") && Loader.isModLoaded("tcomplement") && UTConfigMods.CHISEL.utDuplicationFixesToggle;
            case "mixins.mods.techreborn.json":
                return Loader.isModLoaded("techreborn");
            case "mixins.mods.thaumcraft.json":
            case "mixins.mods.thaumcraft.foci.focuseffects.json":
            case "mixins.mods.thaumcraft.foci.focusmediums.json":
            case "mixins.mods.thaumcraft.entities.server.json":
                return Loader.isModLoaded("thaumcraft");
            case "mixins.mods.thaumcraft.dupes.json":
                return Loader.isModLoaded("thaumcraft") && UTConfigMods.THAUMCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.thaumcraft.enderio.dupes.json":
                return Loader.isModLoaded("thaumcraft") && Loader.isModLoaded("enderio") && UTConfigMods.THAUMCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.thaumicwonders.dupes.json":
                return Loader.isModLoaded("thaumicwonders") && UTConfigMods.THAUMIC_WONDERS.utDuplicationFixesToggle;
            case "mixins.mods.thefarlanders.dupes.json":
                return Loader.isModLoaded("farlanders") && UTConfigMods.THE_FARLANDERS.utDuplicationFixesToggle;
            case "mixins.mods.thermalexpansion.json":
                return Loader.isModLoaded("thermalexpansion");
            case "mixins.mods.thermalexpansion.dupes.json":
                return Loader.isModLoaded("thermalexpansion") && UTConfigMods.THERMAL_EXPANSION.utDuplicationFixesToggle;
            case "mixins.mods.tconstruct.json":
                return Loader.isModLoaded("tconstruct");
            case "mixins.mods.tconstruct.toolcustomization.json":
                return Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConToolCustomizationToggle;
            case "mixins.mods.tconstruct.toolcustomization.plustic.json":
                return Loader.isModLoaded("tconstruct") && Loader.isModLoaded("plustic") && UTConfigMods.TINKERS_CONSTRUCT.utTConToolCustomizationToggle;
            case "mixins.mods.tconstruct.oredictcache.json":
                return Loader.isModLoaded("tconstruct") && UTConfigMods.TINKERS_CONSTRUCT.utTConOreDictCacheToggle;
            case "mixins.mods.tinyprogressions.dupes.json":
                return Loader.isModLoaded("tp") && UTConfigMods.TINY_PROGRESSIONS.utDuplicationFixesToggle;
        }
        return true;
    }
}
