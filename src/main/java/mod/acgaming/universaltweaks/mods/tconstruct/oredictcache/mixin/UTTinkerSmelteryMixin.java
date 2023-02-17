package mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.mixin;

import java.io.File;
import java.io.IOException;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.UTOreDictCache;
import mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.UTOreDictRecipesState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

// Courtesy of youyihj
@Mixin(value = TinkerSmeltery.class, remap = false)
public class UTTinkerSmelteryMixin
{
    @Inject(method = "registerRecipeOredictMelting", at = @At("HEAD"), cancellable = true)
    private static void utRegisterRecipeOredictMeltingHead(CallbackInfo ci)
    {
        if (UTOreDictRecipesState.getCurrentState().isRead())
        {
            final NBTTagCompound cacheNBT = UTOreDictCache.cacheNBT;
            final NBTTagList recipeList = cacheNBT.getTagList("Recipes", Constants.NBT.TAG_COMPOUND);
            for (NBTBase nbtBase : recipeList)
            {
                final NBTTagCompound compound = (NBTTagCompound) nbtBase;
                final ItemStack item = new ItemStack(compound.getCompoundTag("item"));
                final FluidStack fluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluid"));
                if (!item.isEmpty() && fluid != null) TinkerRegistry.registerMelting(new MeltingRecipe(RecipeMatch.of(item, fluid.amount), fluid));
            }
            ci.cancel();
        }
    }

    @Inject(method = "registerRecipeOredictMelting", at = @At("TAIL"))
    private static void utRegisterRecipeOredictMeltingTail(CallbackInfo ci)
    {
        if (UTOreDictRecipesState.getCurrentState().isScan())
        {
            try
            {
                File file = UTOreDictCache.cacheFile;
                if (!file.exists()) file.createNewFile();
                CompressedStreamTools.write(UTOreDictCache.cacheNBT, UTOreDictCache.cacheFile);
            }
            catch (IOException e)
            {
                UniversalTweaks.LOGGER.error("UTTinkerSmeltery ::: Failed to write cache file", e);
            }
        }
    }

    @Redirect(method = "registerRecipeOredictMelting", at = @At(value = "INVOKE", target = "Lslimeknights/tconstruct/library/TinkerRegistry;registerMelting(Lslimeknights/tconstruct/library/smeltery/MeltingRecipe;)V"))
    private static void utRegisterMelting(MeltingRecipe recipe)
    {
        TinkerRegistry.registerMelting(recipe);
        if (UTOreDictRecipesState.getCurrentState().isScan())
        {
            final NBTTagCompound cacheNBT = UTOreDictCache.cacheNBT;
            if (!cacheNBT.hasKey("Recipes")) cacheNBT.setTag("Recipes", new NBTTagList());
            final NBTTagList recipeList = cacheNBT.getTagList("Recipes", Constants.NBT.TAG_COMPOUND);
            final NBTTagCompound recipeNBT = new NBTTagCompound();
            final ItemStack input = recipe.input.getInputs().get(0);
            final FluidStack output = recipe.output;
            if (!input.isEmpty() && FluidRegistry.isFluidRegistered(output.getFluid()))
            {
                recipeNBT.setTag("item", input.serializeNBT());
                recipeNBT.setTag("fluid", fluidToNBT(output));
                recipeList.appendTag(recipeNBT);
            }
        }
    }

    @Unique
    private static NBTTagCompound fluidToNBT(FluidStack fluidStack)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setString("FluidName", fluidStack.getFluid().getName());
        nbt.setInteger("Amount", fluidStack.amount);
        if (fluidStack.tag != null) nbt.setTag("Tag", fluidStack.tag);
        return nbt;
    }
}
