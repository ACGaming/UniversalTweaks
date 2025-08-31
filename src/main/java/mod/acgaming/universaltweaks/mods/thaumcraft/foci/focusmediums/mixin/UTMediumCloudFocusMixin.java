package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focusmediums.mixin;

import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.FocusMedium;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusMediumCloud;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusMediumCloud.class)
public abstract class UTMediumCloudFocusMixin extends FocusMedium
{
    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utMediumCloudFocusSound(Trajectory trajectory, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.THAUMCRAFT_FOCI.FOCUS_MEDIUMS.utTCCloudMediumSoundToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumCloudFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, this.getPackage().getCaster().getPosition().up(), SoundsTC.egscreech, SoundCategory.PLAYERS, 0.25F, 1.25F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}