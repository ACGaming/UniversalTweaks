package mod.acgaming.universaltweaks.mods.wopper.mixin;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import de.ellpeck.wopper.BlockWopper;
import mod.acgaming.universaltweaks.bugfixes.blocks.hopper.boundingbox.UTHopperBoundingBox;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockWopper.class)
public abstract class UTWopperBoundingBoxMixin extends BlockContainer
{
    public UTWopperBoundingBoxMixin(Material material)
    {
        super(material);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Vec3d start, @Nonnull Vec3d end)
    {
        return UTHopperBoundingBox.AABB_MAP.get(blockState.getValue(BlockHopper.FACING)).stream().map(bb -> rayTrace(pos, start, end, bb)).anyMatch(Objects::nonNull) ? super.collisionRayTrace(blockState, world, pos, start, end) : null;
    }
}
