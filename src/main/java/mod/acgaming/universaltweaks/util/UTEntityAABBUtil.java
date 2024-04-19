package mod.acgaming.universaltweaks.util;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import net.minecraft.entity.Entity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

/**
 * Util class mirroring a few AABB functions.
 * Necessary for lessCollisions tweak.
 * Credit goes to Charles445, this is basically straight from RLTweaker's WorldRadiusUtil.
 */
public class UTEntityAABBUtil
{
    /**
     * Copy of {@link World#getEntitiesInAABBexcluding} using variable entity radius.
     */
    public static List<Entity> getEntitiesInAABBexcluding(World world, @Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate, double size)
    {
        List<Entity> list = Lists.newArrayList();
        int j2 = MathHelper.floor((boundingBox.minX - size) / 16.0D);
        int k2 = MathHelper.floor((boundingBox.maxX + size) / 16.0D);
        int l2 = MathHelper.floor((boundingBox.minZ - size) / 16.0D);
        int i3 = MathHelper.floor((boundingBox.maxZ + size) / 16.0D);

        for (int j3 = j2; j3 <= k2; ++j3)
        {
            for (int k3 = l2; k3 <= i3; ++k3)
            {
                Chunk chunk = world.getChunkProvider().getLoadedChunk(j3, k3);
                if (chunk != null)
                {
                    getEntitiesWithinAABBForEntity(chunk, entityIn, boundingBox, list, predicate, size);
                }
            }
        }

        return list;
    }

    /**
     * Copy of {@link Chunk#getEntitiesWithinAABBForEntity} using variable entity radius.
     */
    public static void getEntitiesWithinAABBForEntity(Chunk chunk, @Nullable Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> filter, double size)
    {
        ClassInheritanceMultiMap<Entity>[] entityLists = chunk.getEntityLists();
        int i = MathHelper.floor((aabb.minY - size) / 16.0D);
        int j = MathHelper.floor((aabb.maxY + size) / 16.0D);
        i = MathHelper.clamp(i, 0, entityLists.length - 1);
        j = MathHelper.clamp(j, 0, entityLists.length - 1);

        for (int k = i; k <= j; ++k)
        {
            if (!entityLists[k].isEmpty())
            {
                for (Entity entity : entityLists[k])
                {
                    if (entity.getEntityBoundingBox().intersects(aabb) && entity != entityIn)
                    {
                        if (filter == null || filter.apply(entity))
                        {
                            listToFill.add(entity);
                        }

                        Entity[] aentity = entity.getParts();

                        if (aentity != null)
                        {
                            for (Entity entity1 : aentity)
                            {
                                if (entity1 != entityIn && entity1.getEntityBoundingBox().intersects(aabb) && (filter == null || filter.apply(entity1)))
                                {
                                    listToFill.add(entity1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
