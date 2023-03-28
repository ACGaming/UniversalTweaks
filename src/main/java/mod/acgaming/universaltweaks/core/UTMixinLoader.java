package mod.acgaming.universaltweaks.core;

import java.util.List;

import com.google.common.collect.Lists;
import net.minecraftforge.fml.common.Loader;

import mod.acgaming.universaltweaks.config.UTConfigParser;
import zone.rong.mixinbooter.ILateMixinLoader;

public class UTMixinLoader implements ILateMixinLoader
{
    @Override
    public List<String> getMixinConfigs()
    {
        return Lists.newArrayList(
            "mixins.mods.biomesoplenty.json",
            "mixins.mods.crafttweaker.json",
            "mixins.mods.customspawner.json",
            "mixins.mods.epicsiegemod.json",
            "mixins.mods.forestry.json",
            "mixins.mods.forestry.extratrees.json",
            "mixins.mods.roost.json",
            "mixins.mods.roost.contenttweaker.json",
            "mixins.mods.storagedrawers.client.json",
            "mixins.mods.storagedrawers.server.json",
            "mixins.mods.thaumcraft.json",
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
                case "mixins.mods.crafttweaker.json":
                    return Loader.isModLoaded("crafttweaker");
                case "mixins.mods.roost.json":
                    return Loader.isModLoaded("roost");
                case "mixins.mods.storagedrawers.client.json":
                case "mixins.mods.storagedrawers.server.json":
                    return Loader.isModLoaded("storagedrawers");
            }
        }
        switch (mixinConfig)
        {
            case "mixins.mods.biomesoplenty.json":
                return Loader.isModLoaded("biomesoplenty");
            case "mixins.mods.customspawner.json":
                return Loader.isModLoaded("customspawner");
            case "mixins.mods.epicsiegemod.json":
                return Loader.isModLoaded("epicsiegemod");
            case "mixins.mods.forestry.json":
                return Loader.isModLoaded("forestry");
            case "mixins.mods.forestry.extratrees.json":
                return Loader.isModLoaded("extratrees");
            case "mixins.mods.roost.contenttweaker.json":
                return Loader.isModLoaded("contenttweaker");
            case "mixins.mods.storagedrawers.server.json":
                return Loader.isModLoaded("storagedrawers");
            case "mixins.mods.thaumcraft.json":
                return Loader.isModLoaded("thaumcraft");
            case "mixins.mods.thermalexpansion.json":
                return Loader.isModLoaded("thermalexpansion");
            case "mixins.mods.tconstruct.json":
                return Loader.isModLoaded("tconstruct");
            case "mixins.mods.tconstruct.oredictcache.json":
                return Loader.isModLoaded("tconstruct") && (UTLoadingPlugin.firstLaunch || UTConfigParser.isEnabled("B:\"Ore Dictionary Cache\"=true"));
        }
        return true;
    }
}