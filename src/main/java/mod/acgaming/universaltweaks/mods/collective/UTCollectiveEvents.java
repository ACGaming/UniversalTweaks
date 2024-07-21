package mod.acgaming.universaltweaks.mods.collective;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.natamus.collective.events.CollectiveEvents;

public class UTCollectiveEvents
{
    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event)
    {
        World world = event.getWorld();
        if (!world.isRemote)
        {
            WorldServer serverWorld = (WorldServer) world;
            CollectiveEvents.entitiesToRide.remove(serverWorld);
            CollectiveEvents.entitiesToSpawn.remove(serverWorld);
        }
    }
}
