package mod.acgaming.universaltweaks.tweaks.world.chunks.gen.mixin;

import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.world.WorldServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.world.chunks.gen.UTChunkGenLimit;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of Barteks2x
@Mixin(PlayerChunkMap.class)
public class UTChunkGenLimitMixin
{
    @Shadow
    @Final
    private WorldServer world;

    @ModifyConstant(method = "tick", constant = @Constant(intValue = 49))
    public int utChunkLimit(int constant)
    {
        if (!UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle || !UTChunkGenLimit.isEnabledForDimension(this.world.provider.getDimension())) return constant;
        return UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitTicks - 1;
    }

    @ModifyConstant(method = "tick", constant = @Constant(longValue = 50000000L))
    public long utTimeLimit(long constant)
    {
        if (!UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitToggle || !UTChunkGenLimit.isEnabledForDimension(this.world.provider.getDimension())) return constant;
        return UTConfigTweaks.WORLD.CHUNK_GEN_LIMIT.utChunkGenLimitTime * 1000000L;
    }
}
