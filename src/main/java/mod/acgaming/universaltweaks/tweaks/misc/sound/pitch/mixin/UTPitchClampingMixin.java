package mod.acgaming.universaltweaks.tweaks.misc.sound.pitch.mixin;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundManager.class)
public abstract class UTPitchClampingMixin
{
    @Inject(method = "getClampedPitch", at = @At("RETURN"), cancellable = true)
    private void utReturnOriginalPitch(ISound sound, CallbackInfoReturnable<Float> cir)
    {
        if (UTConfigTweaks.MISC.utUnlimitedSoundPitchRange) cir.setReturnValue(sound.getPitch());
    }
}
