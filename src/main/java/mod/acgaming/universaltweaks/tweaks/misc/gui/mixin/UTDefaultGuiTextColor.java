package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.client.gui.FontRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Courtesy of youyihj
@Mixin(FontRenderer.class)
public class UTDefaultGuiTextColor
{
    @ModifyVariable(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"), argsOnly = true)
    private int utSetDefaultGuiTextColor(int color)
    {
        if (color == 0x404040) {
            return Integer.parseInt(UTConfigTweaks.MISC.utDefaultGuiTextColor, 16);
        } else {
            return color;
        }
    }
}
