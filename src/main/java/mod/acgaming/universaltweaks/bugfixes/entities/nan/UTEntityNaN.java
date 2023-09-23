package mod.acgaming.universaltweaks.bugfixes.entities.nan;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTEntityNaN
{
    @SubscribeEvent
    public static void utEntityNaN(LivingEvent.LivingUpdateEvent event)
    {
        if (UTConfigBugfixes.ENTITIES.utEntityNaNToggle)
        {
            EntityLivingBase entity = event.getEntityLiving();
            if (Float.isNaN(entity.getHealth()))
            {
                if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityNaN ::: Fix entity health");
                entity.setHealth(entity.getMaxHealth());
            }
            if (Float.isNaN(entity.getAbsorptionAmount()))
            {
                if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityNaN ::: Fix entity absorption");
                entity.setAbsorptionAmount(0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utDamageNaN(LivingHurtEvent event)
    {
        if (UTConfigBugfixes.ENTITIES.utEntityNaNToggle && Float.isNaN(event.getAmount()))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEntityNaN ::: Deny invalid damage");
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
        }
    }
}