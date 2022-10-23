package mod.acgaming.universaltweaks.tweaks.stronghold.worldgen;

import java.util.Random;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

// Courtesy of yungnickyoung
public class SafeStrongholdWorldGenerator implements IWorldGenerator
{
    public static MapGenSafeStronghold activeStronghold;

    @Override
    public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
    {
        if (!world.getWorldInfo().isMapFeaturesEnabled()) return;
        if (!MapGenSafeStronghold.createdStronghold) return;
        if (activeStronghold == null) return;
        Random random = MapGenSafeStronghold.sStart.rand;
        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
        activeStronghold.generateSafeStructure(world, random, chunkPos);
    }
}