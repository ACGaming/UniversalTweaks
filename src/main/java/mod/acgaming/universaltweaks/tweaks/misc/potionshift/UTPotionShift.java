package mod.acgaming.universaltweaks.tweaks.misc.potionshift;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTPotionShift
{
    @SubscribeEvent
    public static void utPotionShift(GuiScreenEvent.PotionShiftEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPotionShift ::: Potion shift event");
        event.setCanceled(true);
    }
}