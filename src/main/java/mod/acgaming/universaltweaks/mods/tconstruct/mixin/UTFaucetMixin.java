package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slimeknights.tconstruct.smeltery.tileentity.TileFaucet;

@Mixin(value = TileFaucet.class, remap = false)
public abstract class UTFaucetMixin extends TileEntity
{
    @Shadow
    @Final
    public static int TRANSACTION_AMOUNT;
    @Shadow
    public EnumFacing direction;

    @Inject(method = "doTransfer", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/capability/IFluidHandler;fill(Lnet/minecraftforge/fluids/FluidStack;Z)I", shift = At.Shift.BEFORE), cancellable = true)
    public void utFaucetGaseousFluids(CallbackInfo ci)
    {
        if (!UTConfigMods.TINKERS_CONSTRUCT.utTConGaseousFluidsToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFaucet ::: Check fluid type");
        FluidStack fluidStack = getFluidHandler(pos.offset(direction), direction.getOpposite()).drain(TRANSACTION_AMOUNT, false);
        if (fluidStack != null && fluidStack.getFluid().isGaseous())
        {
            reset();
            ci.cancel();
        }
    }

    @Shadow
    protected abstract IFluidHandler getFluidHandler(BlockPos pos, EnumFacing direction);

    @Shadow
    protected abstract void reset();
}