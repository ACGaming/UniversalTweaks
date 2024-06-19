package mod.acgaming.universaltweaks.mods.codechickenlib.mixin;

import codechicken.lib.packet.PacketCustom;
import net.minecraft.network.INetHandler;
import org.apache.commons.lang3.Validate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = PacketCustom.ServerInboundHandler.class, remap = false)
public class UTPacketCustomServerMixin
{
    /**
     * This releases the COPIED ByteBuf that was created and assigned to PacketCustom.buf in the ctor.
     * @reason Release wrapped ByteBuf after whatever mod has handled the packet.
     */
    @Inject(method = "handle", at = @At(value = "INVOKE", target = "Lcodechicken/lib/packet/ICustomPacketHandler$IServerPacketHandler;handlePacket(Lcodechicken/lib/packet/PacketCustom;Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/network/play/INetHandlerPlayServer;)V", shift = At.Shift.AFTER))
    private void utReleaseServerBuf(INetHandler netHandler, String channel, PacketCustom packet, CallbackInfo ci)
    {
        Validate.isTrue(packet.refCnt() == 1);
        packet.release();
    }
}
