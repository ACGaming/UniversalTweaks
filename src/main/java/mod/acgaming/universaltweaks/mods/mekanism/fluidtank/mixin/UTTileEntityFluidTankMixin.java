package mod.acgaming.universaltweaks.mods.mekanism.fluidtank.mixin;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import mekanism.common.tile.TileEntityFluidTank;
import mekanism.common.util.FluidContainerUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityFluidTank.class, remap = false)
public abstract class UTTileEntityFluidTankMixin
{
    @Shadow
    public boolean isActive;

    @Shadow
    public FluidTank fluidTank;

    /**
     * Converts the code as such:
     * <br><code>- fluidTank != null && FluidContainerUtils.canDrain(fluidTank.getFluid(), fluid) && !isActive || from != EnumFacing.DOWN</code>
     * <br><code>+ fluidTank != null && FluidContainerUtils.canDrain(fluidTank.getFluid(), fluid) && (!isActive || from != EnumFacing.DOWN)</code>
     *
     * @author WaitingIdly
     * @reason Mekanism's default logic always returns true if the direction is anywhere except down,
     * instead of the direction being down only being relevant in relation to isActive.
     * This issue was introduced in <a href="https://github.com/mekanism/Mekanism/commit/7a726f3695cf70d11f6fe6eb4d0f3457a21eb5c6">this commit</a>,
     * while simplifying code.
     */
    @Inject(method = "canDrain", at = @At("HEAD"), cancellable = true)
    private void fixFluidTank(EnumFacing from, FluidStack fluid, CallbackInfoReturnable<Boolean> cir)
    {
        cir.setReturnValue(fluidTank != null && FluidContainerUtils.canDrain(fluidTank.getFluid(), fluid) && (!isActive || from != EnumFacing.DOWN));
    }
}
