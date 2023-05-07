package mod.acgaming.universaltweaks.tweaks.performance.craftingcache;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

// Courtesy of EverNife
public class UTCraftingCache
{
    public static Int2ObjectLinkedOpenHashMap<UTOptionalContent<IRecipe>> NON_NBT_CRAFT_CACHE = new Int2ObjectLinkedOpenHashMap<>();

    public static boolean hasAnyNBT(InventoryCrafting craftMatrix)
    {
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack itemStack = craftMatrix.getStackInSlot(i);
            if (itemStack.hasTagCompound()) return true;
        }
        return false;
    }

    public static IRecipe findMatchingRecipeDefault(InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : CraftingManager.REGISTRY) if (irecipe.matches(craftMatrix, worldIn)) return irecipe;
        return null;
    }

    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        boolean hasNBT = hasAnyNBT(craftMatrix);
        if (!hasNBT && Loader.instance().hasReachedState(LoaderState.SERVER_STARTING))
        {
            UTOptionalContent<IRecipe> optionalContent = getOrCreateCachedRecipe(craftMatrix);
            if (!optionalContent.hasContent()) optionalContent.setContent(findMatchingRecipeDefault(craftMatrix, worldIn));
            return optionalContent.getContent();
        }
        return findMatchingRecipeDefault(craftMatrix, worldIn);
    }

    public static UTOptionalContent<IRecipe> getOrCreateCachedRecipe(InventoryCrafting craftMatrix)
    {
        UTCraftMatrixCacheKey matrixKey = new UTCraftMatrixCacheKey(craftMatrix);
        UTOptionalContent<IRecipe> optionalContent = NON_NBT_CRAFT_CACHE.getAndMoveToFirst(matrixKey.hashCode());
        if (optionalContent == null)
        {
            optionalContent = new UTOptionalContent<>();
            NON_NBT_CRAFT_CACHE.putAndMoveToFirst(matrixKey.hashCode(), optionalContent);
        }
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("Recipe HashCode: " + matrixKey.hashCode());
        return optionalContent;
    }
}