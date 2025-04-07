package mod.acgaming.universaltweaks.mods.gaiadimension.restructurer.mixin;

import net.minecraft.item.ItemStack;

import androsa.gaiadimension.block.tileentity.TileEntityRestructurer;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Cancellable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityRestructurer.class, remap = false)
public abstract class UTTileEntityRestructurerMixin
{
    /**
     * @author WaitingIdly
     * @reason since {@link androsa.gaiadimension.recipe.RestructurerRecipes#getRefactoringResult(ItemStack)} can return null,
     * and there is no validation for array length prior to accessing,
     * the mod can throw a {@link NullPointerException} or an {@link ArrayIndexOutOfBoundsException}.
     */
    @ModifyExpressionValue(method = "canChange", at = @At(value = "INVOKE", target = "Landrosa/gaiadimension/recipe/RestructurerRecipes;getRefactoringResult(Lnet/minecraft/item/ItemStack;)[Lnet/minecraft/item/ItemStack;"))
    private ItemStack[] ensureProperNullHandling(ItemStack[] original, @Cancellable CallbackInfoReturnable<Boolean> cir)
    {
        if (original == null || original.length != 2) cir.setReturnValue(false);
        return original;
    }
}
