package mod.acgaming.universaltweaks.mods.woot;

import java.util.List;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeChunkManager;

import ipsis.Woot;
import ipsis.woot.dimension.WootDimensionManager;
import ipsis.woot.util.DebugSetup;
import mod.acgaming.universaltweaks.UniversalTweaks;

public class UTWootTicketManager
{
    public static final ChunkPos CHUNK = new ChunkPos(WootDimensionManager.CHUNK_X, WootDimensionManager.CHUNK_Z);
    public static ForgeChunkManager.Ticket ticket;

    public static void forceChunk(World world, int boxId)
    {
        WorldServer worldWoot = Woot.wootDimensionManager.getWorldServer(world);
        if (ticket == null)
        {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "UTWootTicketManager:callback", "requesting a ticket");
            ticket = ForgeChunkManager.requestTicket(Woot.instance, worldWoot, ForgeChunkManager.Type.NORMAL);
            if (ticket == null)
            {
                UniversalTweaks.LOGGER.error("UTWootTicketManager ::: Could not get a ticket for Tartarus (Woot)! Please report to Universal Tweaks.");
                return;
            }
        }
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "UTWootTicketManager:callback", "forcing chunk for boxId " + boxId);
        ForgeChunkManager.forceChunk(ticket, CHUNK);
    }

    public static void releaseChunk(int boxId)
    {
        if (ticket == null) return;
        Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "UTWootTicketManager:callback", "trying to release chunk for boxId " + boxId);
        if (!((ITartarusCleaner) Woot.tartarusManager).ut$areBoxesInUse())
        {
            Woot.debugSetup.trace(DebugSetup.EnumDebugType.TARTARUS, "UTWootTicketManager:callback", "releasing ticket");
            ForgeChunkManager.releaseTicket(ticket);
            ticket = null;
        }
    }

    public static void init()
    {
        ForgeChunkManager.setForcedChunkLoadingCallback(Woot.instance, UTWootTicketManager::callback);
    }

    private static void callback(List<ForgeChunkManager.Ticket> tickets, World world)
    {
        int dim = world.provider.getDimension();
        if (dim != Woot.wootDimensionManager.getDimensionId()) return;
        // Sanity check
        if (tickets.size() > 1)
        {
            for (ForgeChunkManager.Ticket ticket : tickets)
            {
                ForgeChunkManager.releaseTicket(ticket);
            }
        }
        UTWootTicketManager.ticket = tickets.get(0);
        ((ITartarusCleaner) Woot.tartarusManager).ut$freeBoxes();
    }
}
