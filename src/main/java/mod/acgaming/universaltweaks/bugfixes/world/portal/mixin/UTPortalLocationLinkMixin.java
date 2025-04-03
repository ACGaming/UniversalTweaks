package mod.acgaming.universaltweaks.bugfixes.world.portal.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.Teleporter;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.bugfixes.world.portal.UTPortalLocationLink;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Teleporter.class)
public abstract class UTPortalLocationLinkMixin
{
    /**
     * Injects into placeInExistingPortal to teleport the player back to their stored location.
     */
    @WrapWithCondition(method = "placeInExistingPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetHandlerPlayServer;setPlayerLocation(DDDFF)V"))
    private boolean utPlacePlayerInExistingPortal(NetHandlerPlayServer connection, double x, double y, double z, float yaw, float pitch)
    {
        double[] storedPortalCoords = UTPortalLocationLink.getStoredPortalCoords(connection.player);
        if (storedPortalCoords.length == 3)
        {
            connection.setPlayerLocation(storedPortalCoords[0], storedPortalCoords[1], storedPortalCoords[2], connection.player.rotationYaw, connection.player.rotationPitch);
            return false;
        }
        return true;
    }

    /**
     * Injects into placeInExistingPortal to teleport the entity back to their stored location.
     */
    @WrapWithCondition(method = "placeInExistingPortal", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setLocationAndAngles(DDDFF)V"))
    private boolean utPlaceEntityInExistingPortal(Entity entity, double x, double y, double z, float yaw, float pitch)
    {
        double[] storedPortalCoords = UTPortalLocationLink.getStoredPortalCoords(entity);
        if (storedPortalCoords.length == 3)
        {
            entity.setLocationAndAngles(storedPortalCoords[0], storedPortalCoords[1], storedPortalCoords[2], entity.rotationYaw, entity.rotationPitch);
            return false;
        }
        return true;
    }
}
