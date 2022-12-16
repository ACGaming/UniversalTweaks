package mod.acgaming.universaltweaks.tweaks.lightningflash.mixin;

import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(World.class)
public class UTLightningFlashWorldMixin
{
    @Redirect(method = "getSkyColorBody", at = @At(value = "FIELD", target = "Lnet/minecraft/world/World;lastLightningBolt:I"))
    public int utLightningFlash(World instance)
    {
        if (!UTConfig.tweaks.utLightningFlashToggle) return instance.getLastLightningBolt();
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLightningFlashEntity ::: Update lightmap");
        return 0;
    }
}