package mod.acgaming.universaltweaks.mods.customspawner.mixin;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import net.minecraftforge.fml.common.Loader;

import drzhark.customspawner.configuration.CMSConfiguration;
import drzhark.customspawner.entity.EntityModData;
import drzhark.customspawner.environment.EnvironmentSettings;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Clienthax
@Mixin(value = EnvironmentSettings.class, remap = false)
public class UTEnvironmentSettingsMixin
{
    @Shadow(remap = false)
    @Final
    private static String CREATURES_FILE_PATH;

    @Shadow(remap = false)
    public Map<String, EntityModData> defaultModMap;

    @Shadow(remap = false)
    public CMSConfiguration CMSEnvironmentConfig;

    @Inject(method = "genModConfiguration", at = @At("HEAD"))
    public void utGenModConfiguration(CallbackInfo ci)
    {
        for (String mod : UTConfig.MOD_INTEGRATION.utMoCBiomeMods)
        {
            String[] modEntries = mod.split(";");
            addDefaultCreaturesIfPresent(modEntries[0], modEntries[1], modEntries[2], modEntries[3], modEntries[4]);
        }
    }

    /**
     * @param modid    Mod ID required for this to be added
     * @param name     Mod name
     * @param key      Used for class lookups, needs to be a unique part of the modded package, e.g. mod.acgaming.*universaltweaks*.mods
     * @param tag      Short tag for logs
     * @param filename Filename to use for the generated config
     */
    public void addDefaultCreaturesIfPresent(String modid, String name, String key, String tag, String filename)
    {
        if (Loader.isModLoaded(modid))
        {
            LogManager.getLogger().info("Loaded default creatures from Universal Tweaks " + CREATURES_FILE_PATH + "" + filename);
            this.defaultModMap.put(name, new EntityModData(key, tag, new CMSConfiguration(new File(this.CMSEnvironmentConfig.file.getParent(), CREATURES_FILE_PATH + filename))));
        }
    }
}