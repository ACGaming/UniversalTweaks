package mod.acgaming.universaltweaks.util;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTObsoleteModsScreenHandler
{
    public static boolean initialLaunch = true;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utDisplayObsoleteModsScreen(GuiOpenEvent event)
    {
        if (initialLaunch && event.getGui() instanceof GuiMainMenu && UTConfig.debug.utObsoleteModsToggle && !UTConfig.debug.utBypassIncompatibilityToggle)
        {
            event.setGui(new UTObsoleteModsScreen(UTObsoleteModsHandler.obsoleteModsMessage()));
            initialLaunch = false;
        }
    }
}