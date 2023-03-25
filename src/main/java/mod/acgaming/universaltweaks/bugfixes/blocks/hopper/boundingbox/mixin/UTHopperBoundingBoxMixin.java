package mod.acgaming.universaltweaks.bugfixes.blocks.hopper.boundingbox.mixin;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Courtesy of RWTema
// Sue here first: https://github.com/SlimeKnights/TinkersConstruct/blob/1.12/src/main/java/slimeknights/tconstruct/gadgets/block/BlockWoodenHopper.java#L53
@Mixin(BlockHopper.class)
public abstract class UTHopperBoundingBoxMixin extends BlockContainer
{
    @Unique
    private static final EnumMap<EnumFacing, List<AxisAlignedBB>> AABB_MAP;

    static
    {
        List<AxisAlignedBB> commonAABBs = ImmutableList.of(new AxisAlignedBB(0, 0.625, 0, 1, 1, 1), new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.625, 0.75));
        AABB_MAP = Stream.of(EnumFacing.values()).filter(t -> t != EnumFacing.UP).collect(Collectors.toMap(a -> a, a -> new ArrayList<>(commonAABBs), (u, v) -> {throw new IllegalStateException();}, () -> new EnumMap<>(EnumFacing.class)));
        AABB_MAP.get(EnumFacing.DOWN).add(new AxisAlignedBB(0.375, 0, 0.375, 0.625, 0.25, 0.625));
        AABB_MAP.get(EnumFacing.NORTH).add(new AxisAlignedBB(0.375, 0.25, 0, 0.625, 0.5, 0.25));
        AABB_MAP.get(EnumFacing.SOUTH).add(new AxisAlignedBB(0.375, 0.25, 0.75, 0.625, 0.5, 1));
        AABB_MAP.get(EnumFacing.WEST).add(new AxisAlignedBB(0, 0.25, 0.375, 0.25, 0.5, 0.625));
        AABB_MAP.get(EnumFacing.EAST).add(new AxisAlignedBB(0.75, 0.25, 0.375, 1, 0.5, 0.625));
    }

    public UTHopperBoundingBoxMixin(Material materialIn)
    {
        super(materialIn);
    }

    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull Vec3d start, @Nonnull Vec3d end)
    {
        return AABB_MAP.get(blockState.getValue(BlockHopper.FACING)).stream().map(bb -> rayTrace(pos, start, end, bb)).anyMatch(Objects::nonNull) ? super.collisionRayTrace(blockState, worldIn, pos, start, end) : null;
    }
}