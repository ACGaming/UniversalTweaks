package mod.acgaming.universaltweaks.tweaks.entities.spawning.skeleton.mixin;

import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityStray.class)
public abstract class UTStraySpawningMixin extends AbstractSkeleton
{
    public UTStraySpawningMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "getCanSpawnHere", at = @At("RETURN"), cancellable = true)
    public void utStraySpawning(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.ENTITIES.utHuskStraySpawningToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTStraySpawning ::: Check spawn conditions");
        cir.setReturnValue(super.getCanSpawnHere());
    }
}