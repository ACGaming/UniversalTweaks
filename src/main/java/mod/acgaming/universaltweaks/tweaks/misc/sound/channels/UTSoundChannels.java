package mod.acgaming.universaltweaks.tweaks.misc.sound.channels;

import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import paulscode.sound.SoundSystemConfig;

public class UTSoundChannels
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utSoundChannels(SoundSetupEvent event)
    {
        SoundSystemConfig.setNumberNormalChannels(UTConfigTweaks.MISC.SOUND_CHANNELS.utSoundChannelsNormal);
        SoundSystemConfig.setNumberStreamingChannels(UTConfigTweaks.MISC.SOUND_CHANNELS.utSoundChannelsStreaming);
    }
}
