package mod.acgaming.universaltweaks.mods.effortlessbuilding.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import nl.requios.effortlessbuilding.compatibility.CompatHelper;
import nl.requios.effortlessbuilding.item.ItemRandomizerBag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static nl.requios.effortlessbuilding.compatibility.CompatHelper.dankNullItem;

@Mixin(CompatHelper.class)
public class UTCompatHelperMixin
{
    /**
     * @author Irgendwer01
     * @reason Fix EB ignoring metadata
     */
    @Overwrite(remap = false)
    public static ItemStack getItemBlockByState(ItemStack stack, IBlockState state)
    {
        int meta = state.getBlock().damageDropped(state);
        ItemStack blockStack = new ItemStack(state.getBlock(), 1, meta);
        if (stack.getItem() instanceof ItemBlock && stack.getMetadata() == meta)
        {
            return stack;
        }
        if (stack.getItem() instanceof ItemRandomizerBag)
        {
            IItemHandler bagInventory = ItemRandomizerBag.getBagInventory(stack);
            for (int i = 0; i < bagInventory.getSlots(); ++i)
            {
                ItemStack bagStack = bagInventory.getStackInSlot(i);
                if (!bagStack.isEmpty() && stack.isItemEqual(blockStack))
                {
                    return stack;
                }
            }
        }
        if (stack.getItem() == dankNullItem)
        {
            IItemHandler handler = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            for (int i = 0; i < handler.getSlots(); ++i)
            {
                ItemStack ref = handler.getStackInSlot(i);
                if (ref.getItem() instanceof ItemBlock && ref.isItemEqual(blockStack))
                {
                    return (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(i);
                }
            }
        }
        return ItemStack.EMPTY;
    }

}
