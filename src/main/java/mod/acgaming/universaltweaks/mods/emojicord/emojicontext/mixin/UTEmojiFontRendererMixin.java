package mod.acgaming.universaltweaks.mods.emojicord.emojicontext.mixin;

import java.util.EnumSet;

import mod.acgaming.universaltweaks.mods.emojicord.emojicontext.EmojiFontRendererContext;
import net.teamfruit.emojicord.EmojicordConfig;
import net.teamfruit.emojicord.emoji.EmojiContext;
import net.teamfruit.emojicord.emoji.EmojiContext.EmojiContextAttribute;
import net.teamfruit.emojicord.emoji.EmojiFontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = EmojiFontRenderer.class, remap = false)
public class UTEmojiFontRendererMixin
{
    @Shadow(remap = false)
    private static EmojiContext CurrentContext;

    /**
     * @author Meldexun
     * @reason Emojicord tweak
     */
    @Overwrite(remap = false)
    public static String updateEmojiContext(final String text)
    {
        if (EmojicordConfig.spec.isAvailable() && EmojicordConfig.RENDER.renderEnabled.get())
        {
            final EnumSet<EmojiContextAttribute> attributes = EnumSet.noneOf(EmojiContextAttribute.class);
            if (EmojiFontRendererContext.isChatInput) attributes.add(EmojiContextAttribute.CHAT_INPUT);
            if (EmojiFontRendererContext.isChatMessage) attributes.add(EmojiContextAttribute.CHAT_MESSAGE);
            CurrentContext = EmojiContext.EmojiContextCache.instance.getContext(text, attributes);
            return CurrentContext.text;
        }
        CurrentContext = null;
        return text;
    }
}