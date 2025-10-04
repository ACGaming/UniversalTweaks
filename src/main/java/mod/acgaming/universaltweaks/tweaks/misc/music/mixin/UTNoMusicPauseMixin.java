package mod.acgaming.universaltweaks.tweaks.misc.music.mixin;

import java.util.Map;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.SoundCategory;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of Apis035, jchung01
@Mixin(SoundManager.class)
public class UTNoMusicPauseMixin
{
    @ModifyExpressionValue(method = "pauseAllSounds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager;isSoundPlaying(Lnet/minecraft/client/audio/ISound;)Z"))
    private boolean utKeepPlayingMusic(boolean shouldPause, @Local Map.Entry<String, ISound> entry)
    {
        return shouldPause && !(UTConfigTweaks.MISC.MUSIC.utKeepMusicOnPause && entry.getValue().getCategory() == SoundCategory.MUSIC);
    }
}