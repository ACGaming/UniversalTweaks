package mod.acgaming.universaltweaks.tweaks.misc.music;

import java.util.Objects;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.audio.MusicTicker.MusicType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum UTMusicType
{
    // MusicTicker.MusicType might not exist yet if this class is loaded during core-mod stage.
    // If so, use these enum members instead of getting the type.
    MENU,
    OVERWORLD,
    CREATIVE,
    NETHER,
    END,
    END_BOSS,
    CREDITS;

    // Must be lazy as MusicType may not exist yet.
    @Nullable
    @SideOnly(Side.CLIENT)
    private Supplier<MusicType> musicTypeSupplier;

    @SideOnly(Side.CLIENT)
    public void setMusicTypeSupplier(@Nonnull Supplier<MusicType> musicTypeSupplier)
    {
        this.musicTypeSupplier = musicTypeSupplier;
    }

    // Must call this instead of initializing in constructor to avoid early class-loading
    @SideOnly(Side.CLIENT)
    public static void init()
    {
        MENU.setMusicTypeSupplier(() -> MusicType.MENU);
        OVERWORLD.setMusicTypeSupplier(() -> MusicType.GAME);
        CREATIVE.setMusicTypeSupplier(() -> MusicType.CREATIVE);
        NETHER.setMusicTypeSupplier(() -> MusicType.NETHER);
        END.setMusicTypeSupplier(() -> MusicType.END);
        END_BOSS.setMusicTypeSupplier(() -> MusicType.END_BOSS);
        CREDITS.setMusicTypeSupplier(() -> MusicType.CREDITS);
    }

    @Nonnull
    @SideOnly(Side.CLIENT)
    public Supplier<MusicType> getMusicTickerType()
    {
        return Objects.requireNonNull(musicTypeSupplier);
    }
}