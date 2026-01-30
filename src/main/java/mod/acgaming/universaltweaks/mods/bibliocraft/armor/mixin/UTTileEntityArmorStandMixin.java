package mod.acgaming.universaltweaks.mods.bibliocraft.armor.mixin;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import jds.bibliocraft.tileentities.TileEntityArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityArmorStand.class, remap = false)
public abstract class UTTileEntityArmorStandMixin
{
    /**
     * @author WaitingIdly
     * @reason BiblioCraft incorrectly checks slot numbers, inverting the order of head to feet.
     * It also fails to properly check the slot used for items, only allowing armor instead of
     * using the forge method for anything that fits in the armor slot.
     */
    @Inject(method = "isItemValidForSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;", ordinal = 0), cancellable = true, remap = true)
    private void utEnsureValidity(int slot, ItemStack itemstack, CallbackInfoReturnable<Boolean> cir)
    {
        if (slot == 0)
        {
            cir.setReturnValue(EntityEquipmentSlot.HEAD == EntityLiving.getSlotForItemStack(itemstack));
        }
        else if (slot == 1)
        {
            cir.setReturnValue(EntityEquipmentSlot.CHEST == EntityLiving.getSlotForItemStack(itemstack));
        }
        else if (slot == 2)
        {
            cir.setReturnValue(EntityEquipmentSlot.LEGS == EntityLiving.getSlotForItemStack(itemstack));
        }
        else if (slot == 3)
        {
            cir.setReturnValue(EntityEquipmentSlot.FEET == EntityLiving.getSlotForItemStack(itemstack));
        }
        else
        {
            cir.setReturnValue(false);
        }
    }
}
