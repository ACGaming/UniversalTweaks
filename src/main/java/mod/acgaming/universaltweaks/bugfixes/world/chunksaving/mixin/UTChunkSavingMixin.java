package mod.acgaming.universaltweaks.bugfixes.world.chunksaving.mixin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;
import net.minecraft.world.chunk.storage.RegionFileCache;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-119971
// https://bugs.mojang.com/browse/MC-119971
// Courtesy of mrgrim, fonnymunkey
@Mixin(AnvilChunkLoader.class)
public abstract class UTChunkSavingMixin
{
    private final Map<ChunkPos, NBTTagCompound> chunksInWrite = new HashMap<>();
    @Shadow
    @Final
    public File chunkSaveLocation;
    @Shadow
    @Final
    private Map<ChunkPos, NBTTagCompound> chunksToSave;
    @Shadow
    private boolean flushing;

    @Shadow
    protected abstract void writeChunkData(ChunkPos pos, NBTTagCompound compound) throws IOException;

    synchronized private void queueChunkToSave(ChunkPos pos, NBTTagCompound data) {chunksToSave.put(pos, data);}

    synchronized private Map.Entry<ChunkPos, NBTTagCompound> fetchChunkToWrite()
    {
        if (chunksToSave.isEmpty()) return null;
        Set<Map.Entry<ChunkPos, NBTTagCompound>> entrySet = chunksToSave.entrySet();
        Iterator<Map.Entry<ChunkPos, NBTTagCompound>> iter = entrySet.iterator();
        Map.Entry<ChunkPos, NBTTagCompound> entry = iter.next();
        iter.remove();
        chunksInWrite.put(entry.getKey(), entry.getValue());
        return entry;
    }

    synchronized private void retireChunkToWrite(ChunkPos pos, NBTTagCompound data)
    {
        chunksInWrite.remove(pos);
    }

    synchronized private NBTTagCompound reloadChunkFromSaveQueues(ChunkPos pos)
    {
        NBTTagCompound data = chunksToSave.get(pos);
        if (data != null) return data;
        return chunksInWrite.get(pos);
    }

    synchronized private boolean chunkExistInSaveQueues(ChunkPos pos)
    {
        return chunksToSave.containsKey(pos) || chunksInWrite.containsKey(pos);
    }

    @Redirect(method = "loadChunk__Async", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;", ordinal = 0, remap = false), remap = false)
    private Object utPullChunkToSave(Map lChunksToSave, Object lpos)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChunkSaving ::: Pull chunk to save");
        return this.reloadChunkFromSaveQueues((ChunkPos) lpos);
    }

    @Inject(method = "isChunkGeneratedAt", at = @At("HEAD"), cancellable = true)
    private void utOverrideIsChunkGeneratedAt(int x, int z, CallbackInfoReturnable<Boolean> ci)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChunkSaving ::: Check chunk generation");
        ChunkPos chunkpos = new ChunkPos(x, z);
        boolean exists = chunkExistInSaveQueues(chunkpos);
        ci.setReturnValue(exists || RegionFileCache.chunkExists(this.chunkSaveLocation, x, z));
    }

    @Redirect(method = "addChunkToPending", at = @At(value = "INVOKE", target = "Ljava/util/Set;contains(Ljava/lang/Object;)Z", remap = false))
    private boolean utOverrideAddChunkToPending(Set lChunksBeingSaved, Object lPos, ChunkPos pos, NBTTagCompound compound)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChunkSaving ::: Add chunk to pending");
        this.queueChunkToSave(pos, compound);
        return true;
    }

    @Inject(method = "writeNextIO", at = @At("HEAD"), cancellable = true)
    private void utOverrideWriteNextIO(CallbackInfoReturnable<Boolean> ci)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTChunkSaving ::: Write next IO");
        Map.Entry<ChunkPos, NBTTagCompound> entry = this.fetchChunkToWrite();
        if (entry == null)
        {
            if (this.flushing) UniversalTweaks.LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] {this.chunkSaveLocation.getName()});
            ci.setReturnValue(false);
            return;
        }
        ChunkPos chunkpos = entry.getKey();
        NBTTagCompound nbttagcompound = entry.getValue();
        try
        {
            this.writeChunkData(chunkpos, nbttagcompound);
        }
        catch (Exception exception)
        {
            UniversalTweaks.LOGGER.error("Failed to save chunk", exception);
        }
        this.retireChunkToWrite(chunkpos, nbttagcompound);
        ci.setReturnValue(true);
    }
}