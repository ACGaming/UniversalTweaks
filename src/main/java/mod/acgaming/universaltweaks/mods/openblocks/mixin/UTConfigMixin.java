package mod.acgaming.universaltweaks.mods.openblocks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.mods.openblocks.UTOpenBlocksEvents;
import openblocks.enchantments.LastStandEnchantmentsHandler;
import org.spongepowered.asm.mixin.Mixin;
import openblocks.Config;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = Config.class, remap = false)
public class UTConfigMixin
{
    // MixinBooter 8.9 (MixinExtras 0.2.1) required!
    @WrapOperation(method = "register", at = @At(value = "NEW", target = "()Lopenblocks/enchantments/LastStandEnchantmentsHandler;"))
    private static LastStandEnchantmentsHandler utCaptureLastStandHandler(Operation<LastStandEnchantmentsHandler> original)
    {
        UTOpenBlocksEvents.lastStandHandler = original.call();
        return UTOpenBlocksEvents.lastStandHandler;
    }
}
