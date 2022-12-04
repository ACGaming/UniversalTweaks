package mod.acgaming.universaltweaks.bugfixes.entitydesync.mixin;

import net.minecraft.entity.Entity;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.entitydesync.IPrevMotion;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Meldexun
@Mixin(Entity.class)
public class UTEntityMixin implements IPrevMotion
{
    @Shadow
    public double motionX;
    @Shadow
    public double motionY;
    @Shadow
    public double motionZ;
    @Unique
    private double prevMotionX;
    @Unique
    private double prevMotionY;
    @Unique
    private double prevMotionZ;

    @Inject(method = "onUpdate", at = @At("HEAD"))
    public void utOnUpdate(CallbackInfo info)
    {
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityMixin ::: Motion update");
        prevMotionX = motionX;
        prevMotionY = motionY;
        prevMotionZ = motionZ;
    }

    @Override
    public double getPrevMotionX()
    {
        return prevMotionX;
    }

    @Override
    public void setPrevMotionX(double prevMotionX)
    {
        this.prevMotionX = prevMotionX;
    }

    @Override
    public double getPrevMotionY()
    {
        return prevMotionY;
    }

    @Override
    public void setPrevMotionY(double prevMotionY)
    {
        this.prevMotionY = prevMotionY;
    }

    @Override
    public double getPrevMotionZ()
    {
        return prevMotionZ;
    }

    @Override
    public void setPrevMotionZ(double prevMotionZ)
    {
        this.prevMotionZ = prevMotionZ;
    }
}