package mod.acgaming.universaltweaks.tweaks.misc.chat.bed.mixin;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiTextField;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiChat.class)
public interface UTGuiChatAccessor
{
    @Accessor("inputField")
    GuiTextField getInputField();
}
