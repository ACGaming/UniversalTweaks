package mod.acgaming.universaltweaks.mods.netherchest.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import netherchest.common.inventory.ExtendedItemStackHandler;
import netherchest.common.tileentity.TileEntityNetherChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = TileEntityNetherChest.class, remap = false)
public abstract class UTTileEntityNetherChestMixin extends TileEntity
{
    @Shadow
    ExtendedItemStackHandler itemHandler;

    /**
     * @author DupeFix Project
     * @reason Fix Duplication Glitch
     */
    @Overwrite
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        this.invalidate();
        if (itemHandler != null && !world.isRemote)
        {
            for (int i = 0; i < itemHandler.getSlots(); i++)
            {
                ItemStack stack = itemHandler.getStackInSlot(i);
                if (!stack.isEmpty())
                {
                    if (stack.getCount() > stack.getMaxStackSize())
                    {
                        ItemStack itemx1 = stack.copy();
                        itemx1.setCount(1);
                        for (int j = 0; j < stack.getCount(); j++)
                        {
                            Block.spawnAsEntity(world, pos, itemx1.copy());
                        }
                    }
                    else
                    {
                        Block.spawnAsEntity(world, pos, stack);
                    }
                }
            }
        }
        world.setTileEntity(pos, null);
    }
}