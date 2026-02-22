package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import divinerpg.capabilities.armor.ArmorPowers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = ArmorPowers.class, remap = false)
public abstract class UTArmorPowersMixin
{
    /**
     * Don't register the event handler, as it has all sorts of issues.
     * @see mod.acgaming.universaltweaks.mods.divinerpg.armorset.ArmorPowerHandler#copyArmorPowers(PlayerEvent.Clone) for the fixed handler
      */
    @WrapWithCondition(method = "<init>(Lnet/minecraft/entity/EntityLivingBase;)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;register(Ljava/lang/Object;)V"))
    private boolean ut$cancelRegister(EventBus instance, Object eventType)
    {
        return false;
    }

    /**
     * @see mod.acgaming.universaltweaks.mods.divinerpg.armorset.ArmorPowerHandler#copyArmorPowers(PlayerEvent.Clone) for the fixed handler
     */
    @WrapWithCondition(method = "unsubscribe", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fml/common/eventhandler/EventBus;unregister(Ljava/lang/Object;)V"))
    private boolean ut$cancelUnregister(EventBus instance, Object o)
    {
        return false;
    }
}
