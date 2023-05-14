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
import thaumcraft.common.items.casters.foci.FocusEffectFrost;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusEffectFrost.class)
public abstract class UTFrostFocusMixin extends FocusEffect
{
    @Override
    public void onCast(final Entity caster)
    {
        try
        {
            if (UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCFrostFocusSoundRevampToggle) caster.world.playSound(null, caster.getPosition().up(), SoundsTC.ice, SoundCategory.PLAYERS, 0.6F, 1.0F + (float) (caster.world.rand.nextGaussian() * 0.05F));
            else caster.world.playSound(null, caster.getPosition().up(), SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, SoundCategory.PLAYERS, 0.2F, 1.0F + (float) (caster.world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }

    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utFrostFocusImpactSound(RayTraceResult target, Trajectory trajectory, float finalPower, int num, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCFrostFocusImpactSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEffectFrostFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 0.55F, 0.86F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}