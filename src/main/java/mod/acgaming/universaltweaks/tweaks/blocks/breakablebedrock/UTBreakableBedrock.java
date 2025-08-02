package mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTBreakableBedrock
{
    private static final List<Item> toolList = new ArrayList<>();

    public static void initToolList()
    {
        toolList.clear();
        try
        {
            for (String entry : UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToolList)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ITEMS.containsKey(resLoc)) toolList.add(ForgeRegistries.ITEMS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Breakable Bedrock tool list initialized");
    }

    @SubscribeEvent
    public static void utMineBedrock(PlayerInteractEvent.LeftClickBlock event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBreakableBedrock ::: Left click block event");
        Item heldTool = event.getEntityPlayer().getHeldItemMainhand().getItem();
        boolean isWhitelist = UTConfigTweaks.BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToolListMode == UTConfigTweaks.EnumLists.WHITELIST;
        if (toolList.contains(heldTool) == isWhitelist) return;
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        Block block = world.getBlockState(blockPos).getBlock();
        if (block == Blocks.BEDROCK)
        {
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
            event.setCanceled(true);
        }
    }
}