package mod.acgaming.universaltweaks.tweaks.misc.loadsound;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import static mod.acgaming.universaltweaks.config.UTConfigTweaks.MiscCategory.LoadSoundsCategory.EnumSoundModes.*;

public class UTLoadSound
{
    private static final Random random = new Random();
    private static final List<SoundEvent> soundListMC = new ArrayList<>();
    private static final List<SoundEvent> soundListWorld = new ArrayList<>();
    private static final List<Float> pitchListMC = new ArrayList<>();
    private static final List<Float> pitchListWorld = new ArrayList<>();
    private static final List<Float> volumeListMC = new ArrayList<>();
    private static final List<Float> volumeListWorld = new ArrayList<>();
    private static boolean playedMenu = false;
    private static boolean playedWorld = false;

    public static void initLists()
    {
        soundListMC.clear();
        soundListWorld.clear();
        pitchListMC.clear();
        pitchListWorld.clear();
        volumeListMC.clear();
        volumeListWorld.clear();

        for (String sound : UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMC)
        {
            String[] soundEntries = sound.split(";");
            SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundEntries[0]));
            if (soundEvent != null)
            {
                Float pitch = 1.0F;
                Float volume = 1.0F;
                try
                {
                    pitch = new Float(soundEntries[1]);
                    volume = new Float(soundEntries[2]);
                }
                catch (Exception e)
                {
                    UniversalTweaks.LOGGER.warn("Unable to parse sound properties for {}", new ResourceLocation(soundEntries[0]));
                }
                soundListMC.add(soundEvent);
                pitchListMC.add(pitch);
                volumeListMC.add(volume);
            }
            else
            {
                UniversalTweaks.LOGGER.warn("Unable to find sound: {}", new ResourceLocation(soundEntries[0]));
            }
        }

        for (String sound : UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundWorld)
        {
            String[] soundEntries = sound.split(";");
            SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundEntries[0]));
            if (soundEvent != null)
            {
                Float pitch = 1.0F;
                Float volume = 1.0F;
                try
                {
                    pitch = new Float(soundEntries[1]);
                    volume = new Float(soundEntries[2]);
                }
                catch (Exception e)
                {
                    UniversalTweaks.LOGGER.warn("Unable to parse sound properties for {}", new ResourceLocation(soundEntries[0]));
                }
                soundListWorld.add(soundEvent);
                pitchListWorld.add(pitch);
                volumeListWorld.add(volume);
            }
            else
            {
                UniversalTweaks.LOGGER.warn("Unable to find sound: {}", new ResourceLocation(soundEntries[0]));
            }
        }

        UniversalTweaks.LOGGER.info("Load Sound lists initialized");
    }

    public static void playRandomSoundMC()
    {
        if (soundListMC.isEmpty()) return;
        int randomIndex = random.nextInt(soundListMC.size());
        SoundEvent soundEvent = soundListMC.get(randomIndex);
        Float pitch = pitchListMC.get(randomIndex);
        Float volume = volumeListMC.get(randomIndex);
        Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(soundEvent.getSoundName(), SoundCategory.MASTER, volume, pitch, false, 0, ISound.AttenuationType.NONE, 0F, 0F, 0F));
    }

    public static void playRandomSoundWorld()
    {
        if (soundListMC.isEmpty()) return;
        int randomIndex = random.nextInt(soundListWorld.size());
        SoundEvent soundEvent = soundListWorld.get(randomIndex);
        Float pitch = pitchListWorld.get(randomIndex);
        Float volume = volumeListWorld.get(randomIndex);
        EntityPlayer player = Minecraft.getMinecraft().player;
        Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(soundEvent.getSoundName(), SoundCategory.MASTER, volume, pitch, false, 0, ISound.AttenuationType.NONE, (float) player.posX, (float) player.posY, (float) player.posZ));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utMenuSound(GuiOpenEvent event)
    {
        if (!playedMenu && event.getGui() instanceof GuiMainMenu)
        {
            if (UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT || UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT_AND_WORLD) playRandomSoundMC();
            playedMenu = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utWorldSound(EntityJoinWorldEvent event)
    {
        if (!playedWorld && event.getEntity() instanceof EntityPlayerSP)
        {
            if (UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode == WORLD || UTConfigTweaks.MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT_AND_WORLD) playRandomSoundWorld();
            playedWorld = true;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utWorldSoundReset(PlayerEvent.PlayerLoggedOutEvent event)
    {
        playedWorld = false;
    }
}