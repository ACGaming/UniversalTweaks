package mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties;

import mod.acgaming.universaltweaks.tweaks.misc.gui.lanserverproperties.mixin.GuiShareToLanAccessor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Courtesy of rikka0w0
public class UTLanServerProperties
{
    @SubscribeEvent
    public static void guiOpenEventHandler(GuiOpenEvent event)
    {
        GuiScreen guiScreen = event.getGui();
        if (guiScreen instanceof GuiShareToLan)
        {
            GuiScreen lastScreen = ((GuiShareToLanAccessor) guiScreen).getLastScreen();
            event.setGui(new UTGuiShareToLan(lastScreen));
        }
    }
}