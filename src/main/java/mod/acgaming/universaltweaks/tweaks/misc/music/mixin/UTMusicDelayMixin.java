package mod.acgaming.universaltweaks.tweaks.misc.music.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MusicTicker.class)
public class UTMusicDelayMixin
{
    @Shadow
    @Final
    private Minecraft mc;

    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/MusicTicker$MusicType;getMinDelay()I"))
    public int utInfiniteMusicMinDelay(MusicTicker.MusicType instance, Operation<Integer> original)
    {
        if (!UTConfigTweaks.MISC.MUSIC.utCustomMusicDelay) return original.call(instance);
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfiniteMusic ::: Set min delay");
        return Math.max(20, Math.min(UTConfigTweaks.MISC.MUSIC.utMusicDelayMin, UTConfigTweaks.MISC.MUSIC.utMusicDelayMax) * 20 * 60);
    }

    @WrapOperation(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/MusicTicker$MusicType;getMaxDelay()I"))
    public int utInfiniteMusicMaxDelay(MusicTicker.MusicType instance, Operation<Integer> original)
    {
        if (!UTConfigTweaks.MISC.MUSIC.utCustomMusicDelay) return original.call(instance);
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfiniteMusic ::: Set max delay");
        return Math.max(20, UTConfigTweaks.MISC.MUSIC.utMusicDelayMax * 20 * 60);
    }
}