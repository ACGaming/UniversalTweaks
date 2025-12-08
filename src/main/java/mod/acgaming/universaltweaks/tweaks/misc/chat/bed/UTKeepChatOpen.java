package mod.acgaming.universaltweaks.tweaks.misc.chat.bed;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.chat.bed.mixin.UTGuiChatAccessor;

// Courtesy of WaitingIdly
public class UTKeepChatOpen
{
    @SubscribeEvent
    public static void utKeepChatOpenOnWake(GuiOpenEvent event)
    {
        if (!UTConfigTweaks.MISC.CHAT.utKeepChatOpen) return;
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.currentScreen instanceof GuiSleepMP && event.getGui() == null)
        {
            String text = ((UTGuiChatAccessor) mc.currentScreen).getInputField().getText();
            if (!text.isEmpty())
            {
                event.setGui(new GuiChat(text));
            }
        }
    }
}