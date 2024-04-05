package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.smeltery.client.SmelteryRenderer;
import slimeknights.tconstruct.smeltery.client.SmelteryTankRenderer;
import slimeknights.tconstruct.smeltery.tileentity.TileSmeltery;

import javax.annotation.Nonnull;

// Courtesy of WaitingIdly
@Mixin(value = SmelteryRenderer.class, remap = false)
public abstract class SmelteryRendererMixin extends SmelteryTankRenderer<TileSmeltery>
{
    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lslimeknights/tconstruct/smeltery/client/SmelteryRenderer;renderFluids(Lslimeknights/tconstruct/library/smeltery/SmelteryTank;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;DDD)V", shift = At.Shift.AFTER), cancellable = true)
    public void utRender(@Nonnull TileSmeltery smeltery, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci)
    {
        if (UTConfigMods.TINKERS_CONSTRUCT.utMaximumItemRendersInSmeltery == -1) return;
        if (smeltery.getSizeInventory() > UTConfigMods.TINKERS_CONSTRUCT.utMaximumItemRendersInSmeltery) {
            ci.cancel();
        }
    }
}
