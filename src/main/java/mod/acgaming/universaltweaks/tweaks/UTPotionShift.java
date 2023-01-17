package mod.acgaming.universaltweaks.tweaks;

import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTPotionShift
{
    @SubscribeEvent
    public static void utPotionShift(GuiScreenEvent.PotionShiftEvent event)
    {
        if (!UTConfig.TWEAKS_MISC.utPotionShiftToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPotionShift ::: Potion shift event");
        event.setCanceled(true);
    }
}