package mod.acgaming.universaltweaks.mods.opencomputers.mixin;

import java.io.InputStream;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import li.cil.oc.common.PacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of jchung01
@Mixin(value = PacketHandler.class, remap = false)
public class UTPacketHandlerMixin
{
    /**
     * @reason The original constructor doesn't release the ByteBuf when {@link InputStream#close()} is called.
     */
    @Redirect(method = "li$cil$oc$common$PacketHandler$$process", at = @At(value = "NEW", target = "(Lio/netty/buffer/ByteBuf;)Lio/netty/buffer/ByteBufInputStream;"))
    private ByteBufInputStream utReleasePayloadOnClose(ByteBuf buffer)
    {
        return new ByteBufInputStream(buffer, true);
    }
}
