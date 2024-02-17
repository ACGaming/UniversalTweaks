package mod.acgaming.universaltweaks.tweaks.misc.chat;

import net.minecraft.util.text.*;

public class TextComponentCount extends TextComponentBase {

    private final int count;

    public TextComponentCount(int count) {
        this.count = count;
    }

    @Override
    public ITextComponent setStyle(Style style) {
        style.setColor(TextFormatting.GRAY);
        return super.setStyle(style);
    }

    @Override
    public String getUnformattedComponentText() {
        return String.format(" (%s)", this.count);
    }

    @Override
    public TextComponentCount createCopy() {
        TextComponentCount textComponentCount = new TextComponentCount(this.count);
        textComponentCount.setStyle(this.getStyle().createShallowCopy());

        for (ITextComponent itextcomponent : this.getSiblings())
        {
            textComponentCount.appendSibling(itextcomponent.createCopy());
        }

        return textComponentCount;
    }
}
