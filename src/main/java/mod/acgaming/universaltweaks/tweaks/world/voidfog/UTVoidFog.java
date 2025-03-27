package mod.acgaming.universaltweaks.tweaks.world.voidfog;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public abstract class UTVoidFog
{
    private static final List<String> DIMENSION_LIST = new ArrayList<>();

    public static void initDimensionList()
    {
        DIMENSION_LIST.clear();
        DIMENSION_LIST.addAll(Arrays.asList(UTConfigTweaks.WORLD.VOID_FOG.utVoidFogDimensionList));
    }

    public static boolean isEnabledForDimension(int dimension)
    {
        DimensionType dimensionType = DimensionManager.getProviderType(dimension);
        String dimensionName = dimensionType == null ? null : dimensionType.getName();
        boolean isWhitelist = UTConfigTweaks.WORLD.VOID_FOG.utVoidFogDimensionListMode == UTConfigTweaks.EnumLists.WHITELIST;
        return isWhitelist == (DIMENSION_LIST.contains(dimensionName) || DIMENSION_LIST.contains(String.valueOf(dimension)));
    }
}
