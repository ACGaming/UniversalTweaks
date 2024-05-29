package mod.acgaming.universaltweaks.mods.openblocks.mixin;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import mod.acgaming.universaltweaks.mods.openblocks.WrappedLivingHurtEvent;
import openblocks.enchantments.LastStandEnchantmentsHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = LastStandEnchantmentsHandler.class, remap = false)
public class UTLastStandEnchantmentsHandlerMixin
{
    /**
     * Repurpose onHurt as a passthrough method for {@link mod.acgaming.universaltweaks.mods.openblocks.UTOpenBlocksEvents#handleLastStand}
     * instead of an event subscriber.
     * Uses a wrapper for maximum compatibilty with other mods (e.g. DimHopper Tweaks) that may mixin into this method's logic.
     *
     * @reason Only handle player damage from LivingDamageEvent, not LivingHurtEvent
     */
    @Inject(method = "onHurt", at = @At(value = "HEAD"), cancellable = true)
    private void utCheckEvent(LivingHurtEvent event, CallbackInfo ci)
    {
         if (!(event instanceof WrappedLivingHurtEvent))
        {
            ci.cancel();
        }
    }
}
