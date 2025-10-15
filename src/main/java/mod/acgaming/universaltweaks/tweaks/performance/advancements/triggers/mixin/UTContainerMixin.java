package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = Container.class)
public class UTContainerMixin
{
    /**
     * Initialize player inventory slots known not to be empty when opening a GUI other than the player inventory.
     */
    @ModifyArg(method = "addSlotToContainer", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;add(Ljava/lang/Object;)Z", remap = false))
    private Object utAddKnownContents(Object emptyStack, @Local(argsOnly = true) Slot slot)
    {
        if (!UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utInitializeContainerWithInventory
            && slot.inventory instanceof InventoryPlayer && slot.getHasStack())
        {
            return slot.getStack();
        }
        return emptyStack;
    }

    @Inject(method = "detectAndSendChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isEmpty()Z"))
    private void utCapturePreviousStack(CallbackInfo ci, @Local(ordinal = 1) ItemStack prevStack, @Share("prevStack") LocalRef<ItemStack> prevStackRef)
    {
        prevStackRef.set(prevStack);
    }

    /**
     * Save the previous stack size if only the item count changed.
     *
     * @param newStack     the new item stack
     * @param prevStackRef reference to the old item stack, set by {@link #utCapturePreviousStack(CallbackInfo, ItemStack, LocalRef)}
     */
    @Inject(method = "detectAndSendChanges", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/NonNullList;set(ILjava/lang/Object;)Ljava/lang/Object;", shift = At.Shift.AFTER))
    private void utSetPreviousStackSize(CallbackInfo ci, @Local(ordinal = 1) ItemStack newStack, @Share("prevStack") LocalRef<ItemStack> prevStackRef)
    {
        if (ItemStack.areItemsEqual(prevStackRef.get(), newStack))
        {
            ((IPrevSizeTracker) (Object) newStack).ut$setPreviousStackSize(prevStackRef.get().getCount());
        }
    }
}
