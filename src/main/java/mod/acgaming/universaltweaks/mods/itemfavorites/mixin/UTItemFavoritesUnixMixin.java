package mod.acgaming.universaltweaks.mods.itemfavorites.mixin;

import mrunknown404.itemfav.utils.LockHandler;

import java.io.File;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = LockHandler.class, remap = false)
public abstract class UTItemFavoritesUnixMixin
{
    // Replaces "\\" (char 92) with system file separator to fix Unix compatibility
    @ModifyConstant(method = "saveToFile", constant = @Constant(intValue = 92))
    private static int replaceBackslashInSave(int constant)
    {
        return File.separatorChar;
    }

    @ModifyConstant(method = "readFromFile", constant = @Constant(intValue = 92))
    private static int replaceBackslashInRead(int constant)
    {
        return File.separatorChar;
    }
}