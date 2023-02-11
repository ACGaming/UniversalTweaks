package mod.acgaming.universaltweaks.tweaks.breakablebedrock;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTBreakableBedrock
{
    public static List<Item> whitelistedTools = new ArrayList<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utReplaceBedrock(RegistryEvent.Register<Block> event)
    {
        if (!UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBreakableBedrock ::: Register block event");
        Block block = new UTBlockBedrock().setRegistryName("minecraft", "bedrock");
        event.getRegistry().register(block);
        ForgeRegistries.ITEMS.register(new ItemBlock(block)
        {
            @Override
            public String getCreatorModId(ItemStack itemStack)
            {
                return UniversalTweaks.MODID;
            }
        }.setRegistryName(block.getRegistryName()));
    }

    @SubscribeEvent
    public static void utMineBedrock(PlayerInteractEvent.LeftClickBlock event)
    {
        if (!UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle || whitelistedTools.isEmpty()) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBreakableBedrock ::: Left click block event");
        Item heldTool = event.getEntityPlayer().getHeldItemMainhand().getItem();
        if (whitelistedTools.contains(heldTool)) return;
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        Block block = world.getBlockState(blockPos).getBlock();
        if (block instanceof UTBlockBedrock)
        {
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
            event.setCanceled(true);
        }
    }

    public static void initLists()
    {
        whitelistedTools.clear();
        try
        {
            for (String entry : UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockWhitelist)
            {
                ResourceLocation resLoc = new ResourceLocation(entry);
                if (ForgeRegistries.ITEMS.containsKey(resLoc)) whitelistedTools.add(ForgeRegistries.ITEMS.getValue(resLoc));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Breakable Bedrock tool whitelist initialized");
    }
}