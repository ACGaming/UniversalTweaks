package mod.acgaming.universaltweaks.mods.moartinkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;

// Courtesy of kurrycat2004
public class UTBaubleHelper
{
    public static List<ItemStack> getBaublesInventory(Entity entity)
    {
        final IBaublesItemHandler handler = entity.getCapability(BaublesCapabilities.CAPABILITY_BAUBLES, null);
        if (handler == null) return Collections.emptyList();
        final int slots = handler.getSlots();
        final List<ItemStack> stacks = new ArrayList<>(slots);
        for (int i = 0; i < slots; i++)
        {
            stacks.add(handler.getStackInSlot(i));
        }
        return stacks;
    }
}
