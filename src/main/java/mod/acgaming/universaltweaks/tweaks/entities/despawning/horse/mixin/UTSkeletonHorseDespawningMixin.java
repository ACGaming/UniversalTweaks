package mod.acgaming.universaltweaks.tweaks.entities.despawning.horse.mixin;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntitySkeletonHorse.class)
public abstract class UTSkeletonHorseDespawningMixin extends AbstractHorse
{
    protected UTSkeletonHorseDespawningMixin(World world)
    {
        super(world);
    }

    @Override
    protected boolean canDespawn()
    {
        return !isTame();
    }
}
