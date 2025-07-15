package mod.acgaming.universaltweaks.bugfixes.misc.itempickup.mixin;

import net.minecraft.client.particle.ParticleItemPickup;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleItemPickup.class)
public class UTParticleItemPickupMixin
{

    @WrapOperation(method = "renderParticle",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderManager;renderEntity(Lnet/minecraft/entity/Entity;DDDFFZ)V"))
    private void enableCull(RenderManager renderManager, Entity entity,
                            double x, double y, double z, float yaw,
                            float partialTicks, boolean idk, Operation<Void> method)
    {
        GlStateManager.enableCull();
        method.call(renderManager, entity, x, y, z, yaw, partialTicks, idk);
        GlStateManager.disableCull();
    }
}
