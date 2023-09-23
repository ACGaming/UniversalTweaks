package mod.acgaming.universaltweaks.tweaks.world.loading.mixin;

import net.minecraft.server.MinecraftServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of ZombieHDGaming
@Mixin(MinecraftServer.class)
public class UTInitialChunkLoadMixin
{
    @Inject(method = "initialWorldChunkLoad", at = @At("HEAD"), cancellable = true)
    public void utInitialChunkLoad(CallbackInfo ci)
    {
        if (UTConfigTweaks.PERFORMANCE.utWorldLoadingToggle) ci.cancel();
    }
}