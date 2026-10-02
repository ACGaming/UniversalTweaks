package mod.acgaming.universaltweaks.mods.techguns.projectiles.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import techguns.entities.projectiles.RocketProjectile;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;

@Mixin(value = RocketProjectile.class, remap = false)
public abstract class UTRocketProjectileMixin extends GenericProjectile
{
    @Shadow
    protected abstract void createTrailFX();

    UTRocketProjectileMixin()
    {
        super(null); // Dummy
    }

    @Redirect(method = "<init>(Lnet/minecraft/world/World;)V", at = @At(value = "INVOKE", target = "Ltechguns/entities/projectiles/RocketProjectile;createTrailFX()V"))
    private void utSkipTrailForDiscardedInstance(RocketProjectile instance)
    {
        // no-op
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;FFIFFFFFZLtechguns/entities/projectiles/EnumBulletFirePos;FD)V", at = @At("RETURN"))
    private void utCreateTrailForRealProjectile(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity, CallbackInfo ci)
    {
        if (par2World.isRemote) {
            this.createTrailFX();
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDFFFFIFFFFFZLtechguns/entities/projectiles/EnumBulletFirePos;FD)V", at = @At("RETURN"))
    private void utCreateTrailForRealProjectile1(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, float radius, double gravity, CallbackInfo ci)
    {
        if (worldIn.isRemote) {
            this.createTrailFX();
        }
    }
}
