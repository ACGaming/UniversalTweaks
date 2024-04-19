package mod.acgaming.universaltweaks.tweaks.misc.chat.keepsentmessages.mixin;

import net.minecraft.client.gui.GuiNewChat;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = GuiNewChat.class)
public class UTGuiNewChatMixin
{
    @Inject(method = "clearChatMessages", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V", ordinal = 2), cancellable = true)
    public void utKeepSentMessageHistory(boolean clearHistory, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.CHAT.utKeepSentMessageHistory) return;
        ci.cancel();
    }
}