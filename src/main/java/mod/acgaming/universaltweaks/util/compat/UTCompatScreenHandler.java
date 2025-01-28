package mod.acgaming.universaltweaks.util.compat;

import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTCompatScreenHandler
{
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utDisplayCompatScreens(GuiOpenEvent event)
    {
        if (event.getGui() instanceof GuiMainMenu && UTObsoleteModsHandler.hasObsoleteModsMessage())
        {
            event.setGui(new UTCompatScreen(UTObsoleteModsHandler.obsoleteModsMessage()));
            UTObsoleteModsHandler.setHasShownObsoleteMods(true);
        }
    }
}