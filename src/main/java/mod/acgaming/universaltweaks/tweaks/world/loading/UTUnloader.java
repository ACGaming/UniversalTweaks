package mod.acgaming.universaltweaks.tweaks.world.loading;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.world.DimensionType;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

// Courtesy of Unnoen & tie
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTUnloader
{
    private static int tickCount = 0;

    @SubscribeEvent
    public static void utUnloader(TickEvent.ServerTickEvent event)
    {
        if (!UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderToggle || event.phase != TickEvent.Phase.END) return;
        tickCount++;
        if (tickCount < UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderInterval) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTUnloader ::: Server tick event");
        tickCount = 0;
        Integer[] dims = DimensionManager.getIDs();
        for (Integer id : dims) utHandleDimUnload(id);
    }

    @SuppressWarnings("ConstantConditions")
    public static void utHandleDimUnload(Integer id)
    {
        if (!DimensionManager.isDimensionRegistered(id)) return;

        DimensionType dimensionType = DimensionManager.getProviderType(id);
        String dimensionName = "";

        if (dimensionType != null) dimensionName = dimensionType.getName();

        for (String dimConfig : UTConfigTweaks.WORLD.DIMENSION_UNLOAD.utUnloaderBlacklist)
        {
            if (dimensionName.matches(dimConfig) || Integer.toString(id).matches(dimConfig)) return;
        }

        WorldServer worldServer = DimensionManager.getWorld(id);
        ChunkProviderServer chunkProviderServer = worldServer.getChunkProvider();

        if (!worldServer.provider.getDimensionType().shouldLoadSpawn()
            && ForgeChunkManager.getPersistentChunksFor(worldServer).isEmpty()
            && chunkProviderServer.getLoadedChunkCount() == 0
            && worldServer.playerEntities.isEmpty()
            && worldServer.loadedEntityList.isEmpty()
            && worldServer.loadedTileEntityList.isEmpty())
        {
            try
            {
                worldServer.saveAllChunks(true, null);
                UniversalTweaks.LOGGER.info("Saved all chunks from dimension " + dimensionName);
            }
            catch (MinecraftException e)
            {
                UniversalTweaks.LOGGER.error("Caught an exception while saving all chunks:", e);
            }
            finally
            {
                MinecraftForge.EVENT_BUS.post(new WorldEvent.Unload(worldServer));
                worldServer.flush();
                DimensionManager.setWorld(id, null, worldServer.getMinecraftServer());
                UniversalTweaks.LOGGER.info("Unloaded dimension " + dimensionName);
            }
        }
    }
}