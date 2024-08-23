package mod.acgaming.universaltweaks.mods.compactmachines;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.Loader;

import io.netty.util.collection.LongObjectHashMap;
import io.netty.util.collection.LongObjectMap;

public class DummyWorld
{
    public static final WorldSettings DEFAULT_SETTINGS = new WorldSettings(1L, GameType.SURVIVAL, true, false, WorldType.DEFAULT);
    public static final boolean isAlfheimLoaded = Loader.isModLoaded("alfheim");

    public static class DummyChunkProvider implements IChunkProvider
    {
        private final World world;
        private final LongObjectMap<Chunk> loadedChunks = new LongObjectHashMap<>();

        public DummyChunkProvider(World world) {
            this.world = world;
        }

        @Nullable
        @Override
        public Chunk getLoadedChunk(int x, int z) {
            return loadedChunks.get(ChunkPos.asLong(x, z));
        }

        @Nonnull
        @Override
        public Chunk provideChunk(int x, int z) {
            long chunkKey = ChunkPos.asLong(x, z);
            if (loadedChunks.containsKey(chunkKey))
                return loadedChunks.get(chunkKey);
            Chunk chunk = new Chunk(world, x, z);
            loadedChunks.put(chunkKey, chunk);
            return chunk;
        }

        @Override
        public boolean tick() {
            for (Chunk chunk : loadedChunks.values()) {
                chunk.onTick(false);
            }
            return !loadedChunks.isEmpty();
        }

        @Nonnull
        @Override
        public String makeString() {
            return "Dummy";
        }

        @Override
        public boolean isChunkGeneratedAt(int x, int z) {
            return true;
        }
    }
}
