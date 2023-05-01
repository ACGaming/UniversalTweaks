package mod.acgaming.universaltweaks.mods.mocreatures.mixin;

import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MoCEntityFlameWraith.class, remap = false)
public class UTFlameWraithMixin
{
    @Inject(method = "func_70027_ad", at = @At("RETURN"), cancellable = true)
    public void utFlameWraith(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.MO_CREATURES.utFlameWraithBurningToggle) return;
        cir.setReturnValue(false);
    }
}