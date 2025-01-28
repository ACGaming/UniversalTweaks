package mod.acgaming.universaltweaks.tweaks.blocks.piston;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTPistonBlockBlacklist
{
    public static final List<Block> BLOCK_BLACKLIST = new ArrayList<>();

    public static void initBlockBlacklist()
    {
        BLOCK_BLACKLIST.clear();
        try
        {
            for (String string : UTConfigTweaks.BLOCKS.PISTON.utPistonBlockBlacklist)
            {
                ResourceLocation resLoc = new ResourceLocation(string);
                if (ForgeRegistries.BLOCKS.containsKey(resLoc)) BLOCK_BLACKLIST.add(ForgeRegistries.BLOCKS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Piston block blacklist initialized");
    }
}
