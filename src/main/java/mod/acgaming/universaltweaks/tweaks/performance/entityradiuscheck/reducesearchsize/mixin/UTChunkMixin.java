package mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.reducesearchsize.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.world.chunk.Chunk;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.UTEntityRadiusCheck;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(value = Chunk.class)
public class UTChunkMixin
{
    @ModifyExpressionValue(method = "getEntitiesOfTypeWithinAABB", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/World;MAX_ENTITY_RADIUS:D", remap = false))
    private <T extends Entity> double utReduceMaxEntityRadius(double original, Class<? extends T> clazz)
    {
        final double ORIGINAL_MAX_ENTITY_RADIUS = 2.0D;
        if (original == ORIGINAL_MAX_ENTITY_RADIUS || !UTConfigTweaks.PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle) return original;
        return UTEntityRadiusCheck.searchTargets.contains(clazz) ? ORIGINAL_MAX_ENTITY_RADIUS : original;
    }
}
