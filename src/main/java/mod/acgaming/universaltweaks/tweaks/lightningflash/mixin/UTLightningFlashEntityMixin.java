package mod.acgaming.universaltweaks.tweaks.lightningflash.mixin;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class UTLightningFlashEntityMixin
{
    @Redirect(method = "updateLightmap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getLastLightningBolt()I"))
    public int utLightningFlash(World instance)
    {
        if (!UTConfig.TWEAKS_MISC.utLightningFlashToggle) return instance.getLastLightningBolt();
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLightningFlashEntity ::: Update lightmap");
        return 0;
    }
}