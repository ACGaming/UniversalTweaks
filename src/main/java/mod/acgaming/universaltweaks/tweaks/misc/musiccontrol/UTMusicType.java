package mod.acgaming.universaltweaks.tweaks.misc.musiccontrol;

import net.minecraft.client.audio.MusicTicker.MusicType;

public enum UTMusicType
{
    // When the mod is loading, MusicTicker.MusicType isn't initialized yet.
    // Otherwise we can use enumerated field instead of getting the value with method.
    MENU,
    OVERWORLD,
    CREATIVE,
    NETHER,
    END,
    END_BOSS,
    CREDITS;

    public MusicType getMusicTickerType()
    {
        switch (this)
        {
            default:
            case MENU: return MusicType.MENU;
            case OVERWORLD: return MusicType.GAME;
            case CREATIVE: return MusicType.CREATIVE;
            case NETHER: return MusicType.NETHER;
            case END: return MusicType.END;
            case END_BOSS: return MusicType.END_BOSS;
            case CREDITS: return MusicType.CREDITS;
        }
    }
}