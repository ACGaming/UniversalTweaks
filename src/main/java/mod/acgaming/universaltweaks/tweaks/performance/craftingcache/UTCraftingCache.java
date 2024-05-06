package mod.acgaming.universaltweaks.tweaks.performance.craftingcache;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of EverNife
public class UTCraftingCache
{

    private static final Int2ObjectLinkedOpenHashMap<UTOptionalContent<IRecipe>> NON_NBT_CRAFT_CACHE = new Int2ObjectLinkedOpenHashMap<>();
    private static IRecipe lastMatchingRecipe = null;
    private static final boolean isIC2ClassicLoaded = Loader.isModLoaded("ic2-classic-spmod");

    public static void resetCache()
    {
        NON_NBT_CRAFT_CACHE.clear();
        lastMatchingRecipe = null;
    }

    public static boolean isValid(InventoryCrafting craftMatrix)
    {
        for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
        {
            ItemStack itemStack = craftMatrix.getStackInSlot(i);
            // Skip NBT items
            if (itemStack.hasTagCompound()) return false;
            // Skip IC2C's stacked items
            if (isIC2ClassicLoaded && itemStack.getCount() > 1) return false;
        }
        return true;
    }

    public static IRecipe findMatchingRecipeDefault(InventoryCrafting craftMatrix, World worldIn)
    {
        for (IRecipe irecipe : CraftingManager.REGISTRY)
        {
            if (irecipe.matches(craftMatrix, worldIn))
            {
                lastMatchingRecipe = irecipe;
                return irecipe;
            }
        }
        return null;
    }

    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        if (Loader.instance().hasReachedState(LoaderState.SERVER_STARTING))
        {
            final IRecipe previousMatch = lastMatchingRecipe;
            if (previousMatch != null && previousMatch.matches(craftMatrix, worldIn))
            {
                return previousMatch;
            }
            if (isValid(craftMatrix))
            {
                UTOptionalContent<IRecipe> optionalContent = getOrCreateCachedRecipe(craftMatrix);

                if (optionalContent.hasContent())
                {
                    IRecipe recipe = optionalContent.getContent();
                    if (recipe == null || recipe.matches(craftMatrix, worldIn))
                    {
                        lastMatchingRecipe = recipe;
                        return recipe;
                    }
                }

                IRecipe recipe = findMatchingRecipeDefault(craftMatrix, worldIn);
                optionalContent.setContent(recipe);
                return recipe;
            }
        }
        return findMatchingRecipeDefault(craftMatrix, worldIn);
    }

    public static synchronized UTOptionalContent<IRecipe> getOrCreateCachedRecipe(InventoryCrafting craftMatrix)
    {
        UTCraftMatrixCacheKey matrixKey = new UTCraftMatrixCacheKey(craftMatrix);
        UTOptionalContent<IRecipe> optionalContent = NON_NBT_CRAFT_CACHE.getAndMoveToFirst(matrixKey.hashCode());
        if (optionalContent == null)
        {
            optionalContent = new UTOptionalContent<>();
            NON_NBT_CRAFT_CACHE.putAndMoveToFirst(matrixKey.hashCode(), optionalContent);
        }
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("Recipe HashCode: " + matrixKey.hashCode());
        return optionalContent;
    }
}
