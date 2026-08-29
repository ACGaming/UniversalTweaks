package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import net.minecraft.client.gui.GuiTextField;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiTextField.class)
public abstract class UTTextFieldBorderColor
{
    @ModifyConstant(method = "drawTextBox", constant = @Constant(intValue = 0xFFA0A0A0))
    private int utFocusedBorderColor(int constant)
    {
        if (!UTConfigTweaks.MISC.utTextFieldBorderColor) return constant;
        GuiTextField self = (GuiTextField) (Object) this;
        return self.isFocused() ? 0xFFFFFFFF : constant;
    }
}