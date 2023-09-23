package mod.acgaming.universaltweaks.tweaks.blocks.leafdecay.mixin;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockLeaves.class, remap = false)
public class UTLeafDecayMixin
{
    @Inject(method = "beginLeavesDecay", at = @At("TAIL"))
    public void utLeafDecay(IBlockState state, World world, BlockPos pos, CallbackInfo ci)
    {
        if (!UTConfigTweaks.BLOCKS.utLeafDecayToggle || !world.getChunk(pos).isPopulated()) return;
        world.scheduleUpdate(pos, state.getBlock(), MathHelper.getInt(world.rand, 10, 20));
    }
}