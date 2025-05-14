package mod.acgaming.universaltweaks.mods.randomthings.teleport.mixin;

import net.minecraft.network.NetHandlerPlayServer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import lumien.randomthings.handler.spectre.SpectreHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = SpectreHandler.class, remap = false)
public class UTSpectreHandlerMixin
{
    /**
     * @author WaitingIdly
     * @reason When a player teleports to an already existing Spectre cube in the Spectre dimension on servers,
     * the player might stall out in the void unless they are in creative (which skips this code path).
     * Setting their location directly avoids this issue.
     */
    @WrapOperation(method = "checkPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetHandlerPlayServer;setPlayerLocation(DDDFF)V"))
    private void fixSpectreTeleport(NetHandlerPlayServer instance, double x, double y, double z, float yaw, float pitch, Operation<Void> original)
    {
        if (UTConfigMods.RANDOM_THINGS.utTeleportStall)
        {
            original.call(instance, x, y, z, yaw, pitch);
        }
        else
        {
            instance.player.setLocationAndAngles(x, y, z, yaw, pitch);
        }
    }
}
