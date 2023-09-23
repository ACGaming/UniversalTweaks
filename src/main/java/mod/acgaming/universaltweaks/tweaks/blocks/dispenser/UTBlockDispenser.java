package mod.acgaming.universaltweaks.tweaks.blocks.dispenser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Vazkii
public class UTBlockDispenser
{
    public static List<String> blockList = new ArrayList<>();

    public static void initBlockList()
    {
        blockList.clear();
        blockList = Arrays.asList(UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserBlockList);
        UTConfigTweaks.EnumLists listMode = UTConfigTweaks.BLOCKS.BLOCK_DISPENSER.utBlockDispenserBlockListMode;
        try
        {
            for (ResourceLocation resLoc : ForgeRegistries.BLOCKS.getKeys())
            {
                Block block = ForgeRegistries.BLOCKS.getValue(resLoc);
                Item item = Item.getItemFromBlock(block);
                if (!(item instanceof ItemBlock)
                    || (listMode == UTConfigTweaks.EnumLists.BLACKLIST && blockList.contains(resLoc.toString()))
                    || (listMode == UTConfigTweaks.EnumLists.WHITELIST && !blockList.contains(resLoc.toString()))) continue;
                if (!BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.containsKey(item)) BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, new UTBehaviorBlock((ItemBlock) item, block));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Block Dispenser block list initialized");
    }
}