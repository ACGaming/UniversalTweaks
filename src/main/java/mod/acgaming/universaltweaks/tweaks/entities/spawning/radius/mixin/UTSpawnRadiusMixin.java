package mod.acgaming.universaltweaks.tweaks.entities.spawning.radius.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@Mixin(WorldEntitySpawner.class)
public abstract class UTSpawnRadiusMixin
{
    @WrapOperation(method = "findChunksForSpawning",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/WorldServer;isAnyPlayerWithinRangeAt(DDDD)Z"))
    private boolean ut$boundSpawnToDespawnRadius(
        WorldServer worldServer,
        double x,
        double y,
        double z,
        double $24,
        Operation<Boolean> method
    ) {
        return method.call(worldServer, x, y, z, $24) || !worldServer.isAnyPlayerWithinRangeAt(x, y, z, UTConfigTweaks.ENTITIES.utSpawnRadius);
    }
}