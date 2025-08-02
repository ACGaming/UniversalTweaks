package mod.acgaming.universaltweaks.tweaks.misc.lightning.damage;

import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTLightningItemDestruction
{
    @SubscribeEvent
    public static void onLightningStrike(EntityStruckByLightningEvent event)
    {
        if (event.getEntity() instanceof EntityItem)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLightningItemDestruction ::: Lightning strike event");
            event.setCanceled(true);
        }
    }
}