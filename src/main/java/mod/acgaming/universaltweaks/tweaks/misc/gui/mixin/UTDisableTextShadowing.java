package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// Courtesy of WaitingIdly
@Mixin(value = FontRenderer.class)
public class UTDisableTextShadowing
{
    @ModifyVariable(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"))
    private boolean utDisableTextShadowing(boolean original)
    {
        if (!UTConfigTweaks.MISC.utDisableTextShadow) return original;
        return false;
    }
}
