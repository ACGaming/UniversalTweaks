package mod.acgaming.universaltweaks.tweaks.misc.fluidstackconstructor.mixin;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FluidStack.class, remap = false)
public class UTUnsafeFluidStackMixin
{
    @Redirect(method = "<init>(Lnet/minecraftforge/fluids/Fluid;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidRegistry;isFluidRegistered(Lnet/minecraftforge/fluids/Fluid;)Z"))
    public boolean utUnsafeFluidStackConstructor(Fluid fluid)
    {
        return UTConfigTweaks.MISC.utUnsafeFluidStackConstructorToggle;
    }
}
