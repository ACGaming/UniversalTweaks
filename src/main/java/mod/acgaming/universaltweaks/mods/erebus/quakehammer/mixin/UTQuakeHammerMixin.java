package mod.acgaming.universaltweaks.mods.erebus.quakehammer.mixin;

import erebus.core.handler.configs.ConfigHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraftforge.common.config.Configuration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of WaitingIdly
@Mixin(value = ConfigHandler.class, remap = false)
public class UTQuakeHammerMixin
{
    @Shadow
    public Configuration config;

    @Shadow
    public float getHammer_renderSizeChargedMultiplier;

    @Redirect(method = "syncConfigs", at = @At(value = "FIELD", target = "Lerebus/core/handler/configs/ConfigHandler;hammer_renderSize:F", ordinal = 1))
    private void utFixRenderSize(ConfigHandler configHandler, float original)
    {
        if (!UTConfigMods.EREBUS.utFixQuakeHammerTexture) return;
        this.getHammer_renderSizeChargedMultiplier = this.config.getFloat("Quake Hammer charged render size", "Quake Hammer", 0.03F, 0.0F, Float.MAX_VALUE, "");
    }
}