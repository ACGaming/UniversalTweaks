package mod.acgaming.universaltweaks.tweaks.misc.console.addpacket.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of fonnymunkey
@Mixin(EntityTrackerEntry.class)
public abstract class UTEntityTrackerEntryMixin
{
    @Shadow @Final private Entity trackedEntity;

    @Redirect(method = "createSpawnPacket", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V", remap = false))
    public void utCreateSpawnPacket(Logger instance, String s)
    {
    	instance.warn(s + ", name: " + this.trackedEntity.getName() + " pos: {x:" + this.trackedEntity.posX + ",y:" + this.trackedEntity.posY + ",z:" + this.trackedEntity.posZ + "}");
    }
}