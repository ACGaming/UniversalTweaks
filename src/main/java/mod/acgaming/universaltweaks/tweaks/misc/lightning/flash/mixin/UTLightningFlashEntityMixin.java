package mod.acgaming.universaltweaks.tweaks.misc.lightning.flash.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class UTLightningFlashEntityMixin
{
    @Redirect(method = "updateLightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getLastLightningBolt()I"))
    public int utLightningFlash(World instance)
    {
        if (!UTConfigTweaks.MISC.LIGHTNING.utLightningFlashToggle) return instance.getLastLightningBolt();
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLightningFlashEntity ::: Update lightmap");
        return 0;
    }
}