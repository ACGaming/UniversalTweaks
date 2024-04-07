package mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;

import com.google.common.collect.ImmutableList;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTReflectionUtil;

public class UTEntityRadiusCheck
{
    public static List<Class<? extends Entity>> searchTargets = ImmutableList.of();

    public static void onLoadComplete()
    {
        searchTargets = new ArrayList<>();
        // Read config for classes
        for (String className : UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeTargets)
        {
            try
            {
                Class<?> clazz = UTReflectionUtil.getClassLoaded(className);
                if (clazz == null)
                {
                    UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Invalid class name " + className + "! Skipping this entry.");
                    continue;
                }
                Class<? extends Entity> target = clazz.asSubclass(Entity.class);
                searchTargets.add(target);
            }
            catch (ClassCastException ex)
            {
                UniversalTweaks.LOGGER.warn("UTEntityRadiusCheck ::: Class " + className + " is not an Entity! Skipping this entry.");
            }
        }
    }
}
