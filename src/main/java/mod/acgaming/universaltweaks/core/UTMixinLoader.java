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
            "mixins.mods.aoa3.json",
            "mixins.mods.biomesoplenty.json",
            "mixins.mods.botania.json",
            "mixins.mods.cofhcore.json",
            "mixins.mods.crafttweaker.json",
            "mixins.mods.cqrepoured.json",
            "mixins.mods.elementarystaffs.json",
            "mixins.mods.elenaidodge2.json",
            "mixins.mods.epicsiegemod.json",
            "mixins.mods.forestry.json",
            "mixins.mods.forestry.cocoa.json",
            "mixins.mods.forestry.extratrees.json",
            "mixins.mods.infernalmobs.json",
            "mixins.mods.reskillable.json",
            "mixins.mods.roost.json",
            "mixins.mods.roost.contenttweaker.json",
            "mixins.mods.simpledifficulty.json",
            "mixins.mods.storagedrawers.client.json",
            "mixins.mods.storagedrawers.server.json",
            "mixins.mods.techreborn.json",
            "mixins.mods.thaumcraft.json",
            "mixins.mods.thaumcraft.foci.focuseffects.json",
            "mixins.mods.thaumcraft.foci.focusmediums.json",
            "mixins.mods.thaumcraft.entities.client.json",
            "mixins.mods.thaumcraft.entities.server.json",
            "mixins.mods.thermalexpansion.json",
            "mixins.mods.tconstruct.json",
            "mixins.mods.tconstruct.oredictcache.json"
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
                default:
                    return true;
            }
        }
        switch (mixinConfig)
        {
            case "mixins.mods.abyssalcraft.json":
                return Loader.isModLoaded("abyssalcraft");
            case "mixins.mods.biomesoplenty.json":
                return Loader.isModLoaded("biomesoplenty");
            case "mixins.mods.botania.json":
                return Loader.isModLoaded("botania");
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
            case "mixins.mods.forestry.json":
                return Loader.isModLoaded("forestry");
            case "mixins.mods.forestry.cocoa.json":
                return Loader.isModLoaded("forestry") && UTConfig.MOD_INTEGRATION.FORESTRY.utFOCocoaBeansToggle;
            case "mixins.mods.forestry.extratrees.json":
                return Loader.isModLoaded("extratrees");
            case "mixins.mods.infernalmobs.json":
                return Loader.isModLoaded("infernalmobs");
            case "mixins.mods.reskillable.json":
                return Loader.isModLoaded("reskillable");
            case "mixins.mods.roost.contenttweaker.json":
                return Loader.isModLoaded("contenttweaker");
            case "mixins.mods.simpledifficulty.json":
                return Loader.isModLoaded("simpledifficulty");
            case "mixins.mods.storagedrawers.server.json":
                return Loader.isModLoaded("storagedrawers");
            case "mixins.mods.techreborn.json":
                return Loader.isModLoaded("techreborn");
            case "mixins.mods.thaumcraft.json":
            case "mixins.mods.thaumcraft.foci.focuseffects.json":
            case "mixins.mods.thaumcraft.foci.focusmediums.json":
            case "mixins.mods.thaumcraft.entities.server.json":
                return Loader.isModLoaded("thaumcraft");
            case "mixins.mods.thermalexpansion.json":
                return Loader.isModLoaded("thermalexpansion");
            case "mixins.mods.tconstruct.json":
                return Loader.isModLoaded("tconstruct");
            case "mixins.mods.tconstruct.oredictcache.json":
                return Loader.isModLoaded("tconstruct") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConOreDictCacheToggle;
            default:
                return true;
        }
    }
}