package mod.acgaming.universaltweaks.tweaks.entities.trading.mixin;

import net.minecraft.village.MerchantRecipe;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MerchantRecipe.class)
public class UTMerchantRecipeMixin
{
    @Inject(method = "getMaxTradeUses", at = @At("HEAD"), cancellable = true)
    public void utGetMaxTradeUses(CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfigTweaks.ENTITIES.utVillagerTradeRestockToggle) cir.setReturnValue(1);
    }
}