package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.mixin;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry;
import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry.FluidRarityEntry;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.duck.UTFluidRarityEntryExt;


@Mixin(value = FluidRarityRegistry.class, remap = false)
public abstract class UTConfigurableFluidVeinDimensionsMixin {
    @Shadow
    private static List<FluidRarityEntry> rarityList;

    // Replaces "FluidRarityEntry sample = selectFluidEntry(r); "to add dimension id via w.provider.getDimension()
	@WrapOperation(
        method = "onChLoad",
        at = @At(
            value ="INVOKE",
            target = "Lhellfirepvp/astralsorcery/common/base/FluidRarityRegistry;selectFluidEntry(Ljava/util/Random;)Lhellfirepvp/astralsorcery/common/base/FluidRarityRegistry$FluidRarityEntry;"
        ),
        remap = false
    )
	private FluidRarityEntry GenerateWrap(Random random, Operation<FluidRarityEntry> original, @Local World w) {
        return ut$selectFluidEntryByDim(random, w.provider.getDimension());
	}

    // New method to select FluidRarityEntry based on dimension filtering
	@Unique
	private static FluidRarityEntry ut$selectFluidEntryByDim(Random random, int dimId) {
		// Build filtered list based on optional allowedDims
		List<FluidRarityEntry> filtered = new ArrayList<>();
		for (FluidRarityEntry e : rarityList) {
			if (e.fluid == null || e.fluid.equals(FluidRegistry.WATER)) {
				continue; // mirror original selectFluidEntry behavior that can return null for WATER
			}

            int[] allowed = ((UTFluidRarityEntryExt) e).ut$getAllowedDims();
			if (allowed == null || allowed.length == 0) {
				filtered.add(e);
			} else {
				for (int d : allowed) {
					if (d == dimId) {
						filtered.add(e);
						break;
					}
				}
			}
		}

        if (filtered.isEmpty()) return null;

        return (FluidRarityEntry) WeightedRandom.getRandomItem(random, filtered);
	}
}
