package mod.acgaming.universaltweaks.mods.emojicord.emojicontext.mixin;

import net.minecraft.client.gui.GuiNewChat;

import mod.acgaming.universaltweaks.mods.emojicord.emojicontext.EmojiFontRendererContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiNewChat.class)
public class UTGuiNewChatMixin
{
    @ModifyVariable(method = "drawChat", at = @At("HEAD"), index = 1, ordinal = 0, name = "updateCounter", argsOnly = true)
    private int utEmojicordPreDrawChat(int updateCounter)
    {
        EmojiFontRendererContext.isChatMessage = true;
        return updateCounter;
    }

    @ModifyVariable(method = "drawChat", at = @At("RETURN"), index = 1, ordinal = 0, name = "updateCounter", argsOnly = true)
    private int utEmojicordPostDrawChat(int updateCounter)
    {
        EmojiFontRendererContext.isChatMessage = false;
        return updateCounter;
    }
}