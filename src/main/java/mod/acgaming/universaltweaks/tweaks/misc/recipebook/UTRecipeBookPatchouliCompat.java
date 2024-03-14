package mod.acgaming.universaltweaks.tweaks.misc.recipebook;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import vazkii.patchouli.common.base.PatchouliConfig;

public class UTRecipeBookPatchouliCompat
{
    public static boolean patchouliInventoryButtonBook()
    {
        return Loader.instance().hasReachedState(LoaderState.AVAILABLE) && Loader.isModLoaded("patchouli") && !PatchouliConfig.inventoryButtonBook.isEmpty();
    }
}