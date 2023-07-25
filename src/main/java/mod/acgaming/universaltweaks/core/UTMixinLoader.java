package mod.acgaming.universaltweaks.core;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfig;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{
    @Override
    public List<String> getMixinConfigs()
    {
        return Lists.newArrayList(
            "mixins.mods.abyssalcraft.json",
            "mixins.mods.actuallyadditions.dupes.json",
            "mixins.mods.aoa3.json",
            "mixins.mods.arcanearchives.dupes.json",
            "mixins.mods.biomesoplenty.json",
            "mixins.mods.bloodmagic.dupes.json",
            "mixins.mods.bloodmagic.json",
            "mixins.mods.botania.dupes.json",
            "mixins.mods.botania.json",
            "mixins.mods.chisel.tcomplement.dupes.json",
            "mixins.mods.cofhcore.json",
            "mixins.mods.cqrepoured.json",
            "mixins.mods.crafttweaker.json",
            "mixins.mods.elementarystaffs.json",
            "mixins.mods.elenaidodge2.json",
            "mixins.mods.epicsiegemod.json",
            "mixins.mods.erebus.json",
            "mixins.mods.extrautilities.dupes.json",
            "mixins.mods.forestry.cocoa.json",
            "mixins.mods.forestry.dupes.json",
            "mixins.mods.forestry.extratrees.json",
            "mixins.mods.forestry.json",
            "mixins.mods.industrialcraft.dupes.json",
            "mixins.mods.industrialforegoing.dupes.json",
            "mixins.mods.infernalmobs.json",
            "mixins.mods.ironbackpacks.dupes.json",
            "mixins.mods.itemstages.json",
            "mixins.mods.mekanism.dupes.json",
            "mixins.mods.mobstages.json",
            "mixins.mods.netherchest.dupes.json",
            "mixins.mods.netherrocks.json",
            "mixins.mods.nuclearcraft.json",
            "mixins.mods.quark.dupes.json",
            "mixins.mods.reskillable.json",
            "mixins.mods.roost.contenttweaker.json",
            "mixins.mods.roost.json",
            "mixins.mods.simpledifficulty.json",
            "mixins.mods.spiceoflife.dupes.json",
            "mixins.mods.storagedrawers.client.json",
            "mixins.mods.storagedrawers.server.json",
            "mixins.mods.tconstruct.json",
            "mixins.mods.tconstruct.oredictcache.json",
            "mixins.mods.techreborn.json",
            "mixins.mods.thaumcraft.dupes.json",
            "mixins.mods.thaumcraft.enderio.dupes.json",
            "mixins.mods.thaumcraft.entities.client.json",
            "mixins.mods.thaumcraft.entities.server.json",
            "mixins.mods.thaumcraft.foci.focuseffects.json",
            "mixins.mods.thaumcraft.foci.focusmediums.json",
            "mixins.mods.thaumcraft.json",
            "mixins.mods.thaumicwonders.dupes.json",
            "mixins.mods.thefarlanders.dupes.json",
            "mixins.mods.thermalexpansion.dupes.json",
            "mixins.mods.thermalexpansion.json",
            "mixins.mods.tinyprogressions.dupes.json"
        );
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
                case "mixins.mods.crafttweaker.json":
                    return Loader.isModLoaded("crafttweaker");
                case "mixins.mods.roost.json":
                    return Loader.isModLoaded("roost");
                case "mixins.mods.storagedrawers.client.json":
                    return Loader.isModLoaded("storagedrawers");
                case "mixins.mods.thaumcraft.entities.client.json":
                    return Loader.isModLoaded("thaumcraft");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.mods.abyssalcraft.json":
                return Loader.isModLoaded("abyssalcraft");
            case "mixins.mods.actuallyadditions.dupes.json":
                return Loader.isModLoaded("actuallyadditions") && UTConfig.MOD_INTEGRATION.ACTUALLY_ADDITIONS.utDuplicationFixesToggle;
            case "mixins.mods.arcanearchives.dupes.json":
                return Loader.isModLoaded("arcanearchives") && UTConfig.MOD_INTEGRATION.ARCANE_ARCHIVES.utDuplicationFixesToggle;
            case "mixins.mods.biomesoplenty.json":
                return Loader.isModLoaded("biomesoplenty");
            case "mixins.mods.bloodmagic.json":
                return Loader.isModLoaded("bloodmagic");
            case "mixins.mods.bloodmagic.dupes.json":
                return Loader.isModLoaded("bloodmagic") && UTConfig.MOD_INTEGRATION.BLOOD_MAGIC.utDuplicationFixesToggle;
            case "mixins.mods.botania.json":
                return Loader.isModLoaded("botania");
            case "mixins.mods.botania.dupes.json":
                return Loader.isModLoaded("botania") && UTConfig.MOD_INTEGRATION.BOTANIA.utDuplicationFixesToggle;
            case "mixins.mods.cofhcore.json":
                return Loader.isModLoaded("cofhcore");
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
                return Loader.isModLoaded("extrautils2") && UTConfig.MOD_INTEGRATION.EXTRA_UTILITIES.utDuplicationFixesToggle;
            case "mixins.mods.forestry.json":
                return Loader.isModLoaded("forestry");
            case "mixins.mods.forestry.cocoa.json":
                return Loader.isModLoaded("forestry") && UTConfig.MOD_INTEGRATION.FORESTRY.utFOCocoaBeansToggle;
            case "mixins.mods.forestry.dupes.json":
                return Loader.isModLoaded("forestry") && UTConfig.MOD_INTEGRATION.FORESTRY.utDuplicationFixesToggle;
            case "mixins.mods.forestry.extratrees.json":
                return Loader.isModLoaded("extratrees");
            case "mixins.mods.industrialcraft.dupes.json":
                return Loader.isModLoaded("ic2") && UTConfig.MOD_INTEGRATION.INDUSTRIALCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.industrialforegoing.dupes.json":
                return Loader.isModLoaded("industrialforegoing") && UTConfig.MOD_INTEGRATION.INDUSTRIAL_FOREGOING.utDuplicationFixesToggle;
            case "mixins.mods.infernalmobs.json":
                return Loader.isModLoaded("infernalmobs");
            case "mixins.mods.ironbackpacks.dupes.json":
                return Loader.isModLoaded("ironbackpacks") && UTConfig.MOD_INTEGRATION.IRON_BACKPACKS.utDuplicationFixesToggle;
            case "mixins.mods.itemstages.json":
                return Loader.isModLoaded("itemstages");
            case "mixins.mods.mekanism.dupes.json":
                return Loader.isModLoaded("mekanism") && UTConfig.MOD_INTEGRATION.MEKANISM.utDuplicationFixesToggle;
            case "mixins.mods.mobstages.json":
                return Loader.isModLoaded("mobstages");
            case "mixins.mods.netherchest.dupes.json":
                return Loader.isModLoaded("netherchest") && UTConfig.MOD_INTEGRATION.NETHER_CHEST.utDuplicationFixesToggle;
            case "mixins.mods.netherrocks.json":
                return Loader.isModLoaded("netherrocks");
            case "mixins.mods.nuclearcraft.json":
                return Loader.isModLoaded("nuclearcraft");
            case "mixins.mods.quark.dupes.json":
                return Loader.isModLoaded("quark") && UTConfig.MOD_INTEGRATION.QUARK.utDuplicationFixesToggle;
            case "mixins.mods.reskillable.json":
                return Loader.isModLoaded("reskillable");
            case "mixins.mods.roost.contenttweaker.json":
                return Loader.isModLoaded("contenttweaker");
            case "mixins.mods.simpledifficulty.json":
                return Loader.isModLoaded("simpledifficulty");
            case "mixins.mods.spiceoflife.dupes.json":
                return Loader.isModLoaded("spiceoflife") && UTConfig.MOD_INTEGRATION.SPICE_OF_LIFE.utDuplicationFixesToggle;
            case "mixins.mods.storagedrawers.server.json":
                return Loader.isModLoaded("storagedrawers");
            case "mixins.mods.chisel.tcomplement.dupes.json":
                return Loader.isModLoaded("chisel") && Loader.isModLoaded("tcomplement") && UTConfig.MOD_INTEGRATION.CHISEL.utDuplicationFixesToggle;
            case "mixins.mods.techreborn.json":
                return Loader.isModLoaded("techreborn");
            case "mixins.mods.thaumcraft.json":
            case "mixins.mods.thaumcraft.foci.focuseffects.json":
            case "mixins.mods.thaumcraft.foci.focusmediums.json":
            case "mixins.mods.thaumcraft.entities.server.json":
                return Loader.isModLoaded("thaumcraft");
            case "mixins.mods.thaumcraft.dupes.json":
                return Loader.isModLoaded("thaumcraft") && UTConfig.MOD_INTEGRATION.THAUMCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.thaumcraft.enderio.dupes.json":
                return Loader.isModLoaded("thaumcraft") && Loader.isModLoaded("enderio") && UTConfig.MOD_INTEGRATION.THAUMCRAFT.utDuplicationFixesToggle;
            case "mixins.mods.thaumicwonders.dupes.json":
                return Loader.isModLoaded("thaumicwonders") && UTConfig.MOD_INTEGRATION.THAUMIC_WONDERS.utDuplicationFixesToggle;
            case "mixins.mods.thefarlanders.dupes.json":
                return Loader.isModLoaded("farlanders") && UTConfig.MOD_INTEGRATION.THE_FARLANDERS.utDuplicationFixesToggle;
            case "mixins.mods.thermalexpansion.json":
                return Loader.isModLoaded("thermalexpansion");
            case "mixins.mods.thermalexpansion.dupes.json":
                return Loader.isModLoaded("thermalexpansion") && UTConfig.MOD_INTEGRATION.THERMAL_EXPANSION.utDuplicationFixesToggle;
            case "mixins.mods.tconstruct.json":
                return Loader.isModLoaded("tconstruct");
            case "mixins.mods.tconstruct.oredictcache.json":
                return Loader.isModLoaded("tconstruct") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConOreDictCacheToggle;
            case "mixins.mods.tinyprogressions.dupes.json":
                return Loader.isModLoaded("tp") && UTConfig.MOD_INTEGRATION.TINY_PROGRESSIONS.utDuplicationFixesToggle;
        }
        return true;
    }
}