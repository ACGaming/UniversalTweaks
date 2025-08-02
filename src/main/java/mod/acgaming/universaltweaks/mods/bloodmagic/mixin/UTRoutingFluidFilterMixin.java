package mod.acgaming.universaltweaks.mods.bloodmagic.mixin;

import WayofTime.bloodmagic.routing.IFluidFilter;
import WayofTime.bloodmagic.routing.RoutingFluidFilter;
import WayofTime.bloodmagic.tile.routing.TileMasterRoutingNode;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RoutingFluidFilter.class, remap = false)
public class UTRoutingFluidFilterMixin
{
    /**
     * @author Invadermonky
     * @reason Fixes Blood Magic's routing nodes soft-locking when routing fluids.
     *
     * <p>
     * Blood Magic's routing nodes soft-locked when pushing liquids into tanks. If a fluid was found in an output
     * tank, any fluid that did not match would not be routed. This also caused an issue where if the first tank
     * in the routing network was completely filled, the routing would halt and no additional liquid could be routed.
     * </p>
     *
     * <p>
     * This bug is caused by an incorrect return variable. The method calculated the drain amount by pulling the
     * maxTransfer from the input and simulating an insert into the output. If it succeeded, it would perform a
     * non-simulated drain for the output amount. For some reason, instead of returning the drained amount, the
     * method returned the maxTransfer.
     * </p>
     *
     * <p>
     * The actual logic for continuing the routing logic was controlled by a loop within {@link TileMasterRoutingNode#update()}.
     * When this value was returned, it was subtracted from the maxTransfer amount and the loop would be cancelled if
     * this value was less than or equal to 0. With the default logic, if it could not insert fluid, it would return
     * the maxInsert, which was then subtracted from the loop maxInsert, which, if it reached 0, would cancel further
     * routing logic.
     * </p>
     */
    @Inject(method = "transferThroughInputFilter", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private void transferThroughInputFilterMixin(IFluidFilter outputFilter, int maxTransfer, CallbackInfoReturnable<Integer> cir, @Local(ordinal = 2) int drained)
    {
        cir.setReturnValue(drained);
        cir.cancel();
    }
}
