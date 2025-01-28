package mod.acgaming.universaltweaks.mods.enderio.soulbinderjei.mixin;

import java.util.List;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import crazypants.enderio.machines.integration.jei.SoulBinderRecipeCategory;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.recipe.IFocus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static crazypants.enderio.base.init.ModObject.itemSoulVial;

// Courtesy of WaitingIdly
@Mixin(value = SoulBinderRecipeCategory.class, remap = false)
public abstract class UTSoulBinderRecipeCategoryMixin
{
    /**
     * @author WaitingIdly
     * @reason ensure list replacing prior entries isn't empty,
     * as this will cause JEI to appear in an incorrect state.
     */
    @WrapWithCondition(method = "setRecipe(Lmezz/jei/api/gui/IRecipeLayout;Lcrazypants/enderio/machines/integration/jei/SoulBinderRecipeCategory$SoulBinderRecipeWrapper;Lmezz/jei/api/ingredients/IIngredients;)V", at = @At(value = "INVOKE", target = "Lmezz/jei/api/gui/IGuiItemStackGroup;set(ILjava/util/List;)V"))
    private boolean utEnsureListNotEmpty(IGuiItemStackGroup instance, int i, List<?> list)
    {
        return !list.isEmpty();
    }

    /**
     * @author WaitingIdly
     * @reason check that the focus is not an soul vial, as
     * checking for recipes that output that will cause
     * soul vials to replace the second output slot on all recipes.
     */
    @ModifyExpressionValue(method = "setRecipe(Lmezz/jei/api/gui/IRecipeLayout;Lcrazypants/enderio/machines/integration/jei/SoulBinderRecipeCategory$SoulBinderRecipeWrapper;Lmezz/jei/api/ingredients/IIngredients;)V", at = @At(value = "INVOKE", target = "Lcrazypants/enderio/util/CapturedMob;containsSoul(Lnet/minecraft/item/ItemStack;)Z", ordinal = 1))
    private boolean utCheckFocusNotVial(boolean original, @Local IFocus<?> focus)
    {
        // at this point, focus.getValue() has already been checked if its an instanceof ItemStack.
        return original && ((ItemStack) focus.getValue()).getItem() != itemSoulVial.getItemNN();
    }
}
