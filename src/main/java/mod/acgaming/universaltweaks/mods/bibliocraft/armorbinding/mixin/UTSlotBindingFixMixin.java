package mod.acgaming.universaltweaks.mods.bibliocraft.armorbinding.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import jds.bibliocraft.slots.SlotArmorBoots;
import jds.bibliocraft.slots.SlotArmorCuirass;
import jds.bibliocraft.slots.SlotArmorGreaves;
import jds.bibliocraft.slots.SlotArmorHelm;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of WaitingIdly
@Mixin(value = {SlotArmorBoots.class, SlotArmorCuirass.class, SlotArmorGreaves.class, SlotArmorHelm.class}, remap = false)
public abstract class UTSlotBindingFixMixin extends Slot
{
    public UTSlotBindingFixMixin(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    /**
     * @author WaitingIdly
     * Ensure that the curse of binding logic is applied, applies same logic as the
     * custom slots for armor in {@link net.minecraft.inventory.ContainerPlayer}.
     */
    @Override
    public boolean canTakeStack(@NotNull EntityPlayer playerIn)
    {
        ItemStack itemstack = this.getStack();
        return (itemstack.isEmpty() || playerIn.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(playerIn);
    }
}
