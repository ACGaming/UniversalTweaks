package mod.acgaming.universaltweaks.mods.abyssalcraft;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

// Intermediary class, based off of Pneumaticraft's SemiblockManager
public class UTItemTransferManager
{
    public static final UTItemTransferManager INSTANCE = new UTItemTransferManager();
    /*
     * Holds map of configured tile entities' positions per (loaded) chunk
     * Existing configurations before tweak was enabled need to be reconfigured
     */
    private final Map<Chunk, Map<BlockPos, TileEntity>> configuredTileEntities = new Object2ObjectOpenHashMap<>();

    public boolean isEmpty()
    {
        return configuredTileEntities.isEmpty();
    }

    public boolean contains(Chunk chunk)
    {
        return configuredTileEntities.containsKey(chunk);
    }

    // Get a map of tile entities for this chunk or create a new one if it doesn't exist
    private Map<BlockPos, TileEntity> getOrCreateMap(Chunk chunk)
    {
        Map<BlockPos, TileEntity> positions = configuredTileEntities.get(chunk);
        if (positions == null)
        {
            positions = new Object2ObjectOpenHashMap<>();
            configuredTileEntities.put(chunk, positions);
        }
        return positions;
    }

    public Map<BlockPos, TileEntity> getChunkMap(Chunk chunk)
    {
        return configuredTileEntities.get(chunk);
    }

    // Gets a flattened view of all (BlockPos, TileEntity)s loaded
    public Map<BlockPos, TileEntity> getFlattenedView()
    {
        Map<BlockPos, TileEntity> flattenedMap = new Object2ObjectOpenHashMap<>();
        for (Map<BlockPos, TileEntity> map : configuredTileEntities.values())
        {
            flattenedMap.putAll(map);
        }
        return flattenedMap;
    }

    // Add the configured tile entity to chunk's map (if it doesn't exist)
    public void addConfigured(Chunk chunk, BlockPos pos, TileEntity te)
    {
        getOrCreateMap(chunk).putIfAbsent(pos, te);
    }

    // Update the configured tile entity to chunk's map
    public void updateConfigured(Chunk chunk, BlockPos pos, TileEntity te)
    {
        getOrCreateMap(chunk).put(pos, te);
    }

    // Remove the configured tile entity's position from chunk's map
    public void removeConfigured(Chunk chunk, BlockPos pos)
    {
        Map<BlockPos, TileEntity> map = getOrCreateMap(chunk);
        map.remove(pos);
        // Remove chunk entry so configuredTileEntities can check isEmpty() easily
        if (map.isEmpty()) removeChunk(chunk);
    }

    // Remove chunk entry from parent map
    public void removeChunk(Chunk chunk)
    {
        configuredTileEntities.remove(chunk);
    }
}
