package mod.acgaming.universaltweaks.mods.roots.disabledmodifier.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import epicsquid.roots.container.slots.SlotImposerModifierInfo;
import epicsquid.roots.modifiers.instance.staff.StaffModifierInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = SlotImposerModifierInfo.class, remap = false)
public abstract class UTSlotImposerModifierInfoMixin
{
    /**
     * @author WaitingIdly
     * @reason Without this, you can insert the modifier items into the staff
     * while the relevant modifier is disabled, which voids the item.
     * This makes the modifier not valid if its disabled.
     */
    @ModifyExpressionValue(method = "isItemValid", at = @At(value = "INVOKE", target = "Lepicsquid/roots/modifiers/instance/staff/StaffModifierInstance;isApplied()Z", remap = false), remap = true)
    private boolean utBlockDisabledModifiers(boolean original, @Local StaffModifierInstance info)
    {
        return original || info.isDisabled();
    }
}
