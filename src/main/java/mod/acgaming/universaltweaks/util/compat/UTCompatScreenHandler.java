package mod.acgaming.universaltweaks.util.compat;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTCompatScreenHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utDisplayCompatScreens(GuiOpenEvent event)
    {
        if (event.getGui() instanceof GuiMainMenu)
        {
            if (UTMixinBooterHandler.showMixinBooter && !UTConfig.DEBUG.utBypassIncompatibilityToggle)
            {
                event.setGui(new UTCompatScreen(UTMixinBooterHandler.mixinBooterMessage()));
                UTMixinBooterHandler.showMixinBooter = false;
            }
            else if (UTObsoleteModsHandler.showObsoleteMods && UTObsoleteModsHandler.obsoleteModsMessage().size() > 5 && !UTConfig.DEBUG.utBypassIncompatibilityToggle)
            {
                event.setGui(new UTCompatScreen(UTObsoleteModsHandler.obsoleteModsMessage()));
                UTObsoleteModsHandler.showObsoleteMods = false;
            }
        }
    }
}