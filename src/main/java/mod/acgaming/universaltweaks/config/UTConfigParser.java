package mod.acgaming.universaltweaks.config;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import net.minecraft.launchwrapper.Launch;

import mod.acgaming.universaltweaks.UniversalTweaks;

/*
░░░░░▄▄▄▄▀▀▀▀▀▀▀▀▄▄▄▄▄▄░░░░░░░
░░░░░█░░░░▒▒▒▒▒▒▒▒▒▒▒▒░░▀▀▄░░░░
░░░░█░░░▒▒▒▒▒▒░░░░░░░░▒▒▒░░█░░░
░░░█░░░░░░▄██▀▄▄░░░░░▄▄▄░░░░█░░
░▄▀▒▄▄▄▒░█▀▀▀▀▄▄█░░░██▄▄█░░░░█░
█░▒█▒▄░▀▄▄▄▀░░░░░░░░█░░░▒▒▒▒▒░█
█░▒█░█▀▄▄░░░░░█▀░░░░▀▄░░▄▀▀▀▄▒█
░█░▀▄░█▄░█▀▄▄░▀░▀▀░▄▄▀░░░░█░░█░
░░█░░░▀▄▀█▄▄░█▀▀▀▄▄▄▄▀▀█▀██░█░░
░░░█░░░░██░░▀█▄▄▄█▄▄█▄████░█░░░
░░░░█░░░░▀▀▄░█░░░█░█▀██████░█░░
░░░░░▀▄░░░░░▀▀▄▄▄█▄█▄█▄█▄▀░░█░░
░░░░░░░▀▄▄░▒▒▒▒░░░░░░░░░░▒░░░█░
░░░░░░░░░░▀▀▄▄░▒▒▒▒▒▒▒▒▒▒░░░░█░
░░░░░░░░░░░░░░▀▄▄▄▄▄▄▄▄▄▄▄▄▄█░░
*/
public class UTConfigParser
{
    public static String configString;

    public static boolean init()
    {
        File configFile = new File(Launch.minecraftHome, "config" + File.separator + "UniversalTweaks.cfg");
        try
        {
            configString = FileUtils.readFileToString(configFile, "UTF-8");
            if (!configString.contains("S:\"Config Version\"=" + UniversalTweaks.VERSION))
            {
                File configFileOld = new File(Launch.minecraftHome, "config" + File.separator + "UniversalTweaks.old");
                Files.move(configFile, configFileOld);
                return true;
            }
            return false;
        }
        catch (IOException e)
        {
            return true;
        }
    }

    public static boolean isEnabled(String setting)
    {
        return configString.contains(setting);
    }
}