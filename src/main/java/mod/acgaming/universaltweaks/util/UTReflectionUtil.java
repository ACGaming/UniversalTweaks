package mod.acgaming.universaltweaks.util;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTReflectionUtil
{
    public static boolean isClassLoaded(String className)
    {
        return getClassLoaded(className) != null;
    }

    public static Class<?> getClassLoaded(String className)
    {
        try
        {
            return Class.forName(className);
        }
        catch (ClassNotFoundException ignored)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTReflectionUtil ::: Could not find class with name " + className);
            return null;
        }
    }
}
