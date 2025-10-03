package mod.acgaming.universaltweaks.tweaks.misc.armorswap.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({ItemArmor.class, ItemElytra.class})
public class UTItemArmorMixin
{
    /**
     * @reason change check from "is armor slot empty" to "does armor slot not have curse of binding"
     * @author WaitingIdly
     */
    @WrapOperation(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private boolean utCheckNotBindingCurse(ItemStack stack, Operation<Boolean> original)
    {
        return !EnchantmentHelper.hasBindingCurse(stack);
    }

    /**
     * @reason if the hand stack is empty, set the return value to be the itemstack that was in the armor slot,
     * which will cause the hand to be replaced by it
     * @author WaitingIdly
     */
    @ModifyReturnValue(method = "onItemRightClick", at = @At(value = "RETURN", ordinal = 0))
    private ActionResult<ItemStack> utSwapEquippedArmorStack(ActionResult<ItemStack> original, @Local(ordinal = 0) ItemStack handStack, @Local(ordinal = 1) ItemStack armorStack)
    {
        return handStack.isEmpty() ? new ActionResult<>(EnumActionResult.SUCCESS, armorStack) : original;
    }
}
