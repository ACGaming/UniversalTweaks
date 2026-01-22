package mod.acgaming.universaltweaks.mods.bibliocraft.armor.mixin;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.containers.ContainerArmor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

// Courtesy of WaitingIdly
@Mixin(value = ContainerArmor.class, remap = false)
public abstract class UTContainerArmorMixin
{
    /**
     * @author WaitingIdly
     * @reason Slots are ordered 0/1/2/3 as HEAD/CHEST/LEGS/FEET, BiblioCraft inverts that order in this location.
     * This fixes inserting into the armor stand slots.
     */
    @WrapOperation(method = "transferStackInSlot", at = @At(value = "INVOKE", target = "Ljds/bibliocraft/containers/ContainerArmor;mergeItemStack(Lnet/minecraft/item/ItemStack;IIZ)Z"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/inventory/EntityEquipmentSlot;FEET:Lnet/minecraft/inventory/EntityEquipmentSlot;"), to = @At(value = "CONSTANT", args = "classValue=net/minecraft/item/ItemSkull")), remap = true)
    private boolean utEnsureArmorStandOrder(ContainerArmor instance, ItemStack stack, int startIndex, int endIndex, boolean reverseDirection, Operation<Boolean> original)
    {
        int start = startIndex <= 3 ? 3 - startIndex : startIndex;
        return original.call(instance, stack, start, start + 1, reverseDirection);
    }

    /**
     * @author WaitingIdly
     * @reason Slots are ordered 0/1/2/3 as HEAD/CHEST/LEGS/FEET for Vanilla, BiblioCraft has the incorrect order.
     * This fixes shift-clicking from the player armor slots.
     */
    @WrapOperation(method = "transferStackInSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;get(I)Ljava/lang/Object;"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/inventory/EntityEquipmentSlot;FEET:Lnet/minecraft/inventory/EntityEquipmentSlot;"), to = @At(value = "CONSTANT", args = "classValue=net/minecraft/item/ItemSkull")), remap = true)
    private Object utEnsureArmorSlotOrder(NonNullList<Object> instance, int index, Operation<Object> original)
    {
        return original.call(instance, 3 - index);
    }

    /**
     * @author WaitingIdly
     * @reason Skip the ItemArmor check. Requires {@link #utSkipItemArmorCast} and {@link #utAccessRealEquipmentSlot}
     */
    @Definition(id = "ItemArmor", type = ItemArmor.class)
    @Expression("? instanceof ItemArmor")
    @ModifyExpressionValue(method = "transferStackInSlot", at = @At("MIXINEXTRAS:EXPRESSION"), remap = true)
    private boolean utSkipItemArmorCheck(boolean original)
    {
        return true;
    }

    /**
     * @author WaitingIdly
     * @reason Skip the ItemArmor cast. Requires {@link #utSkipItemArmorCheck} and {@link #utAccessRealEquipmentSlot}
     */
    @Definition(id = "ItemArmor", type = ItemArmor.class)
    @Expression("(ItemArmor) ?")
    @WrapOperation(method = "transferStackInSlot", at = @At("MIXINEXTRAS:EXPRESSION"), remap = true)
    private ItemArmor utSkipItemArmorCast(Object object, Operation<ItemArmor> original)
    {
        return null;
    }

    /**
     * @author WaitingIdly
     * @reason Returns the real EntityEquipmentSlot for the item, using the forge method.
     * Requires {@link #utSkipItemArmorCast} and {@link #utSkipItemArmorCheck}.
     * Fixes slots not respecting the armor type of the stack and only allowing {@link ItemArmor}.
     */
    @WrapOperation(method = "transferStackInSlot", at = @At(value = "FIELD", target = "Lnet/minecraft/item/ItemArmor;armorType:Lnet/minecraft/inventory/EntityEquipmentSlot;"), remap = true)
    private EntityEquipmentSlot utAccessRealEquipmentSlot(ItemArmor instance, Operation<EntityEquipmentSlot> original, @Local(ordinal = 0) ItemStack stack)
    {
        return EntityLiving.getSlotForItemStack(stack);
    }
}
