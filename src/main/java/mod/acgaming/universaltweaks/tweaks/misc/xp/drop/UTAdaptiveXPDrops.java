package mod.acgaming.universaltweaks.tweaks.misc.xp.drop;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTAdaptiveXPDrops
{
    @SubscribeEvent
    public static void utAdaptiveXPDrops(LivingExperienceDropEvent event)
    {
        if (event.getEntityLiving() instanceof EntityPlayer) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAdaptiveXPDrops ::: Living experience drop event");
        event.setDroppedExperience(Math.max(1, (int) (event.getEntityLiving().getMaxHealth() * UTConfigTweaks.ENTITIES.utAdaptiveXPFactor)));
    }
}