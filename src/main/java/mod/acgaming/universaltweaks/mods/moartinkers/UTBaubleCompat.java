package mod.acgaming.universaltweaks.mods.moartinkers;

import net.minecraftforge.fml.common.Loader;

public class UTBaubleCompat
{
    private static final boolean isBaublesLoaded = Loader.isModLoaded("baubles");

    public static boolean isBaublesLoaded()
    {
        return isBaublesLoaded;
    }
}
