package mod.acgaming.universaltweaks.tweaks.misc.music.mixin;

import java.util.Map.Entry;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundManager.class)
public class UTNoMusicPauseMixin
{
    private Entry<String, ISound> localEntry;

    @ModifyVariable(method = "pauseAllSounds", at = @At(value = "STORE"))
    private Entry<String, ISound> entryStore(Entry<String, ISound> entry)
    {
        this.localEntry = entry;
        return entry;
    }

    @ModifyVariable(method = "pauseAllSounds", at = @At(value = "STORE"))
    private boolean flagStore(boolean flag)
    {
        ISound value = this.localEntry.getValue();
        boolean isMusic = value.getCategory() == SoundCategory.MUSIC;
        boolean isPlaying = ((SoundManager) (Object)this).isSoundPlaying(value);
        if (UTConfigTweaks.MISC.MUSIC.utKeepMusicOnPause) return !isMusic && isPlaying;
        else return isPlaying;
    }
}