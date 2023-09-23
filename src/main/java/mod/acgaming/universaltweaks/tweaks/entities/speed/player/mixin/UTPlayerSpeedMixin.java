package mod.acgaming.universaltweaks.tweaks.entities.speed.player.mixin;

import net.minecraft.entity.player.PlayerCapabilities;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerCapabilities.class)
public class UTPlayerSpeedMixin
{
    @Inject(method = "getFlySpeed", at = @At(value = "RETURN"), cancellable = true)
    public void utModifyFlySpeed(CallbackInfoReturnable<Float> cir)
    {
        if (UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) cir.setReturnValue((float) UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerFlySpeed);
    }

    @Inject(method = "getWalkSpeed", at = @At(value = "RETURN"), cancellable = true)
    public void utModifyWalkSpeed(CallbackInfoReturnable<Float> cir)
    {
        if (UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerSpeedToggle) cir.setReturnValue((float) UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerWalkSpeed);
    }
}