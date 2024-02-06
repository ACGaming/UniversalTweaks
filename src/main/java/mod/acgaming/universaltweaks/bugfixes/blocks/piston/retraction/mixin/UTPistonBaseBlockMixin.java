package mod.acgaming.universaltweaks.bugfixes.blocks.piston.retraction.mixin;

import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.block.BlockPistonBase.EXTENDED;

// MC-88959
// https://bugs.mojang.com/browse/MC-88959
// Courtesy of Nessiesson
@Mixin(BlockPistonBase.class)
public abstract class UTPistonBaseBlockMixin
{
    @Inject(method = "checkForMove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addBlockEvent(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;II)V", ordinal = 1))
    private void utOnPistonDepower(World worldIn, BlockPos pos, IBlockState state, CallbackInfo ci)
    {
        if (UTConfigBugfixes.BLOCKS.utPistonRetractionToggle)
        {
            worldIn.setBlockState(pos, state.withProperty(EXTENDED, false), 2);
        }
    }
}