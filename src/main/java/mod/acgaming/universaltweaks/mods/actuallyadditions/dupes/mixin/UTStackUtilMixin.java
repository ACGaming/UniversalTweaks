package mod.acgaming.universaltweaks.mods.actuallyadditions.dupes.mixin;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.ellpeck.actuallyadditions.mod.util.ItemStackHandlerAA;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Courtesy of IntegerLimit
 * Fixes `canAddAll` function increasing stack counts in stack list;
 * under specific edge cases (e.g. inventory is empty)
 */
@Mixin(value = StackUtil.class, remap = false)
public class UTStackUtilMixin
{
    @WrapOperation(method =
        {
            "canAddAll(Lde/ellpeck/actuallyadditions/mod/util/ItemStackHandlerAA;Ljava/util/List;Z)Z",
            "canAddAll(Lde/ellpeck/actuallyadditions/mod/util/ItemStackHandlerAA;Ljava/util/List;IIZ)Z"
        }, at = @At(value = "INVOKE", target = "Lde/ellpeck/actuallyadditions/mod/util/ItemStackHandlerAA;insertItem(ILnet/minecraft/item/ItemStack;ZZ)Lnet/minecraft/item/ItemStack;"), require = 1)
    private static ItemStack utCopyStack(ItemStackHandlerAA instance, int slot, ItemStack stack, boolean simulate, boolean fromAutomation, Operation<ItemStack> original)
    {
        return original.call(instance, slot, stack.copy(), simulate, fromAutomation);
    }
}
