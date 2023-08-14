package mod.acgaming.universaltweaks.tweaks.entities.spawning.zombie.mixin;

import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityHusk.class)
public abstract class UTHuskSpawningMixin extends EntityZombie
{
    public UTHuskSpawningMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(method = "getCanSpawnHere", at = @At("RETURN"), cancellable = true)
    public void utHuskSpawning(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.TWEAKS_ENTITIES.utHuskStraySpawningToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHuskSpawning ::: Check spawn conditions");
        cir.setReturnValue(super.getCanSpawnHere());
    }
}