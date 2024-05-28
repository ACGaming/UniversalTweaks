package mod.acgaming.universaltweaks.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.entities.playerdismount.UTDismountKeybind;
import mod.acgaming.universaltweaks.tweaks.misc.narrator.UTNarratorKeybind;
import mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.UTClearToastKeybind;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
@SideOnly(Side.CLIENT)
public class UTKeybindings extends KeyBinding
{
    private static final List<Key> keys = new ArrayList<>();

    public static void addKey(UTKeybindings.Key key)
    {
        keys.add(key);
    }

    public static void initialize()
    {
        UTClearToastKeybind.createKeybind();
        UTDismountKeybind.createKeybind();
        UTNarratorKeybind.createKeybind();

        for (Key key : keys)
        {
            key.getKey();
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent event)
    {
        if (!Minecraft.getMinecraft().inGameHasFocus) return;

        for (Key key : keys)
        {
            if (key.getKey().isPressed()) key.handleKeybind();
        }
    }

    protected UTKeybindings(UTKeybindings.Key key)
    {
        super(key.getDescription(), key.getKeyConflictContext(), key.getKeyModifier(), key.getKeyCode(), UniversalTweaks.NAME);
        ClientRegistry.registerKeyBinding(this);
    }

    public static abstract class Key
    {
        private final String name;
        private final IKeyConflictContext keyConflictContext;
        private final KeyModifier keyModifier;
        private final int keyCode;

        @SideOnly(Side.CLIENT)
        private KeyBinding key;

        public Key(String name, int keyCode)
        {
            this(name, KeyConflictContext.UNIVERSAL, keyCode);
        }

        public Key(String name, IKeyConflictContext keyConflictContext, int keyCode)
        {
            this(name, keyConflictContext, KeyModifier.NONE, keyCode);
        }

        public Key(String name, IKeyConflictContext keyConflictContext, KeyModifier keyModifier, int keyCode)
        {
            this.name = name;
            this.keyConflictContext = keyConflictContext;
            this.keyModifier = keyModifier;
            this.keyCode = keyCode;
        }

        @SideOnly(Side.CLIENT)
        public abstract void handleKeybind();

        public String getName()
        {
            return name;
        }

        public IKeyConflictContext getKeyConflictContext()
        {
            return keyConflictContext;
        }

        public KeyModifier getKeyModifier()
        {
            return keyModifier;
        }

        public int getKeyCode()
        {
            return keyCode;
        }

        @SideOnly(Side.CLIENT)
        public KeyBinding getKey()
        {
            if (key == null) key = new UTKeybindings(this);
            return key;
        }

        @SideOnly(Side.CLIENT)
        public void setKey(KeyBinding key)
        {
            this.key = key;
        }

        public String getDescription()
        {
            return String.format("keybind.%s.%s", UniversalTweaks.MODID, name);
        }
    }
}
