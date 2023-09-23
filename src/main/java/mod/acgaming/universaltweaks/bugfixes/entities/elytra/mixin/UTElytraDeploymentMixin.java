package mod.acgaming.universaltweaks.bugfixes.entities.elytra.mixin;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-111444
// https://bugs.mojang.com/browse/MC-111444
// Courtesy of masady
@Mixin(EntityPlayerSP.class)
public abstract class UTElytraDeploymentMixin extends AbstractClientPlayer
{
    public UTElytraDeploymentMixin(World worldIn, GameProfile playerProfile)
    {
        super(worldIn, playerProfile);
    }

    @Inject(method = "onLivingUpdate", at = @At(value = "INVOKE", ordinal = 0, shift = At.Shift.AFTER, target = "Lnet/minecraft/client/network/NetHandlerPlayClient;sendPacket(Lnet/minecraft/network/Packet;)V"))
    public void utElytraDeployment(CallbackInfo ci)
    {
        if (UTConfigBugfixes.ENTITIES.utElytraDeploymentLandingToggle && !this.isInWater())
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTElytraDeployment ::: Deploy elytra");
            this.setFlag(7, true);
        }
    }
}