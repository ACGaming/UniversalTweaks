package mod.acgaming.universaltweaks.util;

import java.util.List;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

// Courtesy of TheCodex6824
public class UTRayTraceEntity
{
    @Nullable
    public static Entity rayTraceEntity(EntityLivingBase user, double reach)
    {
        return rayTraceEntity(user, reach, e -> true);
    }

    @Nullable
    public static Entity rayTraceEntityAsPlayer(EntityPlayer player)
    {
        double reach = player.getEntityAttribute(EntityPlayer.REACH_DISTANCE).getAttributeValue();
        reach = player.isCreative() ? reach : reach - 0.5F;
        return rayTraceEntity(player, reach, e -> true);
    }

    @Nullable
    public static Entity rayTraceEntity(EntityLivingBase user, double reach, Predicate<Entity> acceptor)
    {
        Vec3d eyes = user.getPositionEyes(1.0F);
        Vec3d look = user.getLook(1.0F);
        Vec3d extended = eyes.add(look.x * reach, look.y * reach, look.z * reach);
        RayTraceResult blockCheck = user.getEntityWorld().rayTraceBlocks(eyes, extended, false, true, true);
        reach = blockCheck != null ? blockCheck.hitVec.distanceTo(eyes) : reach;
        List<Entity> list = user.getEntityWorld().getEntitiesInAABBexcluding(user, user.getEntityBoundingBox().expand(look.x * reach, look.y * reach, look.z * reach).grow(1.0, 1.0, 1.0), Predicates.and(entity -> entity != null && entity.canBeCollidedWith(), Predicates.and(EntitySelectors.NOT_SPECTATING, acceptor::test)));
        double dist = reach;
        Entity ret = null;
        for (Entity entity : list)
        {
            AxisAlignedBB aabb = entity.getEntityBoundingBox().grow(entity.getCollisionBorderSize());
            if (aabb.contains(eyes))
            {
                ret = entity;
                break;
            }
            else
            {
                RayTraceResult res = aabb.calculateIntercept(eyes, extended);
                if (res != null)
                {
                    double newDist = eyes.distanceTo(res.hitVec);
                    if (newDist < dist)
                    {
                        ret = entity;
                        dist = newDist;
                    }
                }
            }
        }
        return ret;
    }
}