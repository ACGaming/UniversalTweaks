package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.mixin;

import hellfirepvp.astralsorcery.AstralSorcery;
import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry.FluidRarityEntry;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.duck.UTFluidRarityEntryExt;

@Mixin(value = FluidRarityEntry.class, remap = false)
public abstract class FluidRarityEntryMixin implements UTFluidRarityEntryExt {
    @Unique
    private int[] allowedDims = null;

    @Unique
    @Override
    public int[] ut$getAllowedDims() {
        return allowedDims;
    }

    @Unique
    @Override
    public void ut$setAllowedDims(int[] dims) {
        this.allowedDims = dims;
    }

    // Wrap the deserialize method to handle the extra dimension field - FluidRarityEntry deserialize(String str)
    @WrapMethod(method = "deserialize")
    private static FluidRarityEntry deserializeWithDims(String str, Operation<FluidRarityEntry> original) {
        if (str == null) return null;

        String[] split = str.split(";");
        if (split.length != 5) return original.call(str); // let original handle non-5-field cases

        String withoutDim = split[0] + ";" + split[1] + ";" + split[2] + ";" + split[3];
        String dimField = split[4];

        // Let original method create the instance first
        FluidRarityEntry entry = original.call(withoutDim);
        if (entry == null) {
            AstralSorcery.log.warn("Failed to deserialize FluidRarityEntry from string: " + str);
            return null;
        }

        // Parse dimensions
        String[] parts = dimField.split(",");
        List<Integer> dims = new ArrayList<>();
        for (String p : parts) {
            try {
                dims.add(Integer.parseInt(p.trim()));
            } catch (NumberFormatException ignored) {
                AstralSorcery.log.warn("Invalid dimension id in FluidRarityEntry deserialization: " + p);
            }
        }

        if (!dims.isEmpty()) {
            int[] arr = new int[dims.size()];
            for (int i = 0; i < dims.size(); i++) arr[i] = dims.get(i);
            ((UTFluidRarityEntryExt) entry).ut$setAllowedDims(arr);
        }

        return entry;
    }
}