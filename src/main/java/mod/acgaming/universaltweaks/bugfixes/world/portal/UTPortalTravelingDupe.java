package mod.acgaming.universaltweaks.bugfixes.world.portal;

import org.apache.logging.log4j.Level;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of fonnymunkey
public class UTPortalTravelingDupe
{
    @SubscribeEvent
    public static void utDimensionChangeEventPortalTravelingDupe(EntityTravelToDimensionEvent event)
    {
        if (event.getEntity().world.isRemote) return;
        if (event.getEntity() instanceof EntityLiving && !(event.getEntity() instanceof EntityPlayer))
        {
            EntityLiving entity = (EntityLiving) event.getEntity();

            if (entity.isDead || !entity.isEntityAlive() || entity.getHealth() <= 0)
            {
                event.setCanceled(true);
                if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.log(Level.WARN, "Possible attempted dupe at X: " + entity.posX + " Y: " + entity.posY + " Z: " + entity.posZ + " Entity Name: " + entity.getName());
            }
        }
    }
}