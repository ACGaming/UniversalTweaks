package mod.acgaming.universaltweaks.tweaks.dyeblending;

import net.minecraft.item.EnumDyeColor;

// Courtesy of Darkhax
public class UTDyeBlending
{
    public static final EnumDyeColor[][] BLENDABLE_DYE_COLORS = new EnumDyeColor[16][16];

    static
    {
        BLENDABLE_DYE_COLORS[15][8] = EnumDyeColor.SILVER;
        BLENDABLE_DYE_COLORS[15][4] = EnumDyeColor.LIGHT_BLUE;
        BLENDABLE_DYE_COLORS[15][2] = EnumDyeColor.LIME;
        BLENDABLE_DYE_COLORS[15][1] = EnumDyeColor.PINK;
        BLENDABLE_DYE_COLORS[15][0] = EnumDyeColor.GRAY;
        BLENDABLE_DYE_COLORS[11][1] = EnumDyeColor.ORANGE;
        BLENDABLE_DYE_COLORS[9][5] = EnumDyeColor.MAGENTA;
        BLENDABLE_DYE_COLORS[8][15] = EnumDyeColor.SILVER;
        BLENDABLE_DYE_COLORS[5][9] = EnumDyeColor.MAGENTA;
        BLENDABLE_DYE_COLORS[4][15] = EnumDyeColor.LIGHT_BLUE;
        BLENDABLE_DYE_COLORS[4][2] = EnumDyeColor.CYAN;
        BLENDABLE_DYE_COLORS[4][1] = EnumDyeColor.PURPLE;
        BLENDABLE_DYE_COLORS[2][15] = EnumDyeColor.LIME;
        BLENDABLE_DYE_COLORS[2][4] = EnumDyeColor.CYAN;
        BLENDABLE_DYE_COLORS[1][15] = EnumDyeColor.PINK;
        BLENDABLE_DYE_COLORS[1][11] = EnumDyeColor.ORANGE;
        BLENDABLE_DYE_COLORS[1][4] = EnumDyeColor.PURPLE;
        BLENDABLE_DYE_COLORS[0][15] = EnumDyeColor.GRAY;
    }

    public static EnumDyeColor getBlendedColor(EnumDyeColor first, EnumDyeColor second)
    {
        return BLENDABLE_DYE_COLORS[first.getDyeDamage()][second.getDyeDamage()];
    }
}