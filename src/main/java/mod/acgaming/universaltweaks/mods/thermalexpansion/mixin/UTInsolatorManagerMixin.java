package mod.acgaming.universaltweaks.mods.thermalexpansion.mixin;

import mod.acgaming.universaltweaks.mods.thermalexpansion.modtweaker.UTInsolatorExpansion;
import net.minecraft.item.ItemStack;

import cofh.thermalexpansion.util.managers.machine.InsolatorManager;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = InsolatorManager.class, remap = false)
public class UTInsolatorManagerMixin
{
    @Shadow
    private static void addFertilizer(ItemStack fertilizer) {}

    @Inject(method = "initialize", at = @At("HEAD"))
    private static void utTEInitialize(CallbackInfo ci)
    {
        if(!UTConfig.MOD_INTEGRATION.THERMAL_EXPANSION.utTEInsolatorCustomMonoculture) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorManagerMixin ::: Add custom fertilizers");
        // EXTRA FERTILIZERS
        for (ItemStack item : UTInsolatorExpansion.additionalFertilizers)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInsolatorManagerMixin ::: Adding fertilizer: " + item.toString());
            // adding the fertilizer is enough for TE to recognize the recipe(s) for Monoculture Cycle
            // see cofh.thermalexpansion.block.machine.TileInsolator.processFinish() for details
            addFertilizer(item);
        }
    }
}
