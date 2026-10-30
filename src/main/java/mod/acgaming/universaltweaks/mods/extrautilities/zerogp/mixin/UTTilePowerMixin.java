package mod.acgaming.universaltweaks.mods.extrautilities.zerogp.mixin;

import com.rwtema.extrautils2.tile.TilePower;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = TilePower.class, remap = false)
public abstract class UTTilePowerMixin
{
    @ModifyVariable(method = "powerChanged", at = @At("HEAD"), argsOnly = true)
    private boolean utAllowZeroGPDuringOverload(boolean powered)
    {
        if (powered) return true;

        float power = ((TilePower) (Object) this).getPower();
        return Float.isNaN(power) || power == 0;
    }
}
