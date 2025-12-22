package mod.acgaming.universaltweaks.mods.bibliocraft.printpress.mixin;

import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreIngredient;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.tileentities.TileEntityPrintPress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityPrintPress.class, remap = false)
public abstract class UTTileEntityPrintPressMixin
{
    @Unique
    private static final Ingredient ut$INK_INGREDIENT = new OreIngredient("dyeBlack");

    /**
     * @author WaitingIdly
     * @reason Get the proper "burn time" for the ink item.
     */
    @Definition(id = "ItemDye", type = ItemDye.class)
    @Expression("? instanceof ItemDye")
    @ModifyExpressionValue(method = "getItemBurnTime", at = @At("MIXINEXTRAS:EXPRESSION"))
    private static boolean utAnyInk(boolean original, @Local(argsOnly = true) ItemStack stack)
    {
        return ut$INK_INGREDIENT.apply(stack);
    }

    /**
     * @author WaitingIdly
     * @reason Allow inserting any valid ink Ingredient to slot 0, not just ink sacs.
     */
    @Inject(method = "canInsertItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"), cancellable = true, remap = true)
    private void utInsertAnyInk(int slot, ItemStack itemstack, EnumFacing side, CallbackInfoReturnable<Boolean> cir)
    {
        if (slot == 0 && ut$INK_INGREDIENT.apply(itemstack)) cir.setReturnValue(true);
    }

    /**
     * @author WaitingIdly
     * @reason Replace the check in isInk with a check to the Ingredient.
     */
    @Inject(method = "isInk", at = @At("HEAD"), cancellable = true)
    private void utIngredientIsInk(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(ut$INK_INGREDIENT.apply(stack));
    }
}
