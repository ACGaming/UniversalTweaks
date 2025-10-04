package mod.acgaming.universaltweaks.tweaks.misc.buttons.anaglyph.optifine.mixin;

import com.llamalad7.mixinextras.lib.apache.commons.ArrayUtils;
import net.minecraft.client.settings.GameSettings.Options;
import net.optifine.gui.GuiOtherSettingsOF;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = GuiOtherSettingsOF.class, remap = false)
public class UT3DAnaglyphOFMixin
{

    @Shadow
    private static Options[] enumOptions;

    static
    {
        for (int i = 0; i < enumOptions.length; i++)
        {
            if (enumOptions[i] == Options.ANAGLYPH)
            {
                enumOptions = ArrayUtils.remove(enumOptions, i);
                break;
            }
        }
    }
}