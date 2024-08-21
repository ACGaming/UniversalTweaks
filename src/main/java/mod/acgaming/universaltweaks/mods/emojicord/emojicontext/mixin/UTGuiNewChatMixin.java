package mod.acgaming.universaltweaks.mods.emojicord.emojicontext.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import mod.acgaming.universaltweaks.mods.emojicord.emojicontext.EmojiFontRendererContext;
import net.minecraft.client.gui.GuiNewChat;

@Mixin(GuiNewChat.class)
public class UTGuiNewChatMixin
{

    @ModifyVariable(method = "drawChat", at = @At("HEAD"), index = 1, ordinal = 0, name = "updateCounter")
    private int pre_drawChat(int updateCounter)
    {
        EmojiFontRendererContext.isChatMessage = true;
        return updateCounter;
    }

    @ModifyVariable(method = "drawChat", at = @At("RETURN"), index = 1, ordinal = 0, name = "updateCounter")
    private int post_drawChat(int updateCounter)
    {
        EmojiFontRendererContext.isChatMessage = false;
        return updateCounter;
    }

}
