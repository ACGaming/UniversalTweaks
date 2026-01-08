package mod.acgaming.universaltweaks.tweaks.entities.sleeping;

import net.minecraftforge.event.entity.player.SleepingTimeCheckEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTSleepingTime
{
    @SubscribeEvent
    public static void utSleepingTime(SleepingTimeCheckEvent event)
    {
        if (UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime < 0) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSleepingTime ::: Sleeping time check event");
        if (event.getEntityPlayer().getEntityWorld().getWorldTime() >= UTConfigTweaks.ENTITIES.SLEEPING.utSleepingTime) event.setResult(Event.Result.ALLOW);
        else event.setResult(Event.Result.DENY);
    }
}
