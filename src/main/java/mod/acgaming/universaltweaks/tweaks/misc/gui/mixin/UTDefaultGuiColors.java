package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import net.minecraft.client.gui.FontRenderer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Courtesy of youyihj
@Mixin(FontRenderer.class)
public class UTDefaultGuiColors
{
    @ModifyVariable(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), argsOnly = true)
    private int utSetDefaultGuiColors(int color)
    {
        switch (color)
        {
            case 0x404040:
                return utParseColor(UTConfigTweaks.MISC.utDefaultGuiTextColor, color);
            case 0xE0E0E0:
            case 0xFFFFA0:
            case 0xA0A0A0:
                String[] buttonColors = UTConfigTweaks.MISC.utDefaultGuiButtonColor.split("\\|");
                int index = color == 0xE0E0E0 ? 0 : color == 0xFFFFA0 ? 1 : 2;
                return buttonColors.length > index ? utParseColor(buttonColors[index], color) : color;
            default:
                return color;
        }
    }

    @Unique
    private static int utParseColor(String hex, int fallback)
    {
        try
        {
            return Integer.parseInt(hex.trim(), 16);
        }
        catch (NumberFormatException e)
        {
            return fallback;
        }
    }
}