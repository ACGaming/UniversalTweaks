package mod.acgaming.universaltweaks.bugfixes.entities.dimensionchange;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

// Courtesy of MacTso
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTDimensionChange
{
    @SubscribeEvent
    public static void utDimensionChangeXP(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (UTConfigBugfixes.ENTITIES.utDimensionChangeToggle && event.player instanceof EntityPlayerMP)
        {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            player.addExperienceLevel(0);
        }
    }
}
