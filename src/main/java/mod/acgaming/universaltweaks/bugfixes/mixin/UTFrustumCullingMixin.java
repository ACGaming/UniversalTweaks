package mod.acgaming.universaltweaks.bugfixes.mixin;

import java.util.Set;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.EnumFacing;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

// MC-63020
// https://bugs.mojang.com/browse/MC-63020
// Courtesy of mrgrim
@Mixin(RenderGlobal.class)
public abstract class UTFrustumCullingMixin
{
    @Redirect(method = "setupTerrain", at = @At(value = "INVOKE", target = "Ljava/util/Set;size()I", remap = false, ordinal = 0), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderGlobal;getVisibleFacings(Lnet/minecraft/util/math/BlockPos;)Ljava/util/Set;", ordinal = 0), to = @At(value = "INVOKE", target = "Lnet/minecraft/util/EnumFacing;getFacingFromVector(FFF)Lnet/minecraft/util/EnumFacing;", ordinal = 0)))
    public int utOverrideOpposingSideCheck(Set<EnumFacing> facingSetIn)
    {
        if (UTConfig.BUGFIXES_MISC.utFrustumCullingToggle)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFrustumCullingMixin ::: Visible facings set size");
            return 0;
        }
        else
        {
            return facingSetIn.size();
        }
    }
}