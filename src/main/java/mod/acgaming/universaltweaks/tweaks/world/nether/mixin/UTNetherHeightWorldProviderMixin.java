package mod.acgaming.universaltweaks.tweaks.world.nether.mixin;

import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldProvider.class, remap = false)
public abstract class UTNetherHeightWorldProviderMixin
{
    @Shadow
    public abstract int getActualHeight();

    @Inject(method = "getHeight", at = @At(value = "RETURN"), cancellable = true)
    public void utNetherHeightWorldProvider(CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue(getActualHeight());
    }
}
