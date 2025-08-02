package mod.acgaming.universaltweaks.tweaks.blocks.anvil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTRepairableAnvil
{
    private static final Map<Item, Integer> repairItems = new HashMap<>();

    public static void initRepairItemsList()
    {
        repairItems.clear();
        try
        {
            for (String entry : UTConfigTweaks.BLOCKS.ANVIL.utDamagedAnvilRepairItems.keySet())
            {
                if (entry.contains(":"))
                {
                    ResourceLocation resLoc = new ResourceLocation(entry);
                    int amount = UTConfigTweaks.BLOCKS.ANVIL.utDamagedAnvilRepairItems.get(entry);
                    if (ForgeRegistries.ITEMS.containsKey(resLoc)) repairItems.put(ForgeRegistries.ITEMS.getValue(resLoc), amount);
                }
                else
                {
                    List<Item> items = OreDictionary.getOres(entry).stream().map(ItemStack::getItem).collect(Collectors.toList());
                    for (Item item : items)
                    {
                        if (item != null)
                        {
                            int amount = UTConfigTweaks.BLOCKS.ANVIL.utDamagedAnvilRepairItems.get(entry);
                            repairItems.put(item, amount);
                        }
                    }

                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Damaged Anvil repair items list initialized");
    }

    @SubscribeEvent
    public static void utRepairableAnvil(PlayerInteractEvent.RightClickBlock event)
    {
        if (repairItems.isEmpty()) return;
        if (!event.getEntityPlayer().isSneaking()) return;
        if (event.isCanceled()) return;
        if (event.getWorld().getBlockState(event.getPos()).getBlock() != Blocks.ANVIL) return;

        if (repairItems.containsKey(event.getItemStack().getItem()) && event.getItemStack().getCount() >= repairItems.get(event.getItemStack().getItem()))
        {
            IBlockState anvil = event.getWorld().getBlockState(event.getPos());
            int currentDamage = anvil.getValue(BlockAnvil.DAMAGE);
            if (currentDamage <= 0) return;

            event.getEntityPlayer().getHeldItemMainhand().shrink(repairItems.get(event.getItemStack().getItem()));
            IBlockState repaired = anvil.withProperty(BlockAnvil.DAMAGE, currentDamage - 1);
            event.getWorld().setBlockState(event.getPos(), repaired, 3);
            event.setResult(Event.Result.DENY);
        }
    }
}
