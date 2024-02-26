package mod.acgaming.universaltweaks.tweaks.misc.advancements.mixin;

import net.minecraft.advancements.AdvancementManager;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of fonnymunkey
@Mixin(AdvancementManager.class)
public abstract class UTAdvancementManagerMixin
{
    @Inject(method = "reload", at = @At("HEAD"), cancellable = true)
    public void utAdvancementManagerReload(CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utDisableAdvancementsToggle) ci.cancel();
    }
}