package mod.acgaming.universaltweaks.bugfixes.blocks.hopper.boundingbox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class UTHopperBoundingBox
{
    public static final Map<EnumFacing, List<AxisAlignedBB>> AABB_MAP;

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
}
