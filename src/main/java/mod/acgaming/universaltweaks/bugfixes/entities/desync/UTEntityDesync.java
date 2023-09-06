package mod.acgaming.universaltweaks.bugfixes.entities.desync;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTEntityDesync
{
    public static List<EntityEntry> blacklistedEntityEntries = new ArrayList<>();

    public static void initBlacklistedEntityEntries()
    {
        blacklistedEntityEntries.clear();
        try
        {
            for (String entry : UTConfig.BUGFIXES_ENTITIES.ENTITY_DESYNC.utEntityDesyncBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ENTITIES.containsKey(resLoc)) blacklistedEntityEntries.add(ForgeRegistries.ENTITIES.getValue(resLoc));
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
        return blacklistedEntityEntries.contains(EntityRegistry.getEntry(entity.getClass()));
    }
}