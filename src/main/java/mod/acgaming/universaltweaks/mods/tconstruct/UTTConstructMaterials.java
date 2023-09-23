package mod.acgaming.universaltweaks.mods.tconstruct;

import java.util.Arrays;
import java.util.Collection;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.mods.tconstruct.mixin.MaterialAccessor;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

public class UTTConstructMaterials
{
    public static void utHandleBlacklistedMaterials()
    {
        Collection<Material> materialList = TinkerRegistry.getAllMaterials();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.info("+++ TINKERS' CONSTRUCT MATERIALS +++");
        for (Material material : materialList)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.info(material.getIdentifier());
            boolean blacklisted = Arrays.stream(UTConfigMods.TINKERS_CONSTRUCT.utTConMaterialBlacklist).anyMatch(mat -> mat.equals(material.getIdentifier()));
            ((MaterialAccessor) material).setHidden(blacklisted);
        }
    }
}