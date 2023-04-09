package mod.acgaming.universaltweaks.bugfixes.entities.desync.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayerMP;

import mod.acgaming.universaltweaks.bugfixes.entities.desync.IPrevMotion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Meldexun
@Mixin(EntityTrackerEntry.class)
public class UTEntityTrackerEntryMixin
{
    @Inject(method = "<init>", at = @At("RETURN"))
    public void utInit(Entity entityIn, int rangeIn, int maxRangeIn, int updateFrequencyIn, boolean sendVelocityUpdatesIn, CallbackInfo info)
    {
        if (entityIn instanceof EntityMinecart) return;
        entityIn.prevPosX = entityIn.posX;
        entityIn.prevPosY = entityIn.posY;
        entityIn.prevPosZ = entityIn.posZ;
        ((IPrevMotion) entityIn).setPrevMotionX(entityIn.motionX);
        ((IPrevMotion) entityIn).setPrevMotionY(entityIn.motionY);
        ((IPrevMotion) entityIn).setPrevMotionZ(entityIn.motionZ);
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getDistanceSq(DDD)D"))
    public double utGetDistanceSq(Entity entity, double x, double y, double z)
    {
        if (entity instanceof EntityMinecart) return entity.getDistanceSq(x, y, z);
        double dx = x - entity.prevPosX;
        double dy = y - entity.prevPosY;
        double dz = z - entity.prevPosZ;
        return dx * dx + dy * dy + dz * dz;
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posX:D"))
    public double utGetPosX1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.posX : entity.prevPosX;
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posY:D"))
    public double utGetPosY1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.posY : entity.prevPosY;
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;posZ:D"))
    public double utGetPosZ1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.posZ : entity.prevPosZ;
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionX:D"))
    public double utGetMotionX1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionX : ((IPrevMotion) entity).getPrevMotionX();
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionY:D"))
    public double utGetMotionY1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionY : ((IPrevMotion) entity).getPrevMotionY();
    }

    @Redirect(method = "updatePlayerList", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionZ:D"))
    public double utGetMotionZ1(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionZ : ((IPrevMotion) entity).getPrevMotionZ();
    }

    @Redirect(method = "updatePlayerEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionX:D"))
    public double utGetMotionX2(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionX : ((IPrevMotion) entity).getPrevMotionX();
    }

    @Redirect(method = "updatePlayerEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionY:D"))
    public double utGetMotionY2(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionY : ((IPrevMotion) entity).getPrevMotionY();
    }

    @Redirect(method = "updatePlayerEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;motionZ:D"))
    public double utGetMotionZ2(Entity entity)
    {
        return entity instanceof EntityMinecart ? entity.motionZ : ((IPrevMotion) entity).getPrevMotionZ();
    }

    @Redirect(method = "isVisibleTo", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayerMP;posX:D"))
    public double utGetPosX2(EntityPlayerMP player)
    {
        return player.prevPosX;
    }

    @Redirect(method = "isVisibleTo", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/EntityPlayerMP;posZ:D"))
    public double utGetPosZ2(EntityPlayerMP player)
    {
        return player.prevPosZ;
    }
}