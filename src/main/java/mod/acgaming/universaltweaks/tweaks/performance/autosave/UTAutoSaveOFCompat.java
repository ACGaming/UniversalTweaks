package mod.acgaming.universaltweaks.tweaks.performance.autosave;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import net.minecraft.launchwrapper.Launch;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.core.Coremods;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;

public class UTAutoSaveOFCompat
{
    public static File rootFolder = Launch.minecraftHome == null ? new File(".") : Launch.minecraftHome;

    public static void updateOFConfig()
    {
        if (!UTLoadingPlugin.isClient) return;
        if (!Coremods.OPTIFINE.isLoaded()) return;
        try
        {
            UniversalTweaks.LOGGER.info("OptiFine detected, updating config file...");

            Path ofConfigPath = Paths.get(rootFolder + File.separator + "optionsof.txt");
            List<String> lines = Files.readAllLines(ofConfigPath, StandardCharsets.UTF_8);
            lines.set(31, "ofAutoSaveTicks:" + UTConfigTweaks.PERFORMANCE.utAutoSaveInterval);
            Files.write(ofConfigPath, lines, StandardCharsets.UTF_8);

            UniversalTweaks.LOGGER.info("OptiFine auto save interval updated to " + UTConfigTweaks.PERFORMANCE.utAutoSaveInterval);
        }
        catch (Exception ignored) {}
    }
}