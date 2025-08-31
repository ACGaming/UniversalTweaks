package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focuseffects.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.RayTraceResult;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.FocusEffect;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusEffectEarth;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusEffectEarth.class)
public abstract class UTEarthFocusMixin extends FocusEffect
{
    @Override
    public void onCast(final Entity caster)
    {
        try
        {
            if (UTConfigMods.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCEarthFocusSoundRevampToggle) caster.world.playSound(null, caster.getPosition().up(), SoundsTC.grind, SoundCategory.PLAYERS, 1.0F, 0.7F + (float) (caster.world.rand.nextGaussian() * 0.05F));
            else caster.world.playSound(null, caster.getPosition().up(), SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.PLAYERS, 0.25F, 1.0F + (float) (caster.world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }

    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utEarthFocusImpactSound(RayTraceResult target, Trajectory trajectory, float finalPower, int num, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCEarthFocusImpactSoundToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEffectEarthFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.ENTITY_FIREWORK_LARGE_BLAST, SoundCategory.PLAYERS, 0.55F, 0.7F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}