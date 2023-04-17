package mod.acgaming.universaltweaks.mods.cqrepoured.mixin;

import net.minecraftforge.event.entity.living.LivingFallEvent;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import team.cqr.cqrepoured.event.EventsHandler;

@Mixin(value = EventsHandler.class, remap = false)
public class UTGoldenFeatherEventMixin
{
    @Inject(method = "onLivingFallEvent", at = @At("HEAD"), cancellable = true)
    private static void utGoldenFeatherEvent(LivingFallEvent event, CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.CHOCOLATE_QUEST.utCQRGoldenFeatherToggle) ci.cancel();
    }
}