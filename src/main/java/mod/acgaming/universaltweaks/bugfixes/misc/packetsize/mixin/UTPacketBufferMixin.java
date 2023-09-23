package mod.acgaming.universaltweaks.bugfixes.misc.packetsize.mixin;

import net.minecraft.network.PacketBuffer;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(PacketBuffer.class)
public class UTPacketBufferMixin
{
    @ModifyConstant(method = "readCompoundTag", constant = @Constant(longValue = 2097152L))
    public long utPacketSizeBuffer(long constant)
    {
        return UTConfigBugfixes.MISC.utPacketSize;
    }
}