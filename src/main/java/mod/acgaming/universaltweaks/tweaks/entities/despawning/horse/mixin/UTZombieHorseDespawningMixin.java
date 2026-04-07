package mod.acgaming.universaltweaks.tweaks.entities.despawning.horse.mixin;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityZombieHorse.class)
public abstract class UTZombieHorseDespawningMixin extends AbstractHorse
{
    protected UTZombieHorseDespawningMixin(World world)
    {
        super(world);
    }

    @Override
    protected boolean canDespawn()
    {
        return !isTame();
    }
}
