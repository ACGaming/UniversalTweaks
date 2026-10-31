package mod.acgaming.universaltweaks.mods.calculator.mixin;

import java.util.List;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sonar.core.handlers.inventories.SonarLargeInventory;

// SonarLargeInventory.InventoryLargeSlot.getDrops() builds stacks but never adds them to the list,
// so Calculator Reinforced Chests void their contents when broken.
// https://github.com/SonarSonic/Sonar-Core/blob/f5b64e033e2c07af55c2d5f474052fb83fba5ca1/src/main/java/sonar/core/handlers/inventories/SonarLargeInventory.java#L313
@Mixin(value = SonarLargeInventory.InventoryLargeSlot.class, remap = false)
public class UTInventoryLargeSlotMixin
{
    @ModifyExpressionValue(method = "getDrops", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/ItemHandlerHelper;copyStackWithSize(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack utAddToList(ItemStack stack, @Local List drops)
    {
        drops.add(stack);
        return stack;
    }
}
