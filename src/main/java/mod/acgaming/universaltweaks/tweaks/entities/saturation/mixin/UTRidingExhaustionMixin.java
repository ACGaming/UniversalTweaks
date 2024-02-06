package mod.acgaming.universaltweaks.tweaks.entities.saturation.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPlayer.class)
public class UTRidingExhaustionMixin
{
    @Redirect(method = "addMovementStat", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isRiding()Z"))
    public boolean utRidingExhaustion(EntityPlayer player)
    {
        return player.isRiding() || UTConfigTweaks.ENTITIES.utRidingExhaustionToggle;
    }
}