package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.player.PlayerCapabilities;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerCapabilities.class)
public class UTPlayerSpeedMixin
{
    @Redirect(method = "getFlySpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;flySpeed:F"))
    public float utModifyFlySpeed(PlayerCapabilities instance)
    {
        return (float) UTConfig.TWEAKS_ENTITIES.utPlayerFlySpeed;
    }

    @Redirect(method = "getWalkSpeed", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerCapabilities;walkSpeed:F"))
    public float utModifyWalkSpeed(PlayerCapabilities instance)
    {
        return (float) UTConfig.TWEAKS_ENTITIES.utPlayerWalkSpeed;
    }
}