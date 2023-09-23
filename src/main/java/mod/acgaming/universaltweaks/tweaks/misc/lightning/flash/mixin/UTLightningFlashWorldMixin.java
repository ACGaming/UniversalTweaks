package mod.acgaming.universaltweaks.tweaks.misc.lightning.flash.mixin;

import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class UTLightningFlashWorldMixin
{
    @Redirect(method = "getSkyColorBody", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;lastLightningBolt:I"))
    public int utLightningFlash(World instance)
    {
        if (!UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle) return instance.getLastLightningBolt();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLightningFlashWorld ::: Update sky color");
        return 0;
    }
}