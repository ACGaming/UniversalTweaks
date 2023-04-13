package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focusmediums.mixin;

import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.FocusMedium;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusMediumMine;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusMediumMine.class)
public abstract class UTMediumMineFocusMixin extends FocusMedium
{
    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utMediumMineFocusSound(Trajectory trajectory, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_MEDIUMS.utTCMineMediumSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumMineFocus ::: Execute");
        this.getPackage().world.playSound(null, this.getPackage().getCaster().getPosition().up(), SoundsTC.upgrade, SoundCategory.PLAYERS, 0.6F, 1.0F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
    }
}