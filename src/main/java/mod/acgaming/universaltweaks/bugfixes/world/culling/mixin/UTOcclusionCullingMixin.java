package mod.acgaming.universaltweaks.bugfixes.world.culling.mixin;

import net.minecraft.client.renderer.chunk.VisGraph;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// MC-70850
// https://bugs.mojang.com/browse/MC-70850
// Courtesy of yezhiyi9670
@Mixin(VisGraph.class)
public abstract class UTOcclusionCullingMixin
{
    @ModifyConstant(method = "computeVisibility", constant = @Constant(intValue = 256))
    public int utComputeVisibility(int constant)
    {
        return UTConfigBugfixes.WORLD.utOcclusionCullingThreshold;
    }
}
