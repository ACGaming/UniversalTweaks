package mod.acgaming.universaltweaks.tweaks.items.itementities.mixin;

import org.lwjgl.util.vector.Vector3f;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RenderEntityItem.class, priority = 1002)
public abstract class UTRenderEntityItemMixin
{
    @Inject(method = "shouldBob", at = @At("RETURN"), cancellable = true, remap = false)
    public void utIEBobbing(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEBobbingToggle) cir.setReturnValue(false);
    }

    @Redirect(method = "transformModelCount", at = @At(value = "FIELD", target = "Lorg/lwjgl/util/vector/Vector3f;y:F"), remap = false)
    public float utIEBobbing2(Vector3f scale)
    {
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEBobbingToggle) return -0.25F;
        return scale.y;
    }

    @WrapWithCondition(method = "transformModelCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    public boolean utIERotation(float angle, float x, float y, float z)
    {
        return UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIERotationToggle;
    }

    // Courtesy of TheIllusiveC4
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void utIERenderFlash(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci)
    {
        if (!UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEClearDespawnToggle) return;
        int remainingTime = entity.getItem().getItem().getEntityLifespan(entity.getItem(), entity.getEntityWorld()) - entity.getAge();
        if (remainingTime <= UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEClearDespawnTime * 20)
        {
            if (UTConfigTweaks.ITEMS.ITEM_ENTITIES.utIEClearDespawnUrgentToggle)
            {
                int flashFactor = Math.max(2, remainingTime / 20);
                if (remainingTime % (flashFactor) < 0.5F * flashFactor) ci.cancel();
            }
            else if (remainingTime % 20 < 10) ci.cancel();
        }
    }
}