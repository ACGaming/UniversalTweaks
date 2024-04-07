package mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.reducesearchsize.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.UTEntityRadiusCheck;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = World.class)
public class UTWorldMixin
{
    @ModifyExpressionValue(method = "getEntitiesWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/World;MAX_ENTITY_RADIUS:D", remap = false))
    private <T extends Entity> double utReduceMaxEntityRadius(double original, Class<? extends T> clazz)
    {
        final double ORIGINAL_MAX_ENTITY_RADIUS = 2.0D;
        if (original == ORIGINAL_MAX_ENTITY_RADIUS || !UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle) return original;

        for (Class<? extends Entity> target : UTEntityRadiusCheck.searchTargets)
        {
            if (clazz.equals(target)) return ORIGINAL_MAX_ENTITY_RADIUS;
        }
        return original;
    }
}
