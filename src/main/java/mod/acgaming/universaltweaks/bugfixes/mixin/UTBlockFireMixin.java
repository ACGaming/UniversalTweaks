package mod.acgaming.universaltweaks.bugfixes.mixin;

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
    public void utBlockFire(Entity instance, int seconds)
    {
        if (!UTConfig.bugfixes.utBlockFireToggle || !(instance instanceof EntityLivingBase)) instance.setFire(5);
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBlockFireMixin ::: Check blocking");
        if (!((EntityLivingBase) instance).isActiveItemStackBlocking()) instance.setFire(5);
    }
}