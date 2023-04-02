package mod.acgaming.universaltweaks.tweaks.performance.fps.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of shedaniel
@Mixin(Minecraft.class)
public class UTUncapFPSMixin
{
    @Shadow
    public GameSettings gameSettings;

    @Inject(method = "getLimitFramerate", at = @At("HEAD"), cancellable = true)
    public void utUncapFPS(CallbackInfoReturnable<Integer> info)
    {
        if (UTConfig.TWEAKS_PERFORMANCE.utUncapFPSToggle) info.setReturnValue(this.gameSettings.limitFramerate);
    }
}