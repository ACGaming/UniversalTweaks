package mod.acgaming.universaltweaks.mods.extrautilities.wildcardmatch.mixin;

import com.rwtema.extrautils2.recipes.GenericMachineRecipe;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GenericMachineRecipe.class, remap = false)
public class UTGenericMachineRecipeMixin {
    @Redirect(method = "matchesSlotItem", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/oredict/OreDictionary;itemMatches(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Z)Z"))
    private boolean fix(ItemStack target, ItemStack input, boolean strict) {
        return OreDictionary.itemMatches(input, target, strict);
    }
}
