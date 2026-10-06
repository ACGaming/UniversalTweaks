package mod.acgaming.universaltweaks.bugfixes.misc.container.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Container.class)
public class UTContainerMixin
{
    @Shadow
    public List<Slot> inventorySlots;

    /**
     * @author Invadermonky
     * @reason Fixes an edge-case crash that occurs when a hotbar button (0-9) is pressed right as an inventory is closed.
     *         This mixin will not fix mods that have custom {@link Container#slotClick(int, int, ClickType, EntityPlayer)},
     *         behavior, but it will fix all regular cases.
     */
    @Inject(method = "slotClick", at = @At("HEAD"), cancellable = true)
    private void utValidateContainerSlotIndex(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir)
    {
        if(this.inventorySlots == null || slotId >= this.inventorySlots.size())
        {
            cir.setReturnValue(ItemStack.EMPTY);
        }
    }
}
