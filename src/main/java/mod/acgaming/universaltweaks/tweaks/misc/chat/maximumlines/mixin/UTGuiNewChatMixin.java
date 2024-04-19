package mod.acgaming.universaltweaks.tweaks.misc.chat.maximumlines.mixin;

import net.minecraft.client.gui.GuiNewChat;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of WaitingIdly
@Mixin(value = GuiNewChat.class)
public class UTGuiNewChatMixin
{
    @ModifyConstant(method = "setChatLine", constant = @Constant(intValue = 100))
    public int utAdvancementToast(int original)
    {
        // Prevents negative config numbers generating an NPE
        if (UTConfigTweaks.MISC.CHAT.utChatLines < 0) return 0;
        return UTConfigTweaks.MISC.CHAT.utChatLines;
    }
}