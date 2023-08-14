package mod.acgaming.universaltweaks.tweaks.world.sealevel.mixin;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.gen.ChunkGeneratorSettings$Factory")
public class UTSeaLevelChunkGeneratorMixin
{
    @Shadow
    public int seaLevel;

    @Inject(method = "setDefaults", at = @At(value = "TAIL"))
    public void utSeaLevel(CallbackInfo ci)
    {
        seaLevel = UTConfig.TWEAKS_WORLD.utSeaLevel;
    }
}