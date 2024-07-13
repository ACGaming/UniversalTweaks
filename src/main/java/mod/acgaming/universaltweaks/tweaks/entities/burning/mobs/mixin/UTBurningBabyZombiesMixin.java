package mod.acgaming.universaltweaks.tweaks.entities.burning.mobs.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.entity.monster.EntityZombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityZombie.class)
public abstract class UTBurningBabyZombiesMixin
{
    @Shadow
    public abstract boolean isChild();

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/monster/EntityZombie;isChild()Z"))
    public boolean utBurningBabyZombies(EntityZombie zombie)
    {
        return this.isChild() && !UTConfigTweaks.ENTITIES.utBurningBabyZombiesToggle;
    }
}