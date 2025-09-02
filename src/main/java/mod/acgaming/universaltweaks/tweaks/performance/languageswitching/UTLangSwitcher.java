package mod.acgaming.universaltweaks.tweaks.performance.languageswitching;

import mod.acgaming.universaltweaks.core.Coremods;
import net.minecraft.client.Minecraft;
import net.optifine.Lang;

public class UTLangSwitcher {

    // Extracted here to avoid mixin class loading failure on optifine Lang.class
    public static void onLangSwitched()
    {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getLanguageManager().onResourceManagerReload(mc.getResourceManager());
        if (Coremods.OPTIFINE.isLoaded())
        {
            Lang.resourcesReloaded();
        }
    }
}
