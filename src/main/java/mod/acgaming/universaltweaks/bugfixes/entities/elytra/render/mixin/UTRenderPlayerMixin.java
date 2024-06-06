package mod.acgaming.universaltweaks.bugfixes.entities.elytra.render.mixin;

import net.minecraft.client.renderer.entity.RenderPlayer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

// Courtesy of WaitingIdly, TheRandomLabs (RandomPatches)
@Mixin(value = RenderPlayer.class)
public abstract class UTRenderPlayerMixin
{
    @WrapOperation(method = "applyRotations", at = @At(value = "INVOKE", target = "Ljava/lang/Math;acos(D)D"))
    private double utClampToPreventInvisiblePlayerOnElytra(double instance, Operation<Double> original)
    {
        if (!UTConfigBugfixes.ENTITIES.utFixInvisiblePlayerModelWithElytra) return original.call(instance);
        return original.call(Math.min(1, instance));
    }
}