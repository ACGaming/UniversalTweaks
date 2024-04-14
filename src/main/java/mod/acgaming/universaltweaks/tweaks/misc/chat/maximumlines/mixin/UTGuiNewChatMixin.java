package mod.acgaming.universaltweaks.tweaks.misc.chat.maximumlines.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GuiNewChat.class)
public class UTGuiNewChatMixin
{
    @ModifyConstant(method = "setChatLine", constant = @Constant(intValue = 100))
    public int utAdvancementToast(int original)
    {
        // Prevents negative config numbers generating an NPE
        if (UTConfigTweaks.MISC.utChatLines < 0) return 0;
        return UTConfigTweaks.MISC.utChatLines;
    }
}