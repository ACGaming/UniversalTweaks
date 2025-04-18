package mod.acgaming.universaltweaks.tweaks.entities.trading;

import java.util.HashMap;
import java.util.Map;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTVillagerProfessionBlacklist
{
    public static final Map<String, String[]> VILLAGER_PROFESSION_BLACKLIST = new HashMap<>();

    public static void initVillagerProfessionBlacklist()
    {
        for (String entry : UTConfigTweaks.ENTITIES.utVillagerProfessionBiomeBlacklist)
        {
            String[] parts = entry.split("=");
            if (parts.length == 2)
            {
                String biome = parts[0];
                String[] professions = parts[1].split(",");
                VILLAGER_PROFESSION_BLACKLIST.put(biome, professions);
            }
        }
    }
}
