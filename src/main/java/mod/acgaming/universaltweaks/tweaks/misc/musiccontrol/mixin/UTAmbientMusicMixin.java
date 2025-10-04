package mod.acgaming.universaltweaks.tweaks.misc.musiccontrol.mixin;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.musiccontrol.UTMusicType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class UTAmbientMusicMixin
{
    @Shadow
    public WorldClient world;

    @Shadow
    public EntityPlayerSP player;

    @Shadow
    @Nullable
    public GuiScreen currentScreen;

    @Shadow
    public GuiIngame ingameGUI;

    @Inject(method = "getAmbientMusicType", at = @At(value = "HEAD"), cancellable = true)
    public void getAmbientMusicType(CallbackInfoReturnable<MusicTicker.MusicType> cir)
    {
        UTMusicType musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlMenu;

        if (currentScreen instanceof GuiWinGame) musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlCredits;
        else if (player != null)
        {
            MusicTicker.MusicType worldMusicType = world.provider.getMusicType();
            if (worldMusicType != null)
            {
                cir.setReturnValue(worldMusicType);
                return;
            }
            else if (player.world.provider instanceof WorldProviderHell) musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlNether;
            else if (player.world.provider instanceof WorldProviderEnd)
            {
                if (ingameGUI.getBossOverlay().shouldPlayEndBossMusic()) musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlEndBoss;
                else musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlEnd;
            }
            else if (player.capabilities.isCreativeMode && player.capabilities.allowFlying) musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlCreative;
            else musicType = UTConfigTweaks.MISC.MUSIC_CONTROL.utMusicControlOverworld;
        }

        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMusicControl ::: Playing ambient music " + musicType);

        cir.setReturnValue(musicType.getMusicTickerType());
    }
}