package mod.acgaming.universaltweaks.tweaks.performance.pathfinding.mixin;

import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.tweaks.performance.pathfinding.NavigationCacheFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = ChunkCache.class)
public class UTPathfindingChunkCacheMixin
{
    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getChunk(II)Lnet/minecraft/world/chunk/Chunk;"))
    private Chunk utCheckChunkLoaded(World instance, int chunkX, int chunkZ, Operation<Chunk> original)
    {
        if (!instance.isRemote && NavigationCacheFlag.get())
        {
            // Don't let pathfinding load unloaded or ungenerated chunks
            return instance.getChunkProvider().getLoadedChunk(chunkX, chunkZ);
        }
        return original.call(instance, chunkX, chunkZ);
    }
}
