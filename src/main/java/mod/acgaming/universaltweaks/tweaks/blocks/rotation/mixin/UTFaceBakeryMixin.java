package mod.acgaming.universaltweaks.tweaks.blocks.rotation.mixin;

import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FaceBakery.class)
public abstract class UTFaceBakeryMixin
{
    @ModifyArg(method = "rotatePart", at = @At(value = "INVOKE", target = "Lorg/lwjgl/util/vector/Vector3f;scale(F)Lorg/lwjgl/util/vector/Vector;", ordinal = 1, remap = false))
    private float utRotatePartScale(float original, @Local(argsOnly = true) BlockPartRotation partRotation)
    {
        float abs = Math.abs(partRotation.angle);
        if (abs == 0.0F || abs == 45.0F) return original;
        float angleRad = partRotation.angle * (float) Math.PI / 180.0F;
        return (float) (1.0F / Math.cos(angleRad) - 1.0F);
    }
}
