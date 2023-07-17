package mod.acgaming.universaltweaks.tweaks.world.sealevel.mixin;

import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class UTSeaLevelWorldMixin
{
    @Shadow
    private int seaLevel;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void utSeaLevel(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client, CallbackInfo ci)
    {
        seaLevel = UTConfig.TWEAKS_WORLD.utSeaLevel;
    }
}