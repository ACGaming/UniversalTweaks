package mod.acgaming.universaltweaks.tweaks.customrarity;

import java.util.Map;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTCustomRarity
{
    public static Map<Item, EnumRarity> itemRarityMap = new Object2ObjectOpenHashMap<>();

    public static void initRarityItemList()
    {
        itemRarityMap.clear();
        try
        {
            for (String entry : UTConfig.TWEAKS_ITEMS.utCustomRarities)
            {
                String[] entryParts = entry.split(";");
                ResourceLocation resLoc = new ResourceLocation(entryParts[0]);
                EnumRarity rarity;
                switch (entryParts[1])
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
                if (ForgeRegistries.ITEMS.containsKey(resLoc)) itemRarityMap.put(ForgeRegistries.ITEMS.getValue(resLoc), rarity);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Item rarity map initialized");
    }
}