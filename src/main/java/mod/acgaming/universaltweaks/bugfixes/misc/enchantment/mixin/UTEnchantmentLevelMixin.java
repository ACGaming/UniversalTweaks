package mod.acgaming.universaltweaks.bugfixes.misc.enchantment.mixin;

import net.minecraft.enchantment.Enchantment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class UTEnchantmentLevelMixin
{
    @Inject(method = "getTranslatedName", at = @At(value = "RETURN"), cancellable = true)
    public void utGetTranslatedName(int level, CallbackInfoReturnable<String> cir)
    {
        String text = cir.getReturnValue();
        if (text.contains("enchantment.level.")) cir.setReturnValue(text.replace("enchantment.level.", ""));
    }
}
