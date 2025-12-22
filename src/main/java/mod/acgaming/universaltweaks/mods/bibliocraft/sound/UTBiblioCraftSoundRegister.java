package mod.acgaming.universaltweaks.mods.bibliocraft.sound;

import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import jds.bibliocraft.CommonProxy;
import mod.acgaming.universaltweaks.mods.vanilla.mixin.UTSoundEventAccessor;

// Courtesy of WaitingIdly
public class UTBiblioCraftSoundRegister
{
    private static void add(IForgeRegistry<SoundEvent> reg, SoundEvent sound)
    {
        reg.register(sound.setRegistryName(((UTSoundEventAccessor) sound).getSoundName()));
    }

    /**
     * BiblioCraft just... forgets to register its sounds.
     * This works fine in singleplayer, but in multiplayer causes any noise generation to error.
     * Sounds are created in {@link CommonProxy} and *should* be registered in either
     * {@link jds.bibliocraft.BiblioCraft.RegisterTheThings} or perhaps {@link jds.bibliocraft.SoundLoader}.
     */
    @SubscribeEvent
    public void registerBiblioCraftSounds(RegistryEvent.Register<SoundEvent> event)
    {
        IForgeRegistry<SoundEvent> reg = event.getRegistry();
        add(reg, CommonProxy.SOUND_DING);
        add(reg, CommonProxy.SOUND_TYPEWRITER_ADDPAPER);
        add(reg, CommonProxy.SOUND_TYPEWRITER_TYPEING);
        add(reg, CommonProxy.SOUND_TYPEWRITER_ENDBELL);
        add(reg, CommonProxy.SOUND_TYPEWRITER_REMOVEBOOK);
        add(reg, CommonProxy.SOUND_TYPEWRITER_TYPESINGLE);
        add(reg, CommonProxy.SOUND_CLOCK_TICK);
        add(reg, CommonProxy.SOUND_CLOCK_TOCK);
        add(reg, CommonProxy.SOUND_CLOCK_CHIME);
        add(reg, CommonProxy.SOUND_CLOCK_WIND);
        add(reg, CommonProxy.SOUND_ITEM_HANDDRILL);
        add(reg, CommonProxy.SOUND_ITEM_SCREWGUN);
        add(reg, CommonProxy.SOUND_CASE_OPEN);
        add(reg, CommonProxy.SOUND_CASE_CLOSE);
        add(reg, CommonProxy.SOUND_TAPE_OPEN);
        add(reg, CommonProxy.SOUND_TAPE_CLOSE);
    }
}
