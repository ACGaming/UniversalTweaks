package mod.acgaming.universaltweaks.bugfixes.disconnectdupe;

import java.util.concurrent.FutureTask;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.disconnectdupe.mixin.PlayerListInvoker;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Meldexun
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTDisconnectDupe
{
    @SubscribeEvent
    public static void utDisconnectDupe(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (!UTConfig.bugfixes.utDisconnectDupeToggle || event.player.world.isRemote) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDisconnectDupe ::: Player logged out event");
        MinecraftServer server = event.player.getServer();
        if (server != null)
        {
            server.futureTaskQueue.add(new FutureTask<>(() -> {
                ((PlayerListInvoker) server.getPlayerList()).invokeWritePlayerData((EntityPlayerMP) event.player);
                return null;
            }));
        }
    }
}