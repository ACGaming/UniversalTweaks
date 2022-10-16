package mod.acgaming.hkntweaks.tweaks.mixin;

import net.minecraft.entity.Entity;

import mod.acgaming.hkntweaks.config.HkNTweaksConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class HkNFallDamageMixin
{
    @Shadow
    public float fallDistance;
    @Unique
    public float hknFallDistance;

    @Inject(method = "handleWaterMovement", at = @At("HEAD"))
    public void hknStoreFallDistance(CallbackInfoReturnable<Boolean> cir)
    {
        hknFallDistance = this.fallDistance;
    }

    @Inject(method = "handleWaterMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;extinguish()V"))
    public void hknLoadFallDistance(CallbackInfoReturnable<Boolean> cir)
    {
        this.fallDistance = hknFallDistance - HkNTweaksConfig.tweaks.hknFallDamageSubtrahend;
    }
}