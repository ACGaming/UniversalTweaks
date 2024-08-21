package mod.acgaming.universaltweaks.mods.emojicord.emojicontext.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import mod.acgaming.universaltweaks.mods.emojicord.emojicontext.EmojiFontRendererContext;
import net.minecraft.client.gui.GuiChat;

@Mixin(GuiChat.class)
public class UTGuiChatMixin
{

    @ModifyVariable(method = "drawScreen", at = @At("HEAD"), index = 3, ordinal = 0, name = "partialTicks")
    private float pre_drawScreen(float partialTicks)
    {
        EmojiFontRendererContext.isChatInput = true;
        return partialTicks;
    }

    @ModifyVariable(method = "drawScreen", at = @At("RETURN"), index = 3, ordinal = 0, name = "partialTicks")
    private float post_drawScreen(float partialTicks)
    {
        EmojiFontRendererContext.isChatInput = false;
        return partialTicks;
    }

}
