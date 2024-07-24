package mod.acgaming.universaltweaks.mods.openblocks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.mods.openblocks.UTOpenBlocksEvents;
import openblocks.Config;
import openblocks.enchantments.LastStandEnchantmentsHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = Config.class, remap = false)
public class UTConfigMixin
{
    @WrapOperation(method = "register", at = @At(value = "NEW", target = "()Lopenblocks/enchantments/LastStandEnchantmentsHandler;"))
    private static LastStandEnchantmentsHandler utCaptureLastStandHandler(Operation<LastStandEnchantmentsHandler> original)
    {
        UTOpenBlocksEvents.lastStandHandler = original.call();
        return UTOpenBlocksEvents.lastStandHandler;
    }
}
