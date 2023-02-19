package mod.acgaming.universaltweaks.tweaks.itementities.mixin;

import net.minecraft.client.renderer.entity.RenderEntityItem;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}