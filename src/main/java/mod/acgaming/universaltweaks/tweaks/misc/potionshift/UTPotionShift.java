package mod.acgaming.universaltweaks.tweaks.misc.potionshift;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTPotionShift
{
    @SubscribeEvent
    public static void utPotionShift(GuiScreenEvent.PotionShiftEvent event)
    {
        if (!UTConfigTweaks.MISC.utPotionShiftToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPotionShift ::: Potion shift event");
        event.setCanceled(true);
    }
}