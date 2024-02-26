package mod.acgaming.universaltweaks.bugfixes.misc.crafteditemstatistics.mixin;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-65198, MC-161869
// https://bugs.mojang.com/browse/MC-65198
// https://bugs.mojang.com/browse/MC-161869
// Courtesy of mrgrim
@Mixin(SlotCrafting.class)
public abstract class UTSlotCraftingMixin extends Slot
{
    @Shadow
    private int amountCrafted;

    public UTSlotCraftingMixin(IInventory inventoryIn, int index, int xPosition, int yPosition)
    {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Inject(method = "decrStackSize", at = @At("HEAD"), cancellable = true)
    private void utFixCraftingStats(int amount, CallbackInfoReturnable<ItemStack> cir)
    {
        if (UTConfigBugfixes.MISC.utCraftedItemStatisticsToggle)
        {
            ItemStack ret = super.decrStackSize(amount);
            this.amountCrafted += ret.getCount();

            cir.setReturnValue(ret);
        }
    }
}