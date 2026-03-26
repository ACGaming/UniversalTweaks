package mod.acgaming.universaltweaks.mods.enderio.impulsehopper.mixin;

import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(targets = "crazypants.enderio.machines.machine.ihopper.TileImpulseHopper$PredicateItemStackMatch", remap = false)
public abstract class UTPredicateItemStackMatchMixin
{
    /**
     * @author WaitingIdly
     * @reason The Impulse Hopper filters its inputs by each slot's "ghost item",
     * and does so via checking {@link ItemStack#isItemEqual}.
     * However, actually moving the items also checks {@link ItemStack#areItemStackTagsEqual},
     * which means that items with different nbt value can be inserted, but will never be moved.
     */
    @WrapOperation(method = "doApply", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemEqual(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean utCheckNBTTag(ItemStack instance, ItemStack other, Operation<Boolean> original)
    {
        return original.call(instance, other) && ItemStack.areItemStackTagsEqual(instance, other);
    }
}
