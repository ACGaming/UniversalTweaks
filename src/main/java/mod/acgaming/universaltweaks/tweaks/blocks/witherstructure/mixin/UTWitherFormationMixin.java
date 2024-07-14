package mod.acgaming.universaltweaks.tweaks.blocks.witherstructure.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockSkull.class)
public abstract class UTWitherFormationMixin
{
    @WrapWithCondition(method = "checkWitherSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z", ordinal = 1))
    private boolean utOverrideRemovalLogic(World world, BlockPos pos, IBlockState newState, int flags, @Local(ordinal = 0) int j, @Local(ordinal = 1) int k)
    {
        if (!UTConfigTweaks.ENTITIES.utWitherPlacement) return true;
        return k != 2 || (j != 0 && j != 2);
    }

    @ModifyReceiver(method = {"getWitherPattern", "getWitherBasePattern"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/pattern/FactoryBlockPattern;build()Lnet/minecraft/block/state/pattern/BlockPattern;"))
    private FactoryBlockPattern utReplaceAirRequirementWithAny(FactoryBlockPattern receiver)
    {
        if (!UTConfigTweaks.ENTITIES.utWitherPlacement) return receiver;
        return receiver.where('~', unused -> true);
    }
}
