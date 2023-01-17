package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.math.MathHelper;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class UTSkipCreditsMixin
{
    @Shadow
    private Minecraft client;

    @Inject(method = "handleChangeGameState", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;floor(F)I", shift = At.Shift.AFTER), cancellable = true)
    public void utSkipCredits(SPacketChangeGameState packetIn, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utSkipCreditsToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSkipCredits ::: Handle change game state");
        if (packetIn.getGameState() == 4 && MathHelper.floor(packetIn.getValue() + 0.5F) == 1) this.client.player.connection.sendPacket(new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
        ci.cancel();
    }
}