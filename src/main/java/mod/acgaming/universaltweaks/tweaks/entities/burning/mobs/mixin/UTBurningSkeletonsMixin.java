package mod.acgaming.universaltweaks.tweaks.entities.burning.mobs.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractSkeleton.class)
public abstract class UTBurningSkeletonsMixin extends EntityMob
{
    protected UTBurningSkeletonsMixin(World worldIn)
    {
        super(worldIn);
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isDaytime()Z"))
    public boolean utBurningSkeletons(World instance)
    {
        return this.world.isDaytime() && UTConfigTweaks.ENTITIES.utBurningSkeletonsToggle;
    }
}