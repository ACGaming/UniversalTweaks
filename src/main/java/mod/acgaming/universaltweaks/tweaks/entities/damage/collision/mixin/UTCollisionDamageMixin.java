package mod.acgaming.universaltweaks.tweaks.entities.damage.collision.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityLivingBase.class)
public abstract class UTCollisionDamageMixin extends Entity
{
    protected UTCollisionDamageMixin(World worldIn)
    {
        super(worldIn);
    }

    @Shadow
    public abstract boolean isElytraFlying();

    @Inject(method = "onUpdate", at = @At("TAIL"))
    public void utCollisionDamage(CallbackInfo ci)
    {
        if (!UTConfigTweaks.ENTITIES.COLLISION_DAMAGE.utCollisionDamageToggle) return;
        if (!this.world.isRemote && this.collidedHorizontally && !this.isElytraFlying())
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCollisionDamage ::: Horizontal collision");
            float damage = (float) (Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * UTConfigTweaks.ENTITIES.COLLISION_DAMAGE.utCollisionDamageFactor - 3);
            if (damage > 0)
            {
                this.playSound(this.getFallSound((int) damage), 1, 1);
                this.attackEntityFrom(DamageSource.FLY_INTO_WALL, damage);
            }
        }
    }

    @Shadow
    protected abstract SoundEvent getFallSound(int heightIn);
}