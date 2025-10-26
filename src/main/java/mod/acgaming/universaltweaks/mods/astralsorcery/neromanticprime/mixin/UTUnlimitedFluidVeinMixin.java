package mod.acgaming.universaltweaks.mods.astralsorcery.neromanticprime.mixin;

import hellfirepvp.astralsorcery.common.base.FluidRarityRegistry.ChunkFluidEntry;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ChunkFluidEntry.class, remap = false)
public abstract class UTUnlimitedFluidVeinMixin {
    @Shadow
    private int mbRemaining;

    @Shadow
    private Fluid chunkFluid;

    @Inject(method = "tryDrain", at = @At("HEAD"), cancellable = true, remap = false)
    private void shortcutInfiniteDrain(int mbRequested, boolean consume, CallbackInfoReturnable<FluidStack> cir) {
        // use shadowed fields directly to avoid illegal direct access to the target's private fields
        if (UTConfigMods.ASTRAL_SORCERY.utNeromanticPrimeUnlimitedVeins && this.mbRemaining == Integer.MAX_VALUE && ((ChunkFluidEntry) (Object) this).isValid())
            cir.setReturnValue(new FluidStack(this.chunkFluid, mbRequested));
    }
}