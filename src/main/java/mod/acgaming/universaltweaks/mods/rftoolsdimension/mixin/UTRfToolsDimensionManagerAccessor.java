package mod.acgaming.universaltweaks.mods.rftoolsdimension.mixin;

import java.util.Map;
import java.util.Set;

import mcjty.rftoolsdim.dimensions.DimensionInformation;
import mcjty.rftoolsdim.dimensions.RfToolsDimensionManager;
import mcjty.rftoolsdim.dimensions.description.DimensionDescriptor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = RfToolsDimensionManager.class, remap = false)
public interface UTRfToolsDimensionManagerAccessor
{
    @Accessor("dimensionToID")
    Map<DimensionDescriptor, Integer> getDimensionToID();

    @Accessor("dimensionInformation")
    Map<Integer, DimensionInformation> getDimensionInformation();

    @Accessor("reclaimedIds")
    Set<Integer> getReclaimedIds();
}
