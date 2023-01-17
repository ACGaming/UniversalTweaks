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
    public static boolean shouldDisplay = true;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utDisplayObsoleteModsScreen(GuiOpenEvent event)
    {
        if (shouldDisplay
            && event.getGui() instanceof GuiMainMenu
            && UTObsoleteModsHandler.obsoleteModsMessage().size() > 5
            && UTConfig.DEBUG.utObsoleteModsToggle
            && !UTConfig.DEBUG.utBypassIncompatibilityToggle)
        {
            event.setGui(new UTObsoleteModsScreen(UTObsoleteModsHandler.obsoleteModsMessage()));
            shouldDisplay = false;
        }
    }
}