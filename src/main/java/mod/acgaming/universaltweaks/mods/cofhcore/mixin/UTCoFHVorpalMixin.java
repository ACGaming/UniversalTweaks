package mod.acgaming.universaltweaks.mods.cofhcore.mixin;

import net.minecraftforge.event.entity.living.LivingHurtEvent;

import cofh.core.proxy.EventHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EventHandler.class, remap = false)
public abstract class UTCoFHVorpalMixin
{
    @Redirect(method = "handleLivingHurtEvent", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/living/LivingHurtEvent;setAmount(F)V"))
    public void utCoFHVorpalDamage(LivingHurtEvent event, float amount)
    {
        event.setAmount(event.getAmount() * (float) UTConfigMods.COFH_CORE.utCoFHVorpalDamage);
    }
}
