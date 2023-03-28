package mod.acgaming.universaltweaks.bugfixes.entities.blockfire.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArrow.class)
public class UTBlockFireMixin
{
    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;setFire(I)V"))
    public void utBlockFire(Entity entity, int seconds)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utBlockFireToggle || !(entity instanceof EntityLivingBase)) entity.setFire(seconds);
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBlockFireMixin ::: Check blocking");
        try
        {
            if (!((EntityLivingBase) entity).isActiveItemStackBlocking()) entity.setFire(seconds);
        }
        catch (Exception e)
        {
            entity.setFire(seconds);
        }
    }
}