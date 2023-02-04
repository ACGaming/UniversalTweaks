package mod.acgaming.universaltweaks.bugfixes.blockoverlay;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

public class UTBlockOverlayLists
{
    public static List<Block> blacklistedBlocks = new ArrayList<>();
    public static List<Block> whitelistedBlocks = new ArrayList<>();

    public static void initLists()
    {
        blacklistedBlocks.clear();
        whitelistedBlocks.clear();
        try
        {
            for (String entry : UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) blacklistedBlocks.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
            for (String entry : UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayWhitelist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) whitelistedBlocks.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Block overlay lists initialized");
    }
}