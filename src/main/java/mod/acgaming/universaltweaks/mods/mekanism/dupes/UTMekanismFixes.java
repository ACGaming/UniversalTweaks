package mod.acgaming.universaltweaks.mods.mekanism.dupes;

import java.util.ArrayList;

import com.google.common.collect.Lists;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

import mekanism.common.recipe.BinRecipe;

// Courtesy of Focamacho
public class UTMekanismFixes
{
    //Bin Recipe Dupe Fix
    public static void fixBinRecipes()
    {
        ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>) ForgeRegistries.RECIPES;
        ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValuesCollection());
        for (IRecipe recipe : recipes)
        {
            if (recipe instanceof BinRecipe)
            {
                recipeRegistry.remove(recipe.getRegistryName());
            }
        }
    }
}