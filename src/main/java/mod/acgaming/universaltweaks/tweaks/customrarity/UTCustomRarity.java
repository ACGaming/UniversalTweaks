package mod.acgaming.universaltweaks.tweaks.customrarity;

import java.util.Map;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTCustomRarity
{
    public static Map<Item, Integer> itemMetaMap = new Object2IntOpenHashMap<>();
    public static Map<Item, EnumRarity> itemRarityMap = new Object2ObjectOpenHashMap<>();

    public static void initCustomRarityLists()
    {
        itemMetaMap.clear();
        itemRarityMap.clear();
        try
        {
            for (String config : UTConfig.TWEAKS_ITEMS.utCustomRarities)
            {
                String[] configParts = config.split(";");
                String[] itemParts = configParts[0].split(":");
                ResourceLocation resLoc = new ResourceLocation(itemParts[0], itemParts[1]);
                int meta = 0;
                if (itemParts.length > 2) meta = Integer.parseInt(itemParts[2]);
                EnumRarity rarity;
                switch (configParts[1])
                {
                    case "uncommon":
                        rarity = EnumRarity.UNCOMMON;
                        break;
                    case "rare":
                        rarity = EnumRarity.RARE;
                        break;
                    case "epic":
                        rarity = EnumRarity.EPIC;
                        break;
                    default:
                        rarity = EnumRarity.COMMON;
                }
                if (ForgeRegistries.ITEMS.containsKey(resLoc))
                {
                    itemMetaMap.put(ForgeRegistries.ITEMS.getValue(resLoc), meta);
                    itemRarityMap.put(ForgeRegistries.ITEMS.getValue(resLoc), rarity);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Item rarity map initialized");
    }
}