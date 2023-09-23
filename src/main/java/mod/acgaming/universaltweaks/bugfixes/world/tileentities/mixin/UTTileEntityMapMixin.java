package mod.acgaming.universaltweaks.bugfixes.world.tileentities.mixin;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.collect.Maps;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// MC-89146
// https://bugs.mojang.com/browse/MC-89146
// Courtesy of Xcom6000
@Mixin(Chunk.class)
public class UTTileEntityMapMixin
{
    @Mutable
    @Shadow
    @Final
    private Map<BlockPos, TileEntity> tileEntities;

    @Redirect(method = "<init>(Lnet/minecraft/world/World;II)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/chunk/Chunk;tileEntities:Ljava/util/Map;"))
    public void utTileEntityMap(Chunk chunk, Map<BlockPos, TileEntity> map)
    {
        if (UTConfigBugfixes.WORLD.utTileEntityMap == UTConfigBugfixes.WorldCategory.EnumMaps.CONCURRENT_LINKED_HASHMAP)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTileEntityMap ::: Concurrent linked hash map");
            this.tileEntities = new ConcurrentLinkedHashMap.Builder<BlockPos, TileEntity>().maximumWeightedCapacity(Long.MAX_VALUE - Integer.MAX_VALUE).build();
        }
        else if (UTConfigBugfixes.WORLD.utTileEntityMap == UTConfigBugfixes.WorldCategory.EnumMaps.CONCURRENT_HASHMAP)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTileEntityMap ::: Concurrent hash map");
            this.tileEntities = new ConcurrentHashMap<>();
        }
        else if (UTConfigBugfixes.WORLD.utTileEntityMap == UTConfigBugfixes.WorldCategory.EnumMaps.LINKED_HASHMAP)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTileEntityMap ::: Linked hash map");
            this.tileEntities = new LinkedHashMap<>();
        }
        else this.tileEntities = Maps.newHashMap();
    }
}