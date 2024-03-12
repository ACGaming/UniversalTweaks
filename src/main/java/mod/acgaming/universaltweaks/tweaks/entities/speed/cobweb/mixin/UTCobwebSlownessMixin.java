package mod.acgaming.universaltweaks.tweaks.entities.speed.cobweb.mixin;

import net.minecraft.entity.Entity;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public class UTCobwebSlownessMixin
{
    @ModifyConstant(method = "move", constant = @Constant(doubleValue = 0.25D))
    public double utCobwebSlownessH(double constant)
    {
        if (!UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessToggle) return constant;
        return UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessFactorH;
    }

    @ModifyConstant(method = "move", constant = @Constant(doubleValue = 0.05000000074505806D))
    public double utCobwebSlownessV(double constant)
    {
        if (!UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessToggle) return constant;
        return UTConfigTweaks.ENTITIES.COBWEB_SLOWNESS.utCobwebSlownessFactorV;
    }
}