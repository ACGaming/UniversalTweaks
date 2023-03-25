package mod.acgaming.universaltweaks.bugfixes.entities.desync.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityVelocity;

import mod.acgaming.universaltweaks.bugfixes.entities.desync.IPrevMotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of Meldexun
@Mixin(SPacketEntityVelocity.class)
public class UTSPacketEntityVelocityMixin
{
    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionX:D"))
    private static double utGetMotionX(Entity entity)
    {
        return entity instanceof EntityPlayer ? entity.motionX : ((IPrevMotion) entity).getPrevMotionX();
    }

    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionY:D"))
    private static double utGetMotionY(Entity entity)
    {
        return entity instanceof EntityPlayer ? entity.motionY : ((IPrevMotion) entity).getPrevMotionY();
    }

    @Redirect(method = "<init>(Lnet/minecraft/entity/Entity;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionZ:D"))
    private static double utGetMotionZ(Entity entity)
    {
        return entity instanceof EntityPlayer ? entity.motionZ : ((IPrevMotion) entity).getPrevMotionZ();
    }
}