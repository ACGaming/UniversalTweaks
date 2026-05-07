package mod.acgaming.universaltweaks.tweaks.entities.villagerharvest;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A helper class to assist with configuration parsing for fast villager harvest checks.
 */
public class UTVillagerHarvestUtils
{
    private static final Map<Item, Integer> FOOD_WHITELIST = new HashMap<>();
    private static final Set<Block> HARVEST_BLACKLIST = new HashSet<>();

    /**
     * Returns true if the passed ItemStack item extends either {@link ItemSeeds} or {@link ItemSeedFood}.
     */
    public static boolean isSeedItem(ItemStack stack) {
        return !stack.isEmpty() && isSeedItem(stack.getItem());
    }

    /**
     * Returns true if the passed Item extends either {@link ItemSeeds} or {@link ItemSeedFood}.
     */
    public static boolean isSeedItem(Item item) {
        return item instanceof ItemSeeds || item instanceof ItemSeedFood;
    }

    /**
     * Returns true if the passed ItemStack is a valid food item used for villager breeding.
     */
    public static boolean isWhiteListedFood(ItemStack stack) {
        return !stack.isEmpty() && isWhiteListedFood(stack.getItem());
    }

    /**
     * Returns true if the passed item is a valid food item used for villager breeding.
     */
    public static boolean isWhiteListedFood(Item item) {
        return getRequiredFoodCount(item) > 0;
    }

    /**
     * Returns the item count required for this food ItemStack. If this ItemStack is not valid food, this
     * method will return 0.
     */
    public static int getRequiredFoodCount(ItemStack stack) {
        return !stack.isEmpty() ? getRequiredFoodCount(stack.getItem()) : 0;
    }

    /**
     * Returns the item count required for this food item. If this item is not valid food, this
     * method will return 0.
     */
    public static int getRequiredFoodCount(Item item) {
        return FOOD_WHITELIST.containsKey(item) ? FOOD_WHITELIST.get(item) : (item instanceof ItemSeedFood ? 12 : 0);
    }

    /**
     * Returns true if the block is not eligible for Villager harvesting.
     */
    public static boolean isBlacklistedCrop(Block block) {
        return HARVEST_BLACKLIST.contains(block);
    }

    public static void initLists() {
        FOOD_WHITELIST.clear();
        HARVEST_BLACKLIST.clear();

        Pattern pattern = Pattern.compile("^([^:]+:[^:=]+)=(\\d+)$");
        for(String str : UTConfigTweaks.ENTITIES.VILLAGER_HARVEST.utFoodWhitelist) {
            try {
                Matcher matcher = pattern.matcher(str);
                if(matcher.find()) {
                    ResourceLocation loc = new ResourceLocation(matcher.group(1));
                    Item item = ForgeRegistries.ITEMS.getValue(loc);
                    int count = Integer.parseInt(matcher.group(2));
                    if(item == null || item == Items.AIR) {
                        UniversalTweaks.LOGGER.error("Error parsing [02] Crop Food Whitelist. No valid item found for {}", str);
                    } else if(count <= 0) {
                        UniversalTweaks.LOGGER.error("Error parsing [02] Crop Food Whitelist. Count must be greater than 0 {}", str);
                    } else {
                        FOOD_WHITELIST.put(item, count);
                    }
                } else {
                    UniversalTweaks.LOGGER.error("Error parsing [02] Crop Food Whitelist. Invalid configuration string {}", str);
                }
            } catch (Exception e) {
                UniversalTweaks.LOGGER.error("Error parsing [02] Crop Food Whitelist. Error parsing {}", str);
            }
        }

        for(String str : UTConfigTweaks.ENTITIES.VILLAGER_HARVEST.utHarvestBlacklist) {
            ResourceLocation loc = new ResourceLocation(str);
            Block block = ForgeRegistries.BLOCKS.getValue(loc);
            if (block == null || block == Blocks.AIR) {
                UniversalTweaks.LOGGER.error("Error parsing [03] Harvest Blacklist. No valid block found for {}", str);
            } else {
                HARVEST_BLACKLIST.add(block);
            }
        }
    }
}
