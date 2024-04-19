package mod.acgaming.universaltweaks.tweaks.items.bowinfinity.infinityallarrows.mixin;

import net.minecraft.item.ItemArrow;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ItemArrow.class)
public abstract class UTItemArrowMixin
{
    @ModifyReturnValue(method = "isInfinite", at = @At("RETURN"), remap = false)
    public boolean utIsInfinite(boolean original, @Local LocalIntRef enchant)
    {
        if (!UTConfigTweaks.ITEMS.INFINITY.utAllArrowsAreInfinite) return original;
        return enchant.get() > 0;
    }
}
