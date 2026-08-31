package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.Constants;

import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Remove the position data from the dropped item so that it stacks with others
@Mixin(value = BlockDrawers.class, remap = false)
public abstract class UTDrawersDropMixin
{

    @Inject(method = "getDrops", at = @At("TAIL"))
    private void utRemoveDropPosition(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos,
                                      IBlockState state, int fortune, CallbackInfo ci)
    {
        if (drops.isEmpty()) return;

        ItemStack drop = drops.get(drops.size() - 1);
        if (!drop.hasTagCompound()) return;

        NBTTagCompound data = drop.getTagCompound();
        if (!data.hasKey("tile", Constants.NBT.TAG_COMPOUND)) return;

        NBTTagCompound tileData = data.getCompoundTag("tile");
        tileData.removeTag("x");
        tileData.removeTag("y");
        tileData.removeTag("z");
    }
}
