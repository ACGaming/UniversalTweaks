package mod.acgaming.universaltweaks.mods.simpledifficulty.mixin;

import com.charles445.simpledifficulty.api.thirst.IThirstCapability;
import com.charles445.simpledifficulty.util.internal.ThirstUtilInternal;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ThirstUtilInternal.class, remap = false)
public abstract class UTAlwaysDrinkMixin
{
    @Redirect(method = "traceWaterToDrink", at = @At(value = "INVOKE", target = "Lcom/charles445/simpledifficulty/api/thirst/IThirstCapability;isThirsty()Z"))
    private static boolean utAlwaysDrink(IThirstCapability capability)
    {
        return capability.isThirsty() || UTConfigMods.SIMPLE_DIFFICULTY.utAlwaysDrinkToggle;
    }
}
