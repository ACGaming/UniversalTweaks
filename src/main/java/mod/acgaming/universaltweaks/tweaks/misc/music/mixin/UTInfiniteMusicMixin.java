package mod.acgaming.universaltweaks.tweaks.misc.music.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MusicTicker.class)
public class UTInfiniteMusicMixin
{
    @Shadow
    @Final
    private Minecraft mc;

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/MusicTicker$MusicType;getMinDelay()I"))
    public int utInfiniteMusicMinDelay(MusicTicker.MusicType instance)
    {
        if (!UTConfigTweaks.MISC.MUSIC.utInfiniteMusicToggle) return this.mc.getAmbientMusicType().getMinDelay();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfiniteMusic ::: Set min delay");
        return 20;
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/MusicTicker$MusicType;getMaxDelay()I"))
    public int utInfiniteMusicMaxDelay(MusicTicker.MusicType instance)
    {
        if (!UTConfigTweaks.MISC.MUSIC.utInfiniteMusicToggle) return this.mc.getAmbientMusicType().getMaxDelay();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInfiniteMusic ::: Set max delay");
        return 20;
    }
}