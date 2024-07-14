package mod.acgaming.universaltweaks.tweaks.blocks.golemstructure.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockPumpkin.class)
public abstract class UTGolemFormationMixin
{
    @WrapWithCondition(method = "trySpawnGolem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;I)Z", ordinal = 1))
    private boolean utOverrideRemovalLogic(World world, BlockPos pos, IBlockState newState, int flags, @Local(ordinal = 0) int j, @Local(ordinal = 1) int k)
    {
        if (!UTConfigTweaks.ENTITIES.utGolemPlacement) return true;
        return (k != 0 && k != 2) || (j != 0 && j != 2);
    }

    @ModifyReceiver(method = {"getGolemPattern", "getGolemBasePattern"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/pattern/FactoryBlockPattern;build()Lnet/minecraft/block/state/pattern/BlockPattern;"))
    private FactoryBlockPattern utReplaceAirRequirementWithAny(FactoryBlockPattern receiver)
    {
        if (!UTConfigTweaks.ENTITIES.utGolemPlacement) return receiver;
        return receiver.where('~', unused -> true);
    }
}
