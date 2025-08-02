package mod.acgaming.universaltweaks.tweaks.blocks.endcrystal;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTEndCrystalPlacement
{
    public static final List<Block> BLOCK_LIST = new ArrayList<>();

    public static void initBlockList()
    {
        BLOCK_LIST.clear();
        try
        {
            for (String entry : UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementBlockList)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) BLOCK_LIST.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("End Crystal Placement block list initialized");
    }
}
