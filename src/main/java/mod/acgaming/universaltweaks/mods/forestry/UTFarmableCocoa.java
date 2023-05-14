package mod.acgaming.universaltweaks.mods.forestry;

import net.minecraft.block.BlockCocoa;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import forestry.core.utils.BlockUtil;
import forestry.farming.logic.farmables.FarmableAgingCrop;
import forestry.farming.logic.farmables.FarmableCocoa;

public class UTFarmableCocoa extends FarmableAgingCrop
{
    public UTFarmableCocoa()
    {
        super(new ItemStack(FarmableCocoa.COCOA_SEED, 1, 3), Blocks.COCOA, new ItemStack(FarmableCocoa.COCOA_SEED, 1, FarmableCocoa.COCOA_META), BlockCocoa.AGE, 2, 0);
    }

    @Override
    public boolean plantSaplingAt(EntityPlayer player, ItemStack germling, World world, BlockPos pos)
    {
        return BlockUtil.tryPlantCocoaPod(world, pos);
    }
}