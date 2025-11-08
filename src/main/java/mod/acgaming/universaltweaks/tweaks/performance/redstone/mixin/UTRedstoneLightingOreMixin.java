package mod.acgaming.universaltweaks.tweaks.performance.redstone.mixin;

import net.minecraft.block.BlockRedstoneOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockRedstoneOre.class)
public abstract class UTRedstoneLightingOreMixin
{
    @WrapWithCondition(method = "activate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/state/IBlockState;)Z"))
    public boolean utRedstoneOreActivate(World world, BlockPos pos, IBlockState state)
    {
        return !UTConfigTweaks.PERFORMANCE.utRedstoneLightingToggle;
    }
}
