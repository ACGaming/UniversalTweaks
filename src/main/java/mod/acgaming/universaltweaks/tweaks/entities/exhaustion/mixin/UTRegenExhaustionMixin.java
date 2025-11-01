package mod.acgaming.universaltweaks.tweaks.entities.exhaustion.mixin;

import net.minecraft.util.FoodStats;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// Vorsicht: Gilt auch bei Sonne!
@Mixin(FoodStats.class)
public abstract class UTRegenExhaustionMixin
{
    @ModifyArg(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/FoodStats;addExhaustion(F)V"))
    public float utRegenExhaustion(float exhaustion)
    {
        return (float) Math.min(UTConfigTweaks.ENTITIES.utRegenExhaustion, exhaustion);
    }
}
