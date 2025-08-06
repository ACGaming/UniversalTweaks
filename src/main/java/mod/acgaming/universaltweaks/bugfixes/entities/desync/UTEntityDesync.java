package mod.acgaming.universaltweaks.bugfixes.entities.desync;

import java.util.Set;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

public class UTEntityDesync
{
    public static final Set<Class<? extends Entity>> blacklistedEntityEntries = new ObjectOpenHashSet<>();

    public static void initBlacklistedEntityEntries()
    {
        blacklistedEntityEntries.clear();
        try
        {
            for (String entry : UTConfigBugfixes.ENTITIES.ENTITY_DESYNC.utEntityDesyncBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ENTITIES.containsKey(resLoc))
                {
                    EntityEntry entityEntry = ForgeRegistries.ENTITIES.getValue(resLoc);
                    if (entityEntry == null) continue;
                    Class<? extends Entity> entityClass = entityEntry.getEntityClass();
                    if (entityClass == null) continue;
                    blacklistedEntityEntries.add(entityClass);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Entity Desync blacklist initialized");
    }

    public static boolean isBlacklisted(Entity entity)
    {
        return blacklistedEntityEntries.contains(entity.getClass()) || !(((IPrevMotion) entity).hasSuperUpdate());
    }
}