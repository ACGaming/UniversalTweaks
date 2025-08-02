package mod.acgaming.universaltweaks.tweaks.entities.spawning.rabbit;

import net.minecraft.entity.passive.EntityRabbit;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTRandomUtil;

public class UTRabbits
{
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utRabbits(EntityJoinWorldEvent event)
    {
        if (!(event.getEntity() instanceof EntityRabbit) || event.getWorld().isRemote) return;

        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRabbits ::: Rabbit Spawn Event");

        double toastChance = UTConfigTweaks.ENTITIES.utRabbitToastChance;
        double killerChance = UTConfigTweaks.ENTITIES.utRabbitKillerChance;
        if (toastChance <= 0.0D && killerChance <= 0.0D) return;

        EntityRabbit rabbit = (EntityRabbit) event.getEntity();

        if (UTRandomUtil.chance(toastChance))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRabbits ::: Rabbit Spawn Event Toast");
            rabbit.setCustomNameTag("Toast");
        }
        else if (UTRandomUtil.chance(killerChance))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRabbits ::: Rabbit Spawn Event Killer");
            rabbit.setRabbitType(99);
        }
    }
}