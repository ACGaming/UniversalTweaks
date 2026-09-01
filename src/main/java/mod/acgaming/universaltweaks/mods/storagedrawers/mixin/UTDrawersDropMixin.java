package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;

import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Remove the position data from the dropped item so that it stacks with others
@Mixin(value = BlockDrawers.class, remap = false)
public abstract class UTDrawersDropMixin
{

    @ModifyExpressionValue(
        method = "getDrops",
        at = @At(
            value = "INVOKE",
            target = "Lcom/jaquadro/minecraft/storagedrawers/block/BlockDrawers;getMainDrop(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/item/ItemStack;"
        )
    )
    private ItemStack utRemoveDropPosition(ItemStack original)
    {
        if (!original.hasTagCompound()) return original;

        NBTTagCompound data = original.getTagCompound();
        if (!data.hasKey("tile", Constants.NBT.TAG_COMPOUND)) return original;

        NBTTagCompound tileData = data.getCompoundTag("tile");
        tileData.removeTag("x");
        tileData.removeTag("y");
        tileData.removeTag("z");

        return original;
    }
}
