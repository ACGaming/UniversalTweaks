package mod.acgaming.universaltweaks.mods.reskillable.mixin;

import codersafterdark.reskillable.base.ExperienceHelper;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ExperienceHelper.class, remap = false)
public class UTReskillableLinearXPMixin
{
    @Inject(method = "getExperienceForLevel", at = @At("HEAD"), cancellable = true)
    private static void utReskillableLinearXP(int level, CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfig.TWEAKS_MISC.utLinearXP < 1) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTReskillableLinearXP ::: Get experience for level");
        cir.setReturnValue(UTConfig.TWEAKS_MISC.utLinearXP);
    }
}