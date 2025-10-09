package mod.acgaming.universaltweaks.tweaks.world.chunks.spawn.mixin;

import net.minecraft.world.WorldProviderSurface;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WorldProviderSurface.class)
public abstract class UTSpawnChunksLoadingMixin
{
    @Inject(method = "canDropChunk", at = @At("HEAD"), cancellable = true)
    public void utCanDropChunk(int x, int z, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksLoadingToggle) cir.setReturnValue(true);
    }
}
