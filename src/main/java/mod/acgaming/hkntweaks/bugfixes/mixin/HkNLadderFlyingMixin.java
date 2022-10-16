package mod.acgaming.hkntweaks.bugfixes.mixin;

import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.ModifyExpressionValue;

// MC-12829
// https://bugs.mojang.com/browse/MC-12829
@Mixin(EntityLivingBase.class)
public class HkNLadderFlyingMixin
{
    @ModifyExpressionValue(method = "isOnLadder", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isSpectator()Z"))
    public boolean hknIsNotClimbing(boolean isSpectator)
    {
        return isSpectator;
    }
}