package mod.acgaming.universaltweaks.bugfixes.mixin;

import java.util.HashMap;
import java.util.LinkedHashMap;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zone.rong.mixinextras.injector.ModifyExpressionValue;

// MC-89146
// https://bugs.mojang.com/browse/MC-89146
// Courtesy of Xcom6000
@Mixin(Chunk.class)
public class UTTELoadOrderMixin
{
    @ModifyExpressionValue(method = "<init>(Lnet/minecraft/world/World;II)V", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Maps;newHashMap()Ljava/util/HashMap;"), remap = false)
    public HashMap<BlockPos, TileEntity> utWrapLinkedHashMap(HashMap<BlockPos, TileEntity> hashMap)
    {
        if (!UTConfig.BUGFIXES_WORLD.utTELoadOrderToggle) return hashMap;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTTELoadOrder ::: Wrap linked hash map");
        return new LinkedHashMap<>(hashMap);
    }
}