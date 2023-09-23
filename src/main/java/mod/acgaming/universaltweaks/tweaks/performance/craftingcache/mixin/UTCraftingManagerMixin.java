package mod.acgaming.universaltweaks.tweaks.performance.craftingcache.mixin;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.tweaks.performance.craftingcache.UTCraftingCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// Courtesy of EverNife
@Mixin(CraftingManager.class)
public class UTCraftingManagerMixin
{
    /**
     * @author EverNife
     * @reason Crafting cache
     */
    @Overwrite
    @Nullable
    public static IRecipe findMatchingRecipe(InventoryCrafting craftMatrix, World worldIn)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingManager ::: Find matching recipe");
        return UTCraftingCache.findMatchingRecipe(craftMatrix, worldIn);
    }

    /**
     * @author EverNife
     * @reason Crafting cache
     */
    @Overwrite
    public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingManager ::: Get remaining items");
        IRecipe iRecipe = UTCraftingCache.findMatchingRecipe(craftMatrix, worldIn);
        if (iRecipe != null) return iRecipe.getRemainingItems(craftMatrix);
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); i++) nonnulllist.set(i, craftMatrix.getStackInSlot(i));
        return nonnulllist;
    }
}