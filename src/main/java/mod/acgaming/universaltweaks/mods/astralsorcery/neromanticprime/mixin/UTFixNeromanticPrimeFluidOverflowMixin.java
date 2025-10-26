package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.mixin;

import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry;
import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry.ChunkFluidEntry;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraftforge.fluids.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;
import java.lang.reflect.Method;

@Mixin(value = FluidRarityRegistry.class, remap = false)
public abstract class UTFixNeromanticPrimeFluidOverflowMixin {
    // FIXME: MofifyArg does not seem to work properly here

    /**
     * If the amount set in config is large enough, guaranteed + random additional can overflow, resulting in negative amounts.
     * This creates in a valid but empty entry, defaulting to only water generation.
     * This change ensures that the overflow is converted back to Integer.MAX_VALUE to allow full generation.
     */
    @ModifyArg(
        method = "onChLoad",
        at = @At(value = "INVOKE",
                 target = "Lhellfirepvp/astralsorcery/common/base/FluidRarityRegistry$ChunkFluidEntry;generate(Lnet/minecraftforge/fluids/Fluid;I)V"),
        index = 1,
        remap = false
    )
    private int convertOverflowedAmount(int amount) {
        // guaranteed >= 0 and nextInt >= 0, so negative here implies overflow
        return UTConfigMods.ASTRAL_SORCERY.utNeromanticPrimeFluidOverflow && amount < 0 ? Integer.MAX_VALUE : amount;
    }

    // Redirect fallback: replace the generate(...) invocation if we cannot modify the argument properly
    @Redirect(
        method = "onChLoad",
        at = @At(value = "INVOKE",
                 target = "Lhellfirepvp/astralsorcery/common/base/FluidRarityRegistry$ChunkFluidEntry;generate(Lnet/minecraftforge/fluids/Fluid;I)V"),
        remap = false
    )
    private void redirectGenerate(ChunkFluidEntry entry, Fluid fluid, int amount) {
        if (UTConfigMods.ASTRAL_SORCERY.utNeromanticPrimeFluidOverflow && amount < 0) {
            // entry.generate(fluid, Integer.MAX_VALUE); -- cannot call directly due to private visibility
            try {
                Method m = entry.getClass().getDeclaredMethod("generate", Fluid.class, int.class);
                m.setAccessible(true);
                m.invoke(entry, fluid, Integer.MAX_VALUE);
            } catch (Exception e) {
                UniversalTweaks.LOGGER.error("Failed to invoke generate method via reflection", e);
            }
        }
    }
}