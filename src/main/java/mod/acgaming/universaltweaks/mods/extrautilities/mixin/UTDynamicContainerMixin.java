package mod.acgaming.universaltweaks.mods.extrautilities.mixin;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import com.rwtema.extrautils2.compatibility.CompatHelper;
import com.rwtema.extrautils2.compatibility.StackHelper;
import com.rwtema.extrautils2.gui.backend.DynamicContainer;
import com.rwtema.extrautils2.utils.ItemStackNonNull;
import mcp.MethodsReturnNonnullByDefault;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = DynamicContainer.class, remap = false)
@MethodsReturnNonnullByDefault
public abstract class UTDynamicContainerMixin extends Container
{
    @Shadow
    public int playerSlotsStart;

    @Override
    @ItemStackNonNull
    @ParametersAreNonnullByDefault
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = StackHelper.empty();
        Slot slot = this.inventorySlots.get(par2);

        if (slot instanceof SlotCrafting)
        {
            ItemStack itemstack1 = slot.getStack();
            if (slot.getHasStack() && StackHelper.isNonNull(itemstack1))
            {
                itemstack = itemstack1.copy();
                if (!this.mergeItemStack(itemstack1, playerSlotsStart, this.inventorySlots.size(), true))
                {
                    return StackHelper.empty();
                }

                slot.onSlotChange(itemstack1, itemstack);

                if (StackHelper.isEmpty(itemstack1))
                {
                    slot.putStack(StackHelper.empty());
                }
                else
                {
                    slot.onSlotChanged();
                }

                if (StackHelper.getStacksize(itemstack1) == StackHelper.getStacksize(itemstack))
                {
                    return StackHelper.empty();
                }

                CompatHelper.setSlot(slot, par1EntityPlayer, itemstack1);
            }
            return itemstack;
        }

        if (playerSlotsStart > 0 && slot != null && slot.getHasStack())
        {
            ItemStack otherItemStack = slot.getStack();
            if (StackHelper.isNull(otherItemStack)) return StackHelper.empty();
            itemstack = otherItemStack.copy();

            if (par2 < playerSlotsStart)
            {
                if (!this.mergeItemStack(otherItemStack, playerSlotsStart, this.inventorySlots.size(), true))
                {
                    return StackHelper.empty();
                }
            }
            else
            {
                if (!this.mergeItemStack(otherItemStack, 0, playerSlotsStart, false))
                {
                    return StackHelper.empty();
                }
            }

            if (StackHelper.isEmpty(otherItemStack))
            {
                slot.putStack(StackHelper.empty());
            }
            else
            {
                slot.putStack(otherItemStack);
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }
}