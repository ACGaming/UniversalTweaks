package mod.acgaming.universaltweaks.mods.bibliocraft.armor.mixin;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import jds.bibliocraft.slots.SlotArmorBoots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = SlotArmorBoots.class, remap = false)
public abstract class UTSlotArmorBootsMixin
{
    /**
     * @author WaitingIdly
     * @reason Make BiblioCraft use the forge method to determine
     * if the itemstack is valid for the desired armor slot.
     */
    @Inject(method = "isItemValid", at = @At("HEAD"), cancellable = true, remap = true)
    private void utEnsureValidity(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(EntityEquipmentSlot.FEET == EntityLiving.getSlotForItemStack(stack));
    }
}
