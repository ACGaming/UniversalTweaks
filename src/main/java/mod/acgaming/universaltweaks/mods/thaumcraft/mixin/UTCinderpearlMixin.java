package mod.acgaming.universaltweaks.mods.thaumcraft.mixin;

import javax.annotation.Nonnull;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import thaumcraft.common.blocks.world.plants.BlockPlantCinderpearl;

// Courtesy of Turkey9002
@Mixin(BlockPlantCinderpearl.class)
public class UTCinderpearlMixin extends BlockBush
{
    public UTCinderpearlMixin()
    {
        super();
    }

    @Nonnull
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos)
    {
        if (UTConfig.MOD_INTEGRATION.THAUMCRAFT.utTCFlowerBoundingBoxToggle) return BUSH_AABB.offset(state.getOffset(world, pos));
        else return BUSH_AABB;
    }
}