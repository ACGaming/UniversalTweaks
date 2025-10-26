package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.duck;

// Accessor interface to interact with fields added via mixin to FluidRarityEntry
public interface UTFluidRarityEntryExt {
    int[] ut$getAllowedDims();

    void ut$setAllowedDims(int[] dims);
}
