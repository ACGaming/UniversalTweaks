package mod.acgaming.universaltweaks.tweaks.misc.timeouts.mixin;

import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.text.ITextComponent;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.tweaks.misc.timeouts.UTTimeoutManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

// Courtesy of jchung01, TheRandomLabs (RandomPatches)
@Mixin(value = NetHandlerPlayServer.class)
public class UTServerReadTimeoutMixin
{
    // lastPingTime, not sure why this is MCP name
    @Shadow
    private long field_194402_f;

    @WrapOperation(method = "update",
        slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=keepAlive"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V")),
        at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetHandlerPlayServer;disconnect(Lnet/minecraft/util/text/ITextComponent;)V"))
    private void utKeepAliveUntilReadTimeout(NetHandlerPlayServer instance, ITextComponent textComponent, Operation<Void> original, @Local long currentTimeMillis)
    {
        if (currentTimeMillis - field_194402_f >= UTTimeoutManager.readTimeoutMillis)
        {
            original.call(instance, textComponent);
        }
    }
}
