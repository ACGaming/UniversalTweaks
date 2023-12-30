package mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of MacTso
public class UTDimensionChangeEvents
{
    @SubscribeEvent
    public void utDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (!UTConfigBugfixes.ENTITIES.utDimensionChangeToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDimensionChangeEvents ::: Change dimension");
        EntityPlayer ep = event.player;
        if (ep instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) ep;
            player.addExperienceLevel(0);
        }
    }
}