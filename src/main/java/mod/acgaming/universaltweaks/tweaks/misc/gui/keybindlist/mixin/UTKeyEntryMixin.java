package mod.acgaming.universaltweaks.tweaks.misc.gui.keybindlist.mixin;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiKeyBindingList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GuiKeyBindingList.KeyEntry.class)
public abstract class UTKeyEntryMixin
{
    @Redirect(method = "drawEntry", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I"))
    public int utIndentEntryText(FontRenderer f, String text, int x, int y, int color)
    {
        return f.drawString(text, Math.max(x, 5), y, color);
    }
}