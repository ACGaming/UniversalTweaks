package mod.acgaming.universaltweaks.tweaks.misc.buttons.snooper.mixin;

import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class UTSnooperServer
{
    @Shadow
    private PropertyManager settings;

    @Inject(method = "isSnooperEnabled", at = @At("HEAD"), cancellable = true)
    public void utSnooperServer(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.MISC.utSnooperToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSnooperServer ::: Disable server snooper");
        this.settings.getBooleanProperty("snooper-enabled", false);
        this.settings.setProperty("snooper-enabled", false);
        cir.setReturnValue(false);
    }
}