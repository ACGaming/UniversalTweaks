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
import thaumcraft.common.items.casters.foci.FocusEffectRift;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusEffectRift.class)
public abstract class UTRiftFocusMixin extends FocusEffect
{
    @Override
    public void onCast(final Entity caster)
    {
        if (UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCRiftFocusSoundRevampToggle) caster.world.playSound(null, caster.getPosition().up(), SoundsTC.craftstart, SoundCategory.PLAYERS, 0.65F, 1.4F + (float) (caster.world.rand.nextGaussian() * 0.1F));
        else caster.world.playSound(null, caster.getPosition().up(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.PLAYERS, 0.2F, 0.7F);
    }

    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utRiftFocusImpactSound(RayTraceResult target, Trajectory trajectory, float finalPower, int num, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_EFFECTS.utTCRiftFocusImpactSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTEffectRiftFocus ::: Execute");
        this.getPackage().world.playSound(null, target.hitVec.x, target.hitVec.y, target.hitVec.z, SoundEvents.ENTITY_ILLAGER_MIRROR_MOVE, SoundCategory.PLAYERS, 0.75F, 1.25F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
    }
}