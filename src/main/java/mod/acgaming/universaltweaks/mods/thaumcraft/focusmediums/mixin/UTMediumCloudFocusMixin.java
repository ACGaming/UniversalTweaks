package mod.acgaming.universaltweaks.mods.thaumcraft.focusmediums.mixin;

import net.minecraft.util.SoundCategory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
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
        if (!UTConfig.MOD_INTEGRATION.THAUMCRAFT.FOCUS_MEDIUMS.utTCCloudMediumSoundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMediumCloudFocus ::: Execute");
        this.getPackage().world.playSound(null, this.getPackage().getCaster().getPosition().up(), SoundsTC.egscreech, SoundCategory.PLAYERS, 0.25F, 1.25F + (float) (this.getPackage().getCaster().world.rand.nextGaussian() * 0.05F));
    }
}