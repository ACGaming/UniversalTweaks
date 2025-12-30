package mod.acgaming.universaltweaks.mods.aetherlegacy.mixin;

import com.gildedgames.the_aether.player.PlayerAether;
import com.gildedgames.the_aether.player.PlayerAetherEvents;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = PlayerAetherEvents.class, remap = false)
public abstract class UTPlayerAetherEventsMixin
{
    /**
     * @author WaitingIdly
     * @reason Make sure the capture drops flag is enabled when dropping accessories,
     * as this is required for some interactions, such as with grave mods.
     */
    @WrapOperation(method = "onPlayerDrops", at = @At(value = "INVOKE", target = "Lcom/gildedgames/the_aether/player/PlayerAether;dropAccessories()V"))
    private void utCaptureAccessoryDrops(PlayerAether instance, Operation<Void> original)
    {
        instance.thePlayer.captureDrops = true;
        original.call(instance);
        instance.thePlayer.captureDrops = false;
    }
}
