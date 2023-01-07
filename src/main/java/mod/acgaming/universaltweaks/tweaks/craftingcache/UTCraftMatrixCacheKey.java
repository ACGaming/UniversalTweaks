package mod.acgaming.universaltweaks.tweaks.craftingcache;

import java.util.Objects;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;

// Courtesy of EverNife
public class UTCraftMatrixCacheKey
{
    final int hashCode;

    public UTCraftMatrixCacheKey(InventoryCrafting craftMatrix)
    {
        Object[] objects = new Object[craftMatrix.getSizeInventory() * 2];
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack stack = craftMatrix.getStackInSlot(i);
            objects[i] = stack.getItem();
            objects[i + craftMatrix.getSizeInventory()] = objects[i] != Items.AIR ? stack.getItemDamage() : 0;
        }
        hashCode = Objects.hash(objects);
    }

    @Override
    public int hashCode()
    {
        return hashCode;
    }

    @Override
    public boolean equals(Object o)
    {
        UTCraftMatrixCacheKey that = (UTCraftMatrixCacheKey) o;
        return hashCode == that.hashCode;
    }
}