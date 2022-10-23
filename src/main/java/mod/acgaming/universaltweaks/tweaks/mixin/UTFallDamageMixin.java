package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.Entity;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class UTFallDamageMixin
{
    @Shadow
    public float fallDistance;
    @Unique
    public float utFallDistance;

    @Inject(method = "handleWaterMovement", at = @At("HEAD"))
    public void utStoreFallDistance(CallbackInfoReturnable<Boolean> cir)
    {
        utFallDistance = this.fallDistance;
    }

    @Inject(method = "handleWaterMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;extinguish()V"))
    public void utLoadFallDistance(CallbackInfoReturnable<Boolean> cir)
    {
        this.fallDistance = utFallDistance - UTConfig.tweaks.utFallDamageValue;
    }
}