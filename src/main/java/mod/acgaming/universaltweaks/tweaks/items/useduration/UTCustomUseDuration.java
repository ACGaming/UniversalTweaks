package mod.acgaming.universaltweaks.tweaks.items.useduration;

import java.util.Map;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTCustomUseDuration
{
    public static Map<String, Integer> itemUseDurationMap = new Object2IntOpenHashMap<>();
    public static Map<String, Integer> itemUseCooldownMap = new Object2IntOpenHashMap<>();

    public static void initItemUseMaps()
    {
        itemUseDurationMap.clear();
        itemUseCooldownMap.clear();
        try
        {
            for (String config : UTConfig.TWEAKS_ITEMS.utCustomUseDurations)
            {
                String[] configParts = config.split(";");
                String[] itemParts = configParts[0].split(":");
                ResourceLocation resLoc = new ResourceLocation(itemParts[0], itemParts[1]);
                int cooldown = 0;
                if (configParts.length > 2) cooldown = Integer.parseInt(configParts[2]);
                int meta = 0;
                if (itemParts.length > 2) meta = Integer.parseInt(itemParts[2]);
                int duration = Integer.parseInt(configParts[1]);
                if (ForgeRegistries.ITEMS.containsKey(resLoc))
                {
                    itemUseDurationMap.put(ForgeRegistries.ITEMS.getValue(resLoc).toString() + meta, duration);
                    itemUseCooldownMap.put(ForgeRegistries.ITEMS.getValue(resLoc).toString() + meta, cooldown);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Item use duration map initialized");
    }
}