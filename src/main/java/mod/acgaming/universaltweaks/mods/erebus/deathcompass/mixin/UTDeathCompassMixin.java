package mod.acgaming.universaltweaks.mods.erebus.deathcompass.mixin;

import net.minecraftforge.event.entity.player.PlayerEvent;

import erebus.core.handler.DeathCompassRespawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DeathCompassRespawnEvent.class, remap = false)
public abstract class UTDeathCompassMixin
{
    @Inject(method = "onClonePlayer", at = @At(value = "HEAD"), cancellable = true)
    public void utErebusDeathCompass(PlayerEvent.Clone event, CallbackInfo ci)
    {
        ci.cancel();
    }
}
