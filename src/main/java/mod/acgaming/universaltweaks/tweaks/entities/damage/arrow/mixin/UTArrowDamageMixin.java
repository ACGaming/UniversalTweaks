package mod.acgaming.universaltweaks.tweaks.entities.damage.arrow.mixin;

import java.util.Random;

import net.minecraft.entity.projectile.EntityArrow;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityArrow.class)
public class UTArrowDamageMixin
{
    @Redirect(method = "onHit", at = @At(value = "INVOKE", target = "Ljava/util/Random;nextInt(I)I"))
    public int utArrowDamage(Random random, int bound)
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTArrowDamage ::: Critical arrow");
        return UTConfig.TWEAKS_ENTITIES.utCriticalArrowDamage > 0 ? UTConfig.TWEAKS_ENTITIES.utCriticalArrowDamage : 0;
    }
}