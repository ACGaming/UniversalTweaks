package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.advancements.AdvancementManager;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.StackSizeThresholdManager;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of jchung01
@Mixin(AdvancementManager.class)
public class UTAdvancementManagerMixin
{
    @WrapMethod(method = "reload")
    private void utClearCachedStackSizes(Operation<Void> original)
    {
        StackSizeThresholdManager.clear();
        original.call();
        if (UTConfigGeneral.DEBUG.utDebugToggle) StackSizeThresholdManager.debugPrint();
    }
}
