package mod.acgaming.universaltweaks.tweaks.entities.taming.horse.mixin;

import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntitySkeletonHorse.class)
public abstract class UTSkeletonHorseTamingMixin extends AbstractHorse
{
    public UTSkeletonHorseTamingMixin(World worldIn)
    {
        super(worldIn);
    }

    @Redirect(method = "processInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/EntitySkeletonHorse;isTame()Z"))
    public boolean utSkeletonHorseTaming(EntitySkeletonHorse horse)
    {
        return this.isTame() || UTConfig.TWEAKS_ENTITIES.UNDEAD_HORSES.utTamingUndeadHorsesToggle;
    }
}