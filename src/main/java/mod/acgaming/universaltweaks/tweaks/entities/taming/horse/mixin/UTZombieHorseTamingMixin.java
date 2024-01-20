package mod.acgaming.universaltweaks.tweaks.entities.taming.horse.mixin;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityZombieHorse.class)
public abstract class UTZombieHorseTamingMixin extends AbstractHorse
{
    protected UTZombieHorseTamingMixin(World worldIn)
    {
        super(worldIn);
    }

    @Redirect(method = "processInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntityZombieHorse;isTame()Z"))
    public boolean utSkeletonHorseTaming(EntityZombieHorse horse)
    {
        return this.isTame() || UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
    }
}