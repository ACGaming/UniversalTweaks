package mod.acgaming.universaltweaks.tweaks.craftingcache.mixin;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.craftingcache.UTCraftingCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of EverNife
@Mixin(CraftingManager.class)
public class UTCraftingManagerMixin
{
    /**
     * @author EverNife
     * @reason Cache NON_NBT Crafting Matrix by [Item and Meta],
     * to prevent dispendious time on large modpacks with thousands of craftings
     */
    @Inject(method = "findMatchingRecipe", at = @At("HEAD"), cancellable = true)
    private static void utFindMatchingRecipe(InventoryCrafting craftMatrix, World worldIn, CallbackInfoReturnable<IRecipe> cir)
    {
        if (!UTConfig.tweaks.utCraftingCacheToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingManager ::: Find matching recipe");
        cir.setReturnValue(UTCraftingCache.findMatchingRecipe(craftMatrix, worldIn));
    }

    /**
     * @author EverNife
     * @reason Cache NON_NBT Crafting Matrix by [Item and Meta],
     * to prevent dispendious time on large modpacks with thousands of craftings
     */
    @Inject(method = "getRemainingItems", at = @At("HEAD"), cancellable = true)
    private static void utGetRemainingItems(InventoryCrafting craftMatrix, World worldIn, CallbackInfoReturnable<NonNullList<ItemStack>> cir)
    {
        if (!UTConfig.tweaks.utCraftingCacheToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingManager ::: Get remaining items");
        IRecipe iRecipe = UTCraftingCache.findMatchingRecipe(craftMatrix, worldIn);
        if (iRecipe != null) cir.setReturnValue(iRecipe.getRemainingItems(craftMatrix));
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < nonnulllist.size(); ++i) nonnulllist.set(i, craftMatrix.getStackInSlot(i));
        cir.setReturnValue(nonnulllist);
    }
}