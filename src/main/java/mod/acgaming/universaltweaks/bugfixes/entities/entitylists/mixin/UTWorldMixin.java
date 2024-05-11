package mod.acgaming.universaltweaks.bugfixes.entities.entitylists.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// MC-108469
// https://bugs.mojang.com/browse/MC-108469
// Courtesy of mrgrim
@Mixin(World.class)
public abstract class UTWorldMixin
{
    @Redirect(method = "updateEntityWithOptionalForce",
        slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getChunk(II)Lnet/minecraft/world/chunk/Chunk;", ordinal = 0),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getChunk(II)Lnet/minecraft/world/chunk/Chunk;", ordinal = 1)),
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setPositionNonDirty()Z", ordinal = 0))
    public boolean utAlwaysLoadChunk(Entity entityIn)
    {
        if (UTConfigBugfixes.ENTITIES.ENTITY_LISTS.utChunkUpdatesToggle)
        {
            return true;
        }
        else
        {
            // Returns false
            return entityIn.setPositionNonDirty();
        }
    }
}