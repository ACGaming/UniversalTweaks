package mod.acgaming.universaltweaks.tweaks.world.sealevel.mixin;

import net.minecraft.world.gen.ChunkGeneratorSettings;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkGeneratorSettings.Factory.class)
public class UTSeaLevelChunkGeneratorMixin
{
    @Shadow
    public int seaLevel;

    @Inject(method = "setDefaults", at = @At(value = "TAIL"))
    public void utSeaLevel(CallbackInfo ci)
    {
        seaLevel = UTConfigTweaks.WORLD.utSeaLevel;
    }
}