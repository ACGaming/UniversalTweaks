package mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.lesscollisions.mixin;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import com.google.common.base.Predicate;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.UTEntityRadiusCheck;
import mod.acgaming.universaltweaks.util.UTEntityAABBUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EntityLivingBase.class)
public class UTEntityLivingBaseMixin
{
    @WrapOperation(method = "collideWithNearbyEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getEntitiesInAABBexcluding(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;"))
    private List<Entity> utReducedRadiusAABBCall(World instance, Entity entityIn, AxisAlignedBB aabb, Predicate<? super Entity> predicate, Operation<List<Entity>> original)
    {
        final double ORIGINAL_MAX_ENTITY_RADIUS = 2.0D;
        if (World.MAX_ENTITY_RADIUS != ORIGINAL_MAX_ENTITY_RADIUS && UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle && entityIn != null && UTEntityRadiusCheck.collisionTargets.contains(entityIn.getClass()))
        {
            // Trying to modify the actual MAX_ENTITY_RADIUS across multiple methods that only originate from this call would be messy, just redirect.
            return UTEntityAABBUtil.getEntitiesInAABBexcluding(instance, entityIn, aabb, predicate, ORIGINAL_MAX_ENTITY_RADIUS);
        }
        return original.call(instance, entityIn, aabb, predicate);
    }
}
