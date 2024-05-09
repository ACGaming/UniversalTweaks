package mod.acgaming.universaltweaks.bugfixes.entities.disconnectdupe;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// Courtesy of jchung01
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.SERVER)
public class UTDisconnectDupe
{
    @SubscribeEvent
    public static void utDisconnectDupe(ItemTossEvent event)
    {
        if (!UTConfigBugfixes.ENTITIES.utDisconnectDupeToggle || event.getPlayer().world.isRemote) return;
        EntityPlayerMP player = (EntityPlayerMP) event.getPlayer();
        if (!player.connection.getNetworkManager().channel().isOpen())
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDisconnectDupe ::: Player dropped item but is disconnected! Ignoring drop.");
            event.setCanceled(true);
        }
    }
}