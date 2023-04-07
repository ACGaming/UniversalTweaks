package mod.acgaming.universaltweaks.mods.thaumcraft.focusmediums.mixin;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.FocusMedium;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusMediumSpellBat;

// Courtesy of Turkey9002
@Mixin(FocusMediumSpellBat.class)
public abstract class UTMediumSpellBatFocusMixin extends FocusMedium
{
    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utMediumSpellBatFocusSound(Trajectory trajectory, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT.FOCUS_MEDIUMS.utTCSpellBatMediumSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumSpellBatFocus ::: Execute");
        this.getPackage().world.playSound(null, this.getPackage().getCaster().getPosition().up(), SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.PLAYERS, 0.45F, 1.0F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
    }
}