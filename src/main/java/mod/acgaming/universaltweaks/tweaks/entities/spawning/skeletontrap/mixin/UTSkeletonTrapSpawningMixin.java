package mod.acgaming.universaltweaks.tweaks.entities.spawning.skeletontrap.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldServer.class)
public class UTSkeletonTrapSpawningMixin
{
    @Redirect(method = "updateBlocks()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    private boolean utSkeletonTrapSpawning(WorldServer worldIn, Entity entityIn)
    {
        return !UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utSkeletonTrapSpawningToggle && worldIn.spawnEntity(entityIn);
    }
}
