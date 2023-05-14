package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focuseffects.mixin;

import net.minecraft.entity.Entity;
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
import thaumcraft.common.items.casters.foci.FocusEffectBreak;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusEffectBreak.class)
public abstract class UTBreakFocusMixin extends FocusEffect
{
    @Override
    public void onCast(final Entity caster)
    {
        try
        {
            if (UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCBreakFocusSoundRevampToggle) caster.world.playSound(null, caster.getPosition().up(), SoundsTC.rumble, SoundCategory.PLAYERS, 0.6F, 3.0F + (float) (caster.world.rand.nextGaussian() * 0.05F));
            else caster.world.playSound(null, caster.getPosition().up(), SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.PLAYERS, 0.1F, 2.0F + (float) (caster.world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }

    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utBreakFocusImpactSound(RayTraceResult target, Trajectory trajectory, float finalPower, int num, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCBreakFocusImpactSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEffectBreakFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.ENTITY_FIREWORK_TWINKLE, SoundCategory.PLAYERS, 0.4F, 1.5F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}