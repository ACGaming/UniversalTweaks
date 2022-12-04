package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.ai.UTDiamondAtan2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of Charles445
@Mixin(Entity.class)
public class UTDamageTiltMixin
{
    @Inject(method = "setVelocity", at = @At("HEAD"))
    public void utDamageTilt(double x, double y, double z, CallbackInfo ci)
    {
        if (!UTConfig.tweaks.utDamageTiltToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDamageTilt ::: Tilt view");
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null)
        {
            float result = (float) (UTDiamondAtan2.atan2(player.motionZ - z, player.motionX - x) * (180 / Math.PI) - player.rotationYaw);
            if (Float.isFinite(result)) player.attackedAtYaw = result;
        }
    }
}