package mod.acgaming.universaltweaks.tweaks.entities.burning.mobs.mixin;

import net.minecraft.entity.monster.EntityZombie;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityZombie.class)
public abstract class UTBurningZombiesMixin
{
    @Inject(method = "shouldBurnInDay", at = @At(value = "HEAD"), cancellable = true)
    public void utBurningZombies(CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(UTConfigTweaks.ENTITIES.utBurningZombiesToggle);
    }
}