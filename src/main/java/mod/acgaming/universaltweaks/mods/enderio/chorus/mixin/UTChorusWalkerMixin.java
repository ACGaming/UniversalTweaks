package mod.acgaming.universaltweaks.mods.enderio.chorus.mixin;

import java.util.Set;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(targets = "crazypants.enderio.base.farming.farmers.ChorusFarmer$ChorusWalker", remap = false)
public abstract class UTChorusWalkerMixin
{
    @Unique
    private final Set<BlockPos> universalTweaks$checkedPositions = new ObjectOpenHashSet<>();

    /**
     * @author WaitingIdly
     * @reason Prevent StackOverflows by skipping previously already checked positions.
     */
    @Inject(method = "collect", at = @At("HEAD"), cancellable = true)
    protected void utIgnoreDuplicatePositions(BlockPos pos, EnumFacing from, CallbackInfoReturnable<Boolean> cir)
    {
        if (!universalTweaks$checkedPositions.add(pos)) cir.setReturnValue(false);
    }
}
