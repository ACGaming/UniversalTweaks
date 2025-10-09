package mod.acgaming.universaltweaks.tweaks.world.chunks.spawn.mixin;

import net.minecraft.server.MinecraftServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of ZombieHDGaming
@Mixin(MinecraftServer.class)
public abstract class UTSpawnChunksGenMixin
{
    @Inject(method = "initialWorldChunkLoad", at = @At("HEAD"), cancellable = true)
    public void utInitialChunkLoad(CallbackInfo ci)
    {
        if (!UTConfigTweaks.WORLD.SPAWN_CHUNKS.utSpawnChunksGenToggle) ci.cancel();
    }
}
