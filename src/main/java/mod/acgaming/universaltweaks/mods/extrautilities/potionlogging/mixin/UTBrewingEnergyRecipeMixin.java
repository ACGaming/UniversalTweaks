package mod.acgaming.universaltweaks.mods.extrautilities.potionlogging.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.rwtema.extrautils2.utils.LogHelper;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(targets = "com.rwtema.extrautils2.machine.BrewingEnergyRecipe", remap = false)
public abstract class UTBrewingEnergyRecipeMixin
{
    @WrapOperation(method = "checkTypes", at = @At(value = "INVOKE", target = "Lcom/rwtema/extrautils2/utils/LogHelper;info(Ljava/lang/Object;[Ljava/lang/Object;)V"))
    private void utDowngradePotionLogging(Object info, Object[] info2, Operation<Void> original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utDowngradePotionLogging) original.call(info, info2);
        else LogHelper.fine(info, info2);
    }
}
