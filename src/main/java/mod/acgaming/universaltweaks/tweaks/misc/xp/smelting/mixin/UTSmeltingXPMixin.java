package mod.acgaming.universaltweaks.tweaks.misc.xp.smelting.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceRecipes.class)
public class UTSmeltingXPMixin
{
    @Inject(method = "getSmeltingExperience", at = @At("HEAD"), cancellable = true)
    public void utSmeltingXP(ItemStack stack, CallbackInfoReturnable<Float> cir)
    {
        if (UTConfigTweaks.MISC.utSmeltingXPToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSmeltingXP ::: Get smelting experience");
            cir.setReturnValue(0.0F);
        }
    }
}