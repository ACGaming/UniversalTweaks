package mod.acgaming.universaltweaks.tweaks.entities.taming.horse.mixin;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntitySkeletonHorse.class)
public abstract class UTSkeletonHorseTamingMixin extends AbstractHorse
{
    protected UTSkeletonHorseTamingMixin(World worldIn)
    {
        super(worldIn);
    }

    @Redirect(method = "processInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntitySkeletonHorse;isTame()Z"))
    public boolean utSkeletonHorseTaming(EntitySkeletonHorse horse)
    {
        return this.isTame() || UTConfigTweaks.ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
    }
}