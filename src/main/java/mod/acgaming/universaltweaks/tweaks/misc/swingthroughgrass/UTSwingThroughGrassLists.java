package mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTSwingThroughGrassLists
{
    protected static List<Block> blacklistedBlocks = new ArrayList<>();
    protected static List<Block> whitelistedBlocks = new ArrayList<>();

    public static void initLists()
    {
        blacklistedBlocks.clear();
        whitelistedBlocks.clear();
        try
        {
            for (String entry : UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) blacklistedBlocks.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
            for (String entry : UTConfigTweaks.MISC.SWING_THROUGH_GRASS.utSwingThroughGrassWhitelist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) whitelistedBlocks.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Swing Through Grass lists initialized");
    }
}