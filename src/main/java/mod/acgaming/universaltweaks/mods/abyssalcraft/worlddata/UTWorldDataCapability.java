package mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.CapabilityManager;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

// Courtesy of jchung01

/**
 * Based off of Pneumaticraft's SemiblockManager and Abyssalcraft's ItemTransferCapability,
 * Per-world manager of tile entities configured for AbyssalCraft's item transfer system.
 */
public class UTWorldDataCapability implements IUTWorldDataCapability
{
    public static void register()
    {
        CapabilityManager.INSTANCE.register(IUTWorldDataCapability.class, UTWorldDataCapabilityStorage.INSTANCE, UTWorldDataCapability::new);
    }

    public static IUTWorldDataCapability getCap(World world)
    {
        return world.getCapability(UTWorldDataCapabilityProvider.WORLD_DATA_CAP, null);
    }

    /**
     * Holds map of configured tile entities' positions per (loaded) chunk.
     * Existing configurations before tweak was enabled must be reconfigured using the "Spirit Tablet".
     */
    private final Map<Chunk, Map<BlockPos, TileEntity>> configuredTileEntities;

    public UTWorldDataCapability()
    {
        configuredTileEntities = new Object2ObjectOpenHashMap<>();
    }

    @Override
    public boolean isEmpty()
    {
        return configuredTileEntities.isEmpty();
    }

    @Override
    public boolean contains(Chunk chunk)
    {
        return configuredTileEntities.containsKey(chunk);
    }

    @Override
    public Map<BlockPos, TileEntity> getOrCreateMap(Chunk chunk)
    {
        Map<BlockPos, TileEntity> positions = configuredTileEntities.get(chunk);
        if (positions == null)
        {
            positions = new Object2ObjectOpenHashMap<>();
            configuredTileEntities.put(chunk, positions);
        }
        return positions;
    }

    @Override
    public Map<BlockPos, TileEntity> getChunkMap(Chunk chunk)
    {
        return configuredTileEntities.get(chunk);
    }

    @Override
    public Map<BlockPos, TileEntity> getFlattenedView()
    {
        Map<BlockPos, TileEntity> flattenedMap = new Object2ObjectOpenHashMap<>();
        for (Map<BlockPos, TileEntity> map : configuredTileEntities.values())
        {
            flattenedMap.putAll(map);
        }
        return flattenedMap;
    }

    @Override
    public void addConfigured(Chunk chunk, BlockPos pos, TileEntity te)
    {
        getOrCreateMap(chunk).putIfAbsent(pos, te);
    }

    @Override
    public void updateConfigured(Chunk chunk, BlockPos pos, TileEntity te)
    {
        getOrCreateMap(chunk).put(pos, te);
    }

    @Override
    public void removeConfigured(Chunk chunk, BlockPos pos)
    {
        Map<BlockPos, TileEntity> map = getOrCreateMap(chunk);
        map.remove(pos);
        // Remove chunk entry so configuredTileEntities can check isEmpty() easily
        if (map.isEmpty()) removeChunk(chunk);
    }

    @Override
    public void removeChunk(Chunk chunk)
    {
        configuredTileEntities.remove(chunk);
    }
}