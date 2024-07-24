package mod.acgaming.universaltweaks.bugfixes.misc.packetsize.mixin;

import net.minecraft.network.NettyCompressionDecoder;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(NettyCompressionDecoder.class)
public class UTNettyCompressionDecoderMixin
{
    @ModifyConstant(method = "decode", constant = @Constant(intValue = 2097152))
    public int utPacketSizeDecoder(int constant)
    {
        return UTConfigBugfixes.MISC.utPacketSize;
    }
}