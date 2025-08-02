package mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

// Courtesy of MacTso
public class UTDimensionChange
{
    @SubscribeEvent
    public static void utDimensionChangeXP(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.player instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            player.addExperienceLevel(0);
        }
    }
}
