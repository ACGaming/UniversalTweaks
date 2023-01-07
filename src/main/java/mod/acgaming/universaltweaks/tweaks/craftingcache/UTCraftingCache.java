package mod.acgaming.universaltweaks.tweaks.craftingcache;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;

// Courtesy of EverNife
public class UTCraftingCache
{
    public static final Int2ObjectLinkedOpenHashMap<UTOptionalContent<IRecipe>> NON_NBT_CRAFT_CACHE = new Int2ObjectLinkedOpenHashMap<>();

    public static boolean hasAnyNBT(InventoryCrafting craftMatrix)
    {
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack itemStack = craftMatrix.getStackInSlot(i);
            if (itemStack.hasTagCompound()) return true;
        }
        return false;
    }

    public static IRecipe default_findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : CraftingManager.REGISTRY) if (irecipe.matches(craftMatrix, worldIn)) return irecipe;
        return null;
    }

    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        boolean hasNBT = UTCraftingCache.hasAnyNBT(craftMatrix);
        if (!hasNBT)
        {
            UTOptionalContent<IRecipe> optionalContent = UTCraftingCache.getOrCreateCachedRecipe(craftMatrix, worldIn);
            if (!optionalContent.hasContent()) optionalContent.setContent(default_findMatchingRecipe(craftMatrix, worldIn));
            return optionalContent.getContent();
        }
        return default_findMatchingRecipe(craftMatrix, worldIn);
    }

    public static UTOptionalContent<IRecipe> getOrCreateCachedRecipe(InventoryCrafting craftMatrix, World worldInIgnored)
    {
        UTCraftMatrixCacheKey matrixKey = new UTCraftMatrixCacheKey(craftMatrix);
        UTOptionalContent<IRecipe> optionalContent = NON_NBT_CRAFT_CACHE.getAndMoveToFirst(matrixKey.hashCode());
        if (optionalContent == null)
        {
            optionalContent = new UTOptionalContent<>();
            NON_NBT_CRAFT_CACHE.putAndMoveToFirst(matrixKey.hashCode(), optionalContent);
        }
        return optionalContent;
    }
}