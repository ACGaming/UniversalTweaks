package mod.acgaming.universaltweaks.mods.cofhcore.mixin;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

import cofh.core.enchantment.EnchantmentMultishot;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantmentMultishot.class)
public abstract class UTCoFHMultishotApplyMixin
{
    @Shadow(remap = false)
    public static boolean enable;

    @Inject(method = "canApply", at = @At("HEAD"), cancellable = true)
    public void utCoFHMultishotCanApply(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigMods.COFH_CORE.utCoFHMultishotToggle && enable && stack.getItem() instanceof ItemBow) cir.setReturnValue(true);
    }
}
