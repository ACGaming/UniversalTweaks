package mod.acgaming.universaltweaks.bugfixes.misc.packetsize.mixin;

import net.minecraft.network.play.server.SPacketCustomPayload;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(SPacketCustomPayload.class)
public class UTSPacketCustomPayloadMixin
{
    @ModifyConstant(method = "<init>(Ljava/lang/String;Lnet/minecraft/network/PacketBuffer;)V", constant = @Constant(intValue = 1048576))
    public int utPacketSizeCustomPayloadInit(int constant)
    {
        return UTConfigBugfixes.MISC.utPacketSize;
    }

    @ModifyConstant(method = "readPacketData", constant = @Constant(intValue = 1048576))
    public int utPacketSizeCustomPayloadRead(int constant)
    {
        return UTConfigBugfixes.MISC.utPacketSize;
    }
}