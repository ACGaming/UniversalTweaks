package mod.acgaming.universaltweaks.mods.bibliocraft.transfer.mixin;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import jds.bibliocraft.tileentities.BiblioTileEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of WaitingIdly
@Mixin(value = BiblioTileEntity.class, remap = false)
public abstract class UTBiblioTileEntityMixin implements IInventory
{
    @Shadow
    public abstract int getSlotLimit(int slot);

    /**
     * @author WaitingIdly
     * @reason Properly implement insertion logic, based off of {@link net.minecraftforge.items.wrapper.InvWrapper InvWrapper}.
     * Overwritten version does not simulate correctly, meaning that anything that checks if simulation passes
     * will never insert, does not check if the itemstacks can stack, only that they are the same item,
     * and ignores the actual slot limit.
     */
    @Overwrite
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        ItemStack stackInSlot = this.getStackInSlot(slot);
        if (!stackInSlot.isEmpty())
        {
            if (stackInSlot.getCount() >= Math.min(stackInSlot.getMaxStackSize(), getInventoryStackLimit())) return stack;
            if (!ItemHandlerHelper.canItemStacksStack(stack, stackInSlot)) return stack;
            if (!this.isItemValidForSlot(slot, stack)) return stack;
            int m = Math.min(stack.getMaxStackSize(), getInventoryStackLimit()) - stackInSlot.getCount();
            if (stack.getCount() <= m)
            {
                if (!simulate)
                {
                    ItemStack copy = stack.copy();
                    copy.grow(stackInSlot.getCount());
                    this.setInventorySlotContents(slot, copy);
                }
                return ItemStack.EMPTY;
            }
            // copy the stack to not modify the original one
            stack = stack.copy();
            if (simulate)
            {
                stack.shrink(m);
            }
            else
            {
                ItemStack copy = stack.splitStack(m);
                copy.grow(stackInSlot.getCount());
                this.setInventorySlotContents(slot, copy);
            }
            return stack;
        }
        if (!this.isItemValidForSlot(slot, stack)) return stack;
        int m = Math.min(stack.getMaxStackSize(), getInventoryStackLimit());
        if (m < stack.getCount())
        {
            // copy the stack to not modify the original one
            stack = stack.copy();
            if (simulate)
            {
                stack.shrink(m);
            }
            else
            {
                this.setInventorySlotContents(slot, stack.splitStack(m));
            }
            return stack;
        }
        if (!simulate)
        {
            this.setInventorySlotContents(slot, stack);
        }
        return ItemStack.EMPTY;
    }

    /**
     * @author WaitingIdly
     * @reason Properly implement extraction logic, based off of {@link net.minecraftforge.items.wrapper.InvWrapper InvWrapper}.
     * Overwritten version does not properly simulate extraction, always returning {@link ItemStack#EMPTY}
     */
    @Overwrite
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (amount == 0) return ItemStack.EMPTY;
        ItemStack stackInSlot = this.getStackInSlot(slot);
        if (stackInSlot.isEmpty()) return ItemStack.EMPTY;
        if (simulate)
        {
            if (stackInSlot.getCount() < amount)
            {
                return stackInSlot.copy();
            }
            ItemStack copy = stackInSlot.copy();
            copy.setCount(amount);
            return copy;
        }
        int m = Math.min(stackInSlot.getCount(), amount);
        return this.decrStackSize(slot, m);
    }
}
