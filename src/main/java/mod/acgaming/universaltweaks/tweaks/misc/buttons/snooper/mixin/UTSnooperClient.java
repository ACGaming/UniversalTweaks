package mod.acgaming.universaltweaks.tweaks.misc.buttons.snooper.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class UTSnooperClient
{
    @Shadow
    public GameSettings gameSettings;

    @Inject(method = "isSnooperEnabled", at = @At("HEAD"), cancellable = true)
    public void utSnooperClient(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.MISC.utSnooperToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSnooperClient ::: Disable client snooper");
        this.gameSettings.snooperEnabled = false;
        cir.setReturnValue(false);
    }
}