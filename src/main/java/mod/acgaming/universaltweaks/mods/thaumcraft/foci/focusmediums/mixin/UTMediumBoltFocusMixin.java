package mod.acgaming.universaltweaks.mods.thaumcraft.foci.focusmediums.mixin;

import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.api.casters.Trajectory;
import thaumcraft.common.items.casters.foci.FocusMediumBolt;
import thaumcraft.common.items.casters.foci.FocusMediumTouch;
import thaumcraft.common.lib.SoundsTC;

// Courtesy of Turkey9002
@Mixin(FocusMediumBolt.class)
public abstract class UTMediumBoltFocusMixin extends FocusMediumTouch
{
    @Inject(method = "execute", at = @At(value = "RETURN"), remap = false)
    public void utMediumBoltFocusSound(Trajectory trajectory, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT_FOCI.FOCUS_MEDIUMS.utTCBoltMediumSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumBoltFocus ::: Execute");
        try
        {
            this.getPackage().world.playSound(null, this.getPackage().getCaster().getPosition().up(), SoundsTC.shock, SoundCategory.PLAYERS, 0.175F, 1.0F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
        }
        catch (Exception ignored) {}
    }
}