package mod.acgaming.universaltweaks.tweaks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

import static mod.acgaming.universaltweaks.config.UTConfig.TweaksMiscCategory.LoadSoundsCategory.EnumSoundModes.*;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTLoadSound
{
    public static boolean played = false;
    public static Random random = new Random();
    public static List<SoundEvent> soundListMC = new ArrayList<>();
    public static List<SoundEvent> soundListWorld = new ArrayList<>();
    public static List<Float> pitchListMC = new ArrayList<>();
    public static List<Float> pitchListWorld = new ArrayList<>();

    public static void initLists()
    {
        soundListMC.clear();
        soundListWorld.clear();
        pitchListMC.clear();
        pitchListWorld.clear();
        for (String sound : UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMC)
        {
            String[] soundEntries = sound.split(";");
            SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundEntries[0]));
            if (soundEvent != null)
            {
                Float pitch = new Float(soundEntries[1]);
                soundListMC.add(soundEvent);
                pitchListMC.add(pitch);
            }
            else
            {
                UniversalTweaks.LOGGER.warn("Unable to find sound: {}", new ResourceLocation(soundEntries[0]));
            }
        }
        for (String sound : UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundWorld)
        {
            String[] soundEntries = sound.split(";");
            SoundEvent soundEvent = SoundEvent.REGISTRY.getObject(new ResourceLocation(soundEntries[0]));
            if (soundEvent != null)
            {
                Float pitch = new Float(soundEntries[1]);
                soundListWorld.add(soundEvent);
                pitchListWorld.add(pitch);
            }
            else
            {
                UniversalTweaks.LOGGER.warn("Unable to find sound: {}", new ResourceLocation(soundEntries[0]));
            }
        }
        UniversalTweaks.LOGGER.info("Load sound lists initialized");
    }

    public static void playRandomSoundMC()
    {
        if (soundListMC.size() < 1) return;
        int randomIndex = random.nextInt(soundListMC.size());
        SoundEvent soundEvent = soundListMC.get(randomIndex);
        Float pitch = pitchListMC.get(randomIndex);
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(soundEvent, pitch));
    }

    public static void playRandomSoundWorld()
    {
        if (soundListMC.size() < 1) return;
        int randomIndex = random.nextInt(soundListWorld.size());
        SoundEvent soundEvent = soundListWorld.get(randomIndex);
        Float pitch = pitchListWorld.get(randomIndex);
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(soundEvent, pitch));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utMenuSound(GuiOpenEvent event)
    {
        if (!played && event.getGui() instanceof GuiMainMenu)
        {
            if (UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT || UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT_AND_WORLD) playRandomSoundMC();
            played = true;
        }
    }

    @SubscribeEvent
    public static void utWorldSound(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityPlayerSP)
        {
            if (UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode == WORLD || UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode == MINECRAFT_AND_WORLD) playRandomSoundWorld();
        }
    }
}