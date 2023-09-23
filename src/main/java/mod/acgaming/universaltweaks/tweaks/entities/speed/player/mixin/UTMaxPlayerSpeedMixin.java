package mod.acgaming.universaltweaks.tweaks.entities.speed.player.mixin;

import net.minecraft.network.NetHandlerPlayServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NetHandlerPlayServer.class)
public class UTMaxPlayerSpeedMixin
{
    @ModifyConstant(method = "processPlayer", constant = @Constant(floatValue = 100.0F))
    public float utModifyMaxSpeed(float speed)
    {
        return (float) UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerMaxSpeed;
    }

    @ModifyConstant(method = "processPlayer", constant = @Constant(floatValue = 300.0F))
    public float utModifyMaxElytraSpeed(float speed)
    {
        return (float) UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerMaxElytraSpeed;
    }

    @ModifyConstant(method = "processVehicleMove", constant = @Constant(doubleValue = 100.0D))
    public double utModifyMaxVehicleSpeed(double speed)
    {
        return UTConfigTweaks.ENTITIES.PLAYER_SPEED.utPlayerVehicleSpeed;
    }
}