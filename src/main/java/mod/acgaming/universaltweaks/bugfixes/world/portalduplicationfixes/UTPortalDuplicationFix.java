package mod.acgaming.universaltweaks.bugfixes.world.portalduplicationfixes;

import org.apache.logging.log4j.Level;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of fonnymunkey
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTPortalDuplicationFix
{
    @SubscribeEvent
    public static void dimensionChangeEvent(EntityTravelToDimensionEvent event)
    {
        if (event.getEntity().world.isRemote || !(UTConfigBugfixes.WORLD.utPortalDuplicationFixToggle)) return;
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