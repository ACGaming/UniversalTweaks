package mod.acgaming.universaltweaks.mods.calculator.mixin;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sonar.core.handlers.inventories.SonarLargeInventory;

// SonarLargeInventory.InventoryLargeSlot.getDrops() builds stacks but never adds them to the list,
// so Calculator Reinforced Chests void their contents when broken.
// https://github.com/SonarSonic/Sonar-Core/blob/f5b64e033e2c07af55c2d5f474052fb83fba5ca1/src/main/java/sonar/core/handlers/inventories/SonarLargeInventory.java#L313
@Mixin(value = SonarLargeInventory.InventoryLargeSlot.class, remap = false)
public abstract class UTInventoryLargeSlotMixin
{
    @Shadow
    private ItemStack stored_stack;

    @Shadow
    public abstract long getActualStored();

    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    private void utFixReinforcedChestDrops(CallbackInfoReturnable<List> cir)
    {
        List drops = new ArrayList<>();
        long drop = getActualStored();
        while (drop > 0)
        {
            int charge = (int) Math.min(stored_stack.getMaxStackSize(), drop);
            // Originally: ItemHandlerHelper.copyStackWithSize(stored_stack, change); (no drops.add())
            drops.add(ItemHandlerHelper.copyStackWithSize(stored_stack, charge));
            drop -= charge;
        }
        cir.setReturnValue(drops);
    }
}
