package mod.acgaming.universaltweaks.tweaks.misc.chat.mixin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public abstract class UTCompactMessageMixin
{
    @Unique
    private final Pattern universalTweaks$matchPattern = Pattern.compile("(?:§7)?+\\s+\\(+\\d+\\)");

    @Shadow
    @Final
    private final List<ChatLine> drawnChatLines = new ArrayList<>();

    @Shadow
    @Final
    private final List<ChatLine> chatLines = new ArrayList<>();

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    public abstract float getChatScale();

    @Shadow
    public abstract int getChatWidth();

    @Inject(method = "setChatLine", at = @At("HEAD"))
    public void utCompactMessage(ITextComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.CHAT.utCompactMessagesToggle) return;
        int count = 1;
        int chatSize = MathHelper.floor(this.getChatWidth() / this.getChatScale());
        List<ITextComponent> splittedText = GuiUtilRenderComponents.splitText(chatComponent, chatSize, this.mc.fontRenderer, false, false);
        ITextComponent textComponent = splittedText.get(splittedText.size() - 1);
        if (!this.drawnChatLines.isEmpty() && !this.chatLines.isEmpty())
        {
            ChatLine oldDrawnChatLine = this.drawnChatLines.get(0);
            ChatLine oldChatLine = this.chatLines.get(0);
            if (universalTweaks$isMessageEqual(oldDrawnChatLine.getChatComponent().createCopy(), textComponent.createCopy()))
            {
                if (!oldDrawnChatLine.getChatComponent().getSiblings().isEmpty())
                {
                    for (ITextComponent sibling : oldDrawnChatLine.getChatComponent().getSiblings())
                    {
                        if (universalTweaks$matchPattern.matcher(sibling.getUnformattedComponentText()).matches())
                        {
                            count += Integer.parseInt(sibling.getUnformattedComponentText().replaceAll("(?:§7)?\\D?", ""));
                            break;
                        }
                    }
                }
                if ((oldDrawnChatLine.equals(oldDrawnChatLine) || splittedText.contains(oldDrawnChatLine.getChatComponent())) && oldChatLine.getChatComponent().getUnformattedComponentText().equals(chatComponent.getUnformattedComponentText()))
                {
                    this.drawnChatLines.remove(oldDrawnChatLine);
                    this.chatLines.remove(oldChatLine);
                }
                // this.drawnChatLines.removeIf(chatLine1 -> splittedText.contains(chatLine1.getChatComponent()) || chatLine1.equals(chatLine));
                // this.chatLines.removeIf(chatLine1 -> chatLine1.getChatComponent().getUnformattedComponentText().equals(chatComponent.getUnformattedComponentText()));
                ITextComponent textComponentCount = new TextComponentString(" (" + count + ")").setStyle(new Style().setColor(TextFormatting.GRAY));
                textComponentCount.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString(I18n.format("msg.universaltweaks.compactmessages.count", count))));
                chatComponent.appendSibling(textComponentCount);

            }
        }
    }

    @Unique
    private boolean universalTweaks$isMessageEqual(ITextComponent msg1, ITextComponent msg2)
    {
        boolean isEqual = false;
        if (msg1.equals(msg2) || msg1.getUnformattedText().equals(msg2.getUnformattedText()))
        {
            isEqual = true;
        }
        else
        {
            if (!msg1.getSiblings().isEmpty())
            {
                for (int i = 0; i < msg1.getSiblings().size(); i++)
                {
                    ITextComponent sibling = msg1.getSiblings().get(i);
                    if (universalTweaks$matchPattern.matcher(sibling.getUnformattedComponentText()).matches())
                    {
                        msg1.getSiblings().remove(sibling);
                    }
                }
                if (msg1.equals(msg2) || msg1.getUnformattedText().equals(msg2.getUnformattedText()))
                {
                    isEqual = true;
                }
            }
        }
        return isEqual;
    }
}
