package mod.acgaming.universaltweaks.mods.openblocks.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.mods.openblocks.WrappedLivingHurtEvent;
import openblocks.enchantments.LastStandEnchantmentsHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = LastStandEnchantmentsHandler.class, remap = false, priority = 1010)
public abstract class UTLastStandEnchantmentsHandlerMixin
{
    @Shadow
    public abstract void onHurt(LivingHurtEvent e);

    /**
     * Repurpose onHurt as a passthrough method for {@link this#ut$fixLastStand}
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

    /**
     * Last Stand in 1.12 uses LivingHurtEvent, which is the pre-mitigation damage (before armor, potions, etc).
     * It should use LivingDamageEvent, which is the post-mitigation damage.
     * This was actually explicitly fixed in older versions (1.10) but once again uses the wrong event in 1.12.
     */
    @Unique
    @SubscribeEvent
    public void ut$fixLastStand(LivingDamageEvent event)
    {
        // This is a double check, to avoid extra allocations.
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            WrappedLivingHurtEvent eventIn = new WrappedLivingHurtEvent(event.getEntityLiving(), event.getSource(), event.getAmount());
            this.onHurt(eventIn);
            event.setAmount(eventIn.getAmount());
            event.setCanceled(eventIn.isCanceled());
        }
    }
}
