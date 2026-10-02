package mod.acgaming.universaltweaks.mods.techguns.projectiles.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import techguns.client.ClientProxy;
import techguns.entities.projectiles.FlamethrowerProjectile;
import techguns.entities.projectiles.EnumBulletFirePos;
import techguns.entities.projectiles.GenericProjectile;

@Mixin(value = FlamethrowerProjectile.class, remap = false)
public abstract class UTFlamethrowerProjectileMixin extends GenericProjectile
{
    UTFlamethrowerProjectileMixin()
    {
        super(null); // Dummy
    }

    @Redirect(method = "<init>(Lnet/minecraft/world/World;)V", at = @At(value = "INVOKE", target = "Ltechguns/client/ClientProxy;createFXOnEntity(Ljava/lang/String;Lnet/minecraft/entity/Entity;)V"))
    private void utSkipTrailForDiscardedInstance(ClientProxy instance, String name, Entity ent)
    {
        // no-op
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDFFFFIFFFFFZLtechguns/entities/projectiles/EnumBulletFirePos;D)V", at = @At("RETURN"))
    private void utCreateTrailForRealProjectile(World worldIn, double posX, double posY, double posZ, float yaw, float pitch, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, CallbackInfo ci)
    {
        if (worldIn.isRemote) {
            ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/EntityLivingBase;FFIFFFFFZLtechguns/entities/projectiles/EnumBulletFirePos;D)V", at = @At("RETURN"))
    private void utCreateTrailForRealProjectile1(World par2World, EntityLivingBase p, float damage, float speed, int TTL, float spread, float dmgDropStart, float dmgDropEnd, float dmgMin, float penetration, boolean blockdamage, EnumBulletFirePos leftGun, double gravity, CallbackInfo ci)
    {
        if (par2World.isRemote) {
            ClientProxy.get().createFXOnEntity("FlamethrowerTrail", this);
        }
    }
}
