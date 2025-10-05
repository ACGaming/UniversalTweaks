package mod.acgaming.universaltweaks.tweaks.misc.music.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker.MusicType;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of Apis035, jchung01
@Mixin(Minecraft.class)
public class UTAmbientMusicMixin
{
    @ModifyReturnValue(method = "getAmbientMusicType", at = @At(value = "RETURN"))
    private MusicType utGetAmbientMusicType(MusicType originalType)
    {
        switch (originalType)
        {
            case MENU:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlMenu.getMusicTickerType().get();
            case GAME:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlOverworld.getMusicTickerType().get();
            case CREATIVE:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlCreative.getMusicTickerType().get();
            case CREDITS:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlCredits.getMusicTickerType().get();
            case NETHER:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlNether.getMusicTickerType().get();
            case END_BOSS:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlEndBoss.getMusicTickerType().get();
            case END:
                return UTConfigTweaks.MISC.MUSIC.utMusicControlEnd.getMusicTickerType().get();
            default:
                return originalType;
        }
    }

}