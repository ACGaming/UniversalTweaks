package mod.acgaming.universaltweaks.tweaks.performance.connectionspeed.mixin;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.connectionspeed.UTConnectionPatch;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "net.minecraft.realms.RealmsConnect$1")
public class UTRealmsConnectMixin
{
    @WrapOperation(method = "run", at = @At(value = "INVOKE", target = "Ljava/net/InetAddress;getByName(Ljava/lang/String;)Ljava/net/InetAddress;"))
    private InetAddress utPatchInetAddress(String ip, Operation<InetAddress> original) throws UnknownHostException
    {
        if (!UTConfigTweaks.PERFORMANCE.utImproveServerConnectionSpeed) return original.call(ip);
        return UTConnectionPatch.patch(ip);
    }
}
