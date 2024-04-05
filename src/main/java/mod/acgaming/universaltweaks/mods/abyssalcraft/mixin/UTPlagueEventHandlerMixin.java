package mod.acgaming.universaltweaks.mods.abyssalcraft.mixin;

import com.shinoow.abyssalcraft.common.handlers.PlagueEventHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = PlagueEventHandler.class, remap = false)
public class UTPlagueEventHandlerMixin
{
    @Inject(method = "onDeath", at = @At(value = "HEAD"), cancellable = true)
    private void utOnDeath(LivingDeathEvent event, CallbackInfo ci)
    {
        if (!UTConfigMods.ABYSSALCRAFT.utDisablePlaguePotionClouds) return;
        ci.cancel();
    }
}