package mod.acgaming.universaltweaks.tweaks.world.chunks.gen.mixin;

import net.minecraft.server.management.PlayerChunkMap;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of Barteks2x
@Mixin(PlayerChunkMap.class)
public class UTChunkGenLimitMixin
{
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 49))
    public int utChunkLimit(int constant)
    {
        if (!UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle) return constant;
        return UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitTicks - 1;
    }

    @ModifyConstant(method = "tick", constant = @Constant(longValue = 50000000L))
    public long utTimeLimit(long constant)
    {
        if (!UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle) return constant;
        return UTConfig.TWEAKS_WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitTime * 1000000L;
    }
}