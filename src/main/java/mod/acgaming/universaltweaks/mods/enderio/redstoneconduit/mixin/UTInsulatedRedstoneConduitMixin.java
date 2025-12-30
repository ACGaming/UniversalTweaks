package mod.acgaming.universaltweaks.mods.enderio.redstoneconduit.mixin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.util.EnumFacing;

import com.enderio.core.common.util.DyeColor;
import crazypants.enderio.base.conduit.redstone.signals.Signal;
import crazypants.enderio.conduits.conduit.redstone.InsulatedRedstoneConduit;
import crazypants.enderio.conduits.conduit.redstone.RedstoneConduitNetwork;
import crazypants.enderio.util.FuncUtil;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = InsulatedRedstoneConduit.class, remap = false)
public abstract class UTInsulatedRedstoneConduitMixin
{
    @Shadow
    @Nullable
    public abstract RedstoneConduitNetwork getNetwork();

    @Shadow
    @Nonnull
    public abstract Signal getNetworkInput(@NotNull EnumFacing side);

    @Shadow
    @Nonnull
    public abstract DyeColor getInputSignalColor(@NotNull EnumFacing dir);

    /**
     * @author WaitingIdly
     * @reason When changing color, first set the current color to
     * have a signal strength of 0. This prevents the signal strength from lingering
     * in the network.
     */
    @Inject(method = "setInputSignalColor", at = @At("HEAD"))
    private void utRemoveOldSignalStrength(EnumFacing dir, DyeColor col, CallbackInfo ci)
    {
        FuncUtil.doIf(getNetwork(), net -> net.getBundledSignal().addSignal(getInputSignalColor(dir), new Signal(0, getNetworkInput(dir).getId())));
    }
}
