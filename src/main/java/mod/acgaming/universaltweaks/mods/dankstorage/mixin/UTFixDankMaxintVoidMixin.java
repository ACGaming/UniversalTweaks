package mod.acgaming.universaltweaks.mods.dankstorage.mixin;

import com.tfar.dankstorage.container.AbstractAbstractDankContainer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;

@Mixin(AbstractAbstractDankContainer.class)
public class UTFixDankMaxintVoidMixin {
    // Fixes Max Int (2.1B) stacks being voided when right clicking on them in a Dank, due to a maxint + 1 overflow
    @Redirect(
        method = "slotClick(IILnet/minecraft/inventory/ClickType;Lnet/minecraft/entity/player/EntityPlayer;)Lnet/minecraft/item/ItemStack;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/inventory/Slot;decrStackSize(I)Lnet/minecraft/item/ItemStack;",
            ordinal = 0
        )
    )
    private ItemStack safeDecrStackSize(Slot slot, int originalAmount) {
        if (!UTConfigMods.DANK_STORAGE.utFixMaxIntVoid) return slot.decrStackSize(originalAmount);

        ItemStack slotStack = slot.getStack();
        int slotCount = slotStack.isEmpty() ? 0 : slotStack.getCount();

        if (originalAmount <= 0 || slotCount < originalAmount) {
            long half = ((long) slotCount + 1L) / 2L;

            if (half < 0L) half = 0L;
            if (half > slotCount) half = slotCount;

            originalAmount = (int) half;
        }

        return slot.decrStackSize(originalAmount);
    }
}