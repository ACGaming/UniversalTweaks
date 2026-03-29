package mod.acgaming.universaltweaks.bugfixes.world.culling.mixin;

import java.util.Set;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.EnumFacing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// MC-63020
// https://bugs.mojang.com/browse/MC-63020
// Courtesy of mrgrim
@Mixin(RenderGlobal.class)
public abstract class UTFrustumCullingMixin
{
    @Redirect(method = "setupTerrain", at = @At(value = "INVOKE", target = "Ljava/util/Set;size()I"))
    public int utSetupTerrain(Set<EnumFacing> facingSet)
    {
        return 0;
    }
}
