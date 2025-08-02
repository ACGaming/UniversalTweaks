package mod.acgaming.universaltweaks.tweaks.entities.trading;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTVillagerProfessionRestriction
{
    public static final Map<String, Entry> VILLAGER_PROFESSION_MAP = new HashMap<>();

    public static void initBiomeRestrictions()
    {
        VILLAGER_PROFESSION_MAP.clear();
        for (String entry : UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeRestriction)
        {
            try
            {
                String[] parts = entry.split(";");
                if (parts.length != 2)
                {
                    UniversalTweaks.LOGGER.warn("Invalid villager profession blacklist entry: {}", entry);
                    continue;
                }

                String mode = parts[0].trim().toLowerCase();
                if (!mode.equals("whitelist") && !mode.equals("blacklist"))
                {
                    UniversalTweaks.LOGGER.warn("Invalid mode in villager profession blacklist entry (must be 'whitelist' or 'blacklist'): {}", entry);
                    continue;
                }

                String[] biomeProfParts = parts[1].split("=");
                if (biomeProfParts.length != 2)
                {
                    UniversalTweaks.LOGGER.warn("Invalid format in villager profession blacklist entry: {}", entry);
                    continue;
                }

                String biome = biomeProfParts[0].trim();
                String[] professions = biomeProfParts[1].split(",");

                // Validate biome
                if (ForgeRegistries.BIOMES.getValue(new ResourceLocation(biome)) == null)
                {
                    UniversalTweaks.LOGGER.warn("Invalid biome in villager profession blacklist entry: {}", biome);
                    continue;
                }

                // Validate professions
                for (String prof : professions)
                {
                    prof = prof.trim();
                    if (ForgeRegistries.VILLAGER_PROFESSIONS.getValue(new ResourceLocation(prof)) == null)
                    {
                        UniversalTweaks.LOGGER.warn("Invalid profession in villager profession blacklist entry: {}", prof);
                    }
                }

                VILLAGER_PROFESSION_MAP.put(biome, new Entry(mode, professions));
            }
            catch (Exception e)
            {
                UniversalTweaks.LOGGER.error("Error parsing villager profession blacklist entry: {}", entry, e);
            }
        }
    }

    public static class Entry
    {
        public final String mode;
        public final String[] professions;

        public Entry(String mode, String[] professions)
        {
            this.mode = mode;
            this.professions = professions;
        }
    }
}
