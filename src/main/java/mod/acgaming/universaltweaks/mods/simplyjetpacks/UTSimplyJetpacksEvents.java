package mod.acgaming.universaltweaks.mods.simplyjetpacks;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.mods.simplyjetpacks.network.message.MessageClientStatesReset;
import tonius.simplyjetpacks.handler.SyncHandler;
import tonius.simplyjetpacks.network.NetworkHandler;

// Courtesy of jchung01
public class UTSimplyJetpacksEvents
{
    // Can be a mixin, but defined here for organization.
    @SubscribeEvent
    public void onServerPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        NetworkHandler.instance.sendTo(new MessageClientStatesReset(), (EntityPlayerMP) event.player);
    }

    // Can be a mixin, but defined here for organization.
    @SubscribeEvent
    public void onClientPlayerLoggedOut(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
    {
        // Just clear the maps, the client-side ones should only track the client's player.
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSimplyJetpacksEvents ::: Logged out - clearing state maps");
        SyncHandler.clearAll();
    }

    /**
     * Handle respawns from deaths and returning from the End.
     *
     * @param event the PlayerEvent.Clone event
     */
    @SubscribeEvent
    public void onServerPlayerRespawn(net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
    {
        // Clearing the client's maps instead of removing entity-specific entry,
        // so event.getEntity() or using PlayerRespawnEvent is an option here.
        NetworkHandler.instance.sendTo(new MessageClientStatesReset(), (EntityPlayerMP) event.getOriginal());
    }
}
