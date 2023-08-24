package mod.acgaming.universaltweaks.mods.collective.mixin;

import com.natamus.collective.functions.ExperienceFunctions;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceFunctions.class)
public class UTCollectiveLinearXPMixin
{
    @Inject(method = "getExperienceForLevel", at = @At("HEAD"), cancellable = true)
    private static void utCollectiveLinearXP(int level, CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfig.TWEAKS_MISC.utLinearXP < 1) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCollectiveLinearXP ::: Get experience for level");
        cir.setReturnValue(level * UTConfig.TWEAKS_MISC.utLinearXP);
    }
}