package mod.acgaming.universaltweaks.tweaks.itementities.mixin;

import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zone.rong.mixinextras.injector.WrapWithCondition;

@Mixin(value = RenderEntityItem.class, priority = 1002)
public abstract class UTRenderEntityItemMixin
{
    @Inject(method = "shouldBob", at = @At("RETURN"), cancellable = true, remap = false)
    public void utIEBobbing(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEBobbing) cir.setReturnValue(false);
    }

    @WrapWithCondition(method = "transformModelCount", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    public boolean utIERotation(float angle, float x, float y, float z)
    {
        return UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIERotation;
    }

    // Courtesy of TheIllusiveC4
    @Inject(method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void utIERenderFlash(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEClearDespawn) return;
        int remainingTime = entity.getItem().getItem().getEntityLifespan(entity.getItem(), entity.getEntityWorld()) - entity.getAge();
        if (remainingTime <= UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEClearDespawnTime * 20)
        {
            if (UTConfig.TWEAKS_ITEMS.ITEM_ENTITIES.utIEClearDespawnUrgent)
            {
                int flashFactor = Math.max(2, remainingTime / 20);
                if (remainingTime % (flashFactor) < 0.5F * flashFactor) ci.cancel();
            }
            else if (remainingTime % 20 < 10) ci.cancel();
        }
    }
}