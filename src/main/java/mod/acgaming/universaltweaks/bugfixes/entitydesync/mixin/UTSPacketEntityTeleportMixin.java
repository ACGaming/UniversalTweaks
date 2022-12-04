package mod.acgaming.universaltweaks.bugfixes.entitydesync.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.SPacketEntityTeleport;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of Meldexun
@Mixin(SPacketEntityTeleport.class)
public class UTSPacketEntityTeleportMixin
{
    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D"))
    public double utGetPosX(Entity entity)
    {
        return entity.prevPosX;
    }

    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D"))
    public double utGetPosY(Entity entity)
    {
        return entity.prevPosY;
    }

    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D"))
    public double utGetPosZ(Entity entity)
    {
        return entity.prevPosZ;
    }
}