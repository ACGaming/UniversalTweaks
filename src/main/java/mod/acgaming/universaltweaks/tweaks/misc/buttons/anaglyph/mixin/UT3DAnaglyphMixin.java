package mod.acgaming.universaltweaks.tweaks.misc.buttons.anaglyph.mixin;

import net.minecraft.client.gui.GuiVideoSettings;
import net.minecraft.client.settings.GameSettings;

import com.llamalad7.mixinextras.lib.apache.commons.ArrayUtils;
import mod.acgaming.universaltweaks.core.Coremods;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiVideoSettings.class)
public class UT3DAnaglyphMixin
{
    @Mutable
    @Shadow
    @Final
    private static GameSettings.Options[] VIDEO_OPTIONS;

    @Inject(method = "initGui", at = @At(value = "HEAD"))
    public void ut3DAnaglyph(CallbackInfo ci)
    {
        if (Coremods.OPTIFINE.isLoaded()) return;
        for (int i = 0; i < VIDEO_OPTIONS.length; i++)
        {
            if (VIDEO_OPTIONS[i] == GameSettings.Options.ANAGLYPH)
            {
                VIDEO_OPTIONS = ArrayUtils.remove(VIDEO_OPTIONS, i);
                break;
            }
        }
    }
}