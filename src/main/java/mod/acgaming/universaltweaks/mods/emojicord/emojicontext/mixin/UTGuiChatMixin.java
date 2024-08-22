package mod.acgaming.universaltweaks.mods.emojicord.emojicontext.mixin;

import net.minecraft.client.gui.GuiChat;

import mod.acgaming.universaltweaks.mods.emojicord.emojicontext.EmojiFontRendererContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiChat.class)
public class UTGuiChatMixin
{
    @ModifyVariable(method = "drawScreen", at = @At("HEAD"), index = 3, ordinal = 0, name = "partialTicks", argsOnly = true)
    private float utEmojicordPreDrawScreen(float partialTicks)
    {
        EmojiFontRendererContext.isChatInput = true;
        return partialTicks;
    }

    @ModifyVariable(method = "drawScreen", at = @At("RETURN"), index = 3, ordinal = 0, name = "partialTicks", argsOnly = true)
    private float utEmojicordPostDrawScreen(float partialTicks)
    {
        EmojiFontRendererContext.isChatInput = false;
        return partialTicks;
    }
}