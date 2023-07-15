package mod.acgaming.universaltweaks.mods.ironbackpacks.mixin;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.DyeUtils;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.core.recipe.ColorBackpackRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = ColorBackpackRecipe.class, remap = false)
public abstract class UTColorBackpackRecipeMixin
{
    private static final Map<String, Integer> colorValues;

    static
    {
        colorValues = new HashMap<>();
        colorValues.put("white", 16383998);
        colorValues.put("orange", 16351261);
        colorValues.put("magenta", 13061821);
        colorValues.put("lightBlue", 3847130);
        colorValues.put("yellow", 16701501);
        colorValues.put("lime", 8439583);
        colorValues.put("pink", 15961002);
        colorValues.put("gray", 4673362);
        colorValues.put("silver", 10329495);
        colorValues.put("cyan", 1481884);
        colorValues.put("purple", 8991416);
        colorValues.put("blue", 3949738);
        colorValues.put("brown", 8606770);
        colorValues.put("green", 6192150);
        colorValues.put("red", 11546150);
        colorValues.put("black", 1908001);
    }

    @Shadow
    @Nonnull
    @SuppressWarnings("ConstantConditions")
    private static ItemStack getFirstDyeOrWaterBucketInGrid(@Nonnull InventoryCrafting matrix)
    {
        return null;
    }

    /**
     * @reason Make the code works server-side
     * @author DupeFix Project
     */
    @Overwrite
    @SuppressWarnings("cast")
    private int getDyeColor(@Nonnull InventoryCrafting matrix)
    {
        ItemStack stack = getFirstDyeOrWaterBucketInGrid(matrix);
        if (stack.isEmpty()) return BackpackInfo.NO_COLOR; //Error, return no color
        if (stack.getItem().equals(Items.WATER_BUCKET)) return BackpackInfo.NO_COLOR; //Wash away color
        return DyeUtils.colorFromStack(stack).map(dye -> colorValues.getOrDefault(dye.getTranslationKey(), BackpackInfo.NO_COLOR)).orElse(BackpackInfo.NO_COLOR); //Get dye RGB color and return it if possible, otherwise return no color
    }
}