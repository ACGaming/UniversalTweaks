package mod.acgaming.universaltweaks.tweaks.world.voidfog;

import java.util.Arrays;
import java.util.List;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public abstract class UTVoidFog
{
    public static boolean isEnabledForDimension(int dimension)
    {
        List<String> dimensionList = Arrays.asList(UTConfigTweaks.WORLD.VOID_FOG.utVoidFogDimensionList);
        DimensionType dimensionType = DimensionManager.getProviderType(dimension);
        String dimensionName = dimensionType == null ? null : dimensionType.getName();
        boolean isWhitelist = UTConfigTweaks.WORLD.VOID_FOG.utVoidFogDimensionListMode == UTConfigTweaks.EnumLists.WHITELIST;
        return isWhitelist == (dimensionList.contains(dimensionName) || dimensionList.contains(String.valueOf(dimension)));
    }
}
