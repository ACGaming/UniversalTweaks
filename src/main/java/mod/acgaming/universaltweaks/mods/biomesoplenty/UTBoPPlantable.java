package mod.acgaming.universaltweaks.mods.biomesoplenty;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

/**
 * Minimal {@link IPlantable} used to probe {@link net.minecraft.block.Block#canSustainPlant}.
 */
public class UTBoPPlantable implements IPlantable
{
    public static final IPlantable PLAINS = of(EnumPlantType.Plains);

    private final EnumPlantType plantType;

    private UTBoPPlantable(EnumPlantType plantType)
    {
        this.plantType = plantType;
    }

    public static IPlantable of(EnumPlantType plantType)
    {
        return new UTBoPPlantable(plantType);
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return this.plantType;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return Blocks.AIR.getDefaultState();
    }
}
