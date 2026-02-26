package mod.acgaming.universaltweaks.mods.open_glasses.mixin;

import net.minecraft.item.ItemStack;

import com.bymarcin.openglasses.OpenGlasses;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = OpenGlasses.class, remap = false)
public class UTOpenGlassesMixin
{
    /**
     * @author WaitingIdly
     * @reason Fix OpenGlasses returning null instead of {@link ItemStack#EMPTY} for methods that
     * are then immediately treated as not null in the same file.
     */
    @ModifyReturnValue(method = {"getGlassesStackBaubles", "getGlassesStackTechguns"}, at = @At(value = "RETURN", ordinal = 0))
    private static ItemStack utGetActualState(ItemStack original)
    {
        if (original == null) return ItemStack.EMPTY;
        return original;
    }
}
