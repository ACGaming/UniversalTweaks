package mod.acgaming.universaltweaks.bugfixes.misc.packetsize.mixin;

import net.minecraft.network.play.client.CPacketCustomPayload;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CPacketCustomPayload.class)
public class UTCPacketCustomPayloadCommonMixin
{
    @ModifyConstant(method = "readPacketData", constant = @Constant(intValue = 32767))
    public int utPacketSizeCustomPayloadRead(int constant)
    {
        return UTConfigBugfixes.MISC.utPacketSize;
    }
}