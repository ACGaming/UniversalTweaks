package mod.acgaming.universaltweaks.tweaks.misc.advancements.guisize;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
public class UTAdvancementInfo
{
    public static final int DEFAULT_WIDTH = 252;
    public static final int DEFAULT_HEIGHT = 140;
    private static final int PADDING = 9;
    private static final int HEIGHT_MULT = 28;
    private static final int WIDTH_MULT = 32;

    private static int utStepwiseWidth(int width)
    {
        return width / WIDTH_MULT * WIDTH_MULT;
    }

    private static int utStepwiseHeight(int height)
    {
        return height / HEIGHT_MULT * HEIGHT_MULT;
    }

    public static int utPageWidth(int externalWidth)
    {
        return utStepwiseWidth(externalWidth - UTConfigTweaks.MISC.ADVANCEMENTS.utHorizontalMargin * 2);
    }

    public static int utPageWidth(int externalWidth, int padding)
    {
        return utPageWidth(externalWidth) - padding * PADDING;
    }

    public static int utPageHeight(int externalHeight)
    {
        return utStepwiseHeight(externalHeight - UTConfigTweaks.MISC.ADVANCEMENTS.utVerticalMargin * 2);
    }

    public static int utPageHeight(int externalHeight, int padding)
    {
        return utPageHeight(externalHeight) - padding * PADDING;
    }

    private static int utTabCountForWidth(int width)
    {
        return utPageWidth(width) / WIDTH_MULT;
    }

    private static int utTabCountForHeight(int height)
    {
        return utPageHeight(height) / HEIGHT_MULT;
    }

    public static int utTabCountForSide(int width, int height, boolean isVertical)
    {
        return isVertical ? utTabCountForHeight(height) : utTabCountForWidth(width);
    }

    public static int utMaximumTabCountPerPage(int width, int height)
    {
        return (utTabCountForWidth(width) + utTabCountForHeight(height)) * 2;
    }
}
