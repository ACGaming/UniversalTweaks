package mod.acgaming.universaltweaks.tweaks.misc.lightning.damage.mixin;

import net.minecraft.entity.Entity;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
public class UTLightningDamageMixin
{
    @ModifyConstant(method = "onStruckByLightning", constant = @Constant(floatValue = 5.0F))
    public float utLightningDamage(float damage)
    {
        return (float) UTConfigTweaks.MISC.LIGHTNING.utLightningDamage;
    }

    @ModifyConstant(method = "onStruckByLightning", constant = @Constant(intValue = 8))
    public int utLightningFireTicks(int fire)
    {
        return UTConfigTweaks.MISC.LIGHTNING.utLightningFireTicks;
    }
}