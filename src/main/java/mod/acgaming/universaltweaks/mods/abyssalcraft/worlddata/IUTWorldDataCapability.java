package mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata;

import java.util.Map;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

/**
 * Interface for World Data Capability
 *
 * @author jchung01
 */
public interface IUTWorldDataCapability
{
    /**
     * @return true if the map of chunks with configured tile entities is empty;
     * false otherwise.
     */
    boolean isEmpty();

    /**
     * @param chunk the chunk to check
     * @return true if the chunk is in the map;
     * false otherwise.
     */
    boolean contains(Chunk chunk);

    /**
     * Gets the map of tile entities for this chunk,
     * creating a new one if it doesn't exist.
     *
     * @param chunk the chunk the map corresponds to
     * @return the map of configured tile entities in this chunk
     */
    Map<BlockPos, TileEntity> getOrCreateMap(Chunk chunk);

    /**
     * Gets the map of tile entities for this chunk.
     *
     * @param chunk the chunk the map corresponds to
     * @return the map of configured tile entities in this chunk
     */
    Map<BlockPos, TileEntity> getChunkMap(Chunk chunk);

    /**
     * Gets a flattened Map view of all tile entities loaded in the world.
     *
     * @return the map of loaded, configured tile entities in the world
     */
    Map<BlockPos, TileEntity> getFlattenedView();

    /**
     * Adds the configured tile entity to the chunk's map if it doesn't exist.
     *
     * @param chunk the chunk the tile entity is in
     * @param pos   the position of the tile entity
     * @param te    the configured tile entity to add
     */
    void addConfigured(Chunk chunk, BlockPos pos, TileEntity te);

    /**
     * Updates the configured tile entity in the chunk's map.
     *
     * @param chunk the chunk the tile entity is in
     * @param pos   the position of the tile entity
     * @param te    the configured tile entity to update
     */
    void updateConfigured(Chunk chunk, BlockPos pos, TileEntity te);

    /**
     * Removes the configured tile entity from the chunk's map.
     *
     * @param chunk the chunk the tile entity is in
     * @param pos   the position of the tile entity
     */
    void removeConfigured(Chunk chunk, BlockPos pos);

    /**
     * Removes the chunk entry from the map of chunks with configured tile entities
     *
     * @param chunk the chunk entry
     */
    void removeChunk(Chunk chunk);
}