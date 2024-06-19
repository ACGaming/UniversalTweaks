package mod.acgaming.universaltweaks.mods.codechickenlib.mixin;

import codechicken.lib.packet.PacketCustom;
import io.netty.channel.ChannelHandlerContext;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = PacketCustom.CustomInboundHandler.class, remap = false)
public class UTPacketCustomReleaseMixin
{
    /**
     * This releases the ORIGINAL FMLProxyPacket payload that was passed into and copied by PacketCustom's constructor.
     * <p>
     * For S->C packets, some are reused and sent to multiple clients, so those packets are retained in {@link UTPacketCustomRetainMixin}.
     * @reason Release the message's payload after everything has been handled.
     */
    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraftforge/fml/common/network/internal/FMLProxyPacket;)V", at = @At(value = "TAIL"))
    private void utReleasePayload(ChannelHandlerContext ctx, FMLProxyPacket msg, CallbackInfo ci)
    {
        msg.payload().release();
    }
}
