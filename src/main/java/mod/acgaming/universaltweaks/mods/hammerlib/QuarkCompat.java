package mod.acgaming.universaltweaks.mods.hammerlib;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import com.zeitheron.hammercore.client.utils.ItemColorHelper;
import vazkii.quark.misc.feature.ColorRunes;

public class QuarkCompat
{
    private static final boolean isQuarkLoaded = Loader.isModLoaded("quark");

    public static int getRuneColor(ItemStack target)
    {
        if (!isQuarkLoaded)
        {
            return ItemColorHelper.DEFAULT_GLINT_COLOR;
        }
        ColorRunes.setTargetStack(target);
        return ColorRunes.getColor(ItemColorHelper.DEFAULT_GLINT_COLOR);
    }
}
