package mod.acgaming.universaltweaks.core;

import zone.rong.mixinbooter.Context;

public enum Coremods
{
    CHUNKGEN_LIMITER("chunkgenlimit"),
    OPENMODS("openmods"),
    OPTIFINE("optifine"),
    RANDOM_PATCHES("randompatches"),
    RENDERLIB("renderlib"),
    SPONGEFORGE("spongeforge"),
    SURGE("surge"),
    ;

    private static boolean initialized = false;

    public final String modId;
    private boolean isLoaded;

    Coremods(String modId)
    {
        this.modId = modId;
        this.isLoaded = false;
    }

    public static void initFromContext(Context context)
    {
        if (initialized) return;
        initialized = true;
        for (Coremods coreMod : Coremods.values())
        {
            coreMod.isLoaded = context.isModPresent(coreMod.modId);
        }
    }

    public boolean isLoaded()
    {
        return this.isLoaded;
    }
}
