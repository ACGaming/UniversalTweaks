package mod.acgaming.universaltweaks.mods.erebus.quakehammer.mixin;

import erebus.core.handler.configs.ConfigHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of WaitingIdly
@Mixin(value = ConfigHandler.class, remap = false)
public class UTQuakeHammerMixin
{
    @Redirect(method = "syncConfigs", at = @At(value = "FIELD", target = "Lerebus/core/handler/configs/ConfigHandler;hammer_renderSize:F", ordinal = 1))
    private void utFixRenderSize(ConfigHandler configHandler, float original)
    {
        if (!UTConfigMods.EREBUS.utFixQuakeHammerTexture) return;
        configHandler.getHammer_renderSizeChargedMultiplier = original;
    }
}