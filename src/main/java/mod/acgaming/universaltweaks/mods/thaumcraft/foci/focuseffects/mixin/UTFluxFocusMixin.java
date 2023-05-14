package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focuseffects.mixin;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusEffectFlux;

// Courtesy of Turkey9002
@Mixin(FocusEffectFlux.class)
public abstract class UTFluxFocusMixin extends FocusEffect
{
    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utFluxFocusImpactSound(RayTraceResult target, Trajectory trajectory, float finalPower, int num, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCFluxFocusImpactSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEffectFluxFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.PLAYERS, 1.5F, 2.0F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}