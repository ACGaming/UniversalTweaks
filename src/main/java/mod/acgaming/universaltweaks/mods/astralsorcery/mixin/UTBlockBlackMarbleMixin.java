package mod.acgaming.universaltweaks.mods.astralsorcery.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import hellfirepvp.astralsorcery.common.block.BlockBlackMarble;
import hellfirepvp.astralsorcery.common.util.MiscUtils;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = BlockBlackMarble.class, remap = false)
public abstract class UTBlockBlackMarbleMixin
{
    // copies the implementation from BlockMarble because that code is written correctly.
    @Inject(method = "doesSideBlockRendering", at = @At("HEAD"), cancellable = true)
    public void utDoesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.ASTRAL_SORCERY.utSootyMarbleRendering) return;
        BlockBlackMarble.BlackMarbleBlockType marbleType = state.getValue(BlockBlackMarble.BLACK_MARBLE_TYPE);
        IBlockState other = world.getBlockState(pos.offset(face));
        if (MiscUtils.isFluidBlock(other) && (marbleType == BlockBlackMarble.BlackMarbleBlockType.PILLAR || marbleType == BlockBlackMarble.BlackMarbleBlockType.PILLAR_BOTTOM || marbleType == BlockBlackMarble.BlackMarbleBlockType.PILLAR_TOP))
        {
            cir.setReturnValue(false);
            return;
        }
        if (marbleType == BlockBlackMarble.BlackMarbleBlockType.PILLAR_TOP)
        {
            cir.setReturnValue(face == EnumFacing.UP);
            return;
        }
        if (marbleType == BlockBlackMarble.BlackMarbleBlockType.PILLAR_BOTTOM)
        {
            cir.setReturnValue(face == EnumFacing.DOWN);
            return;
        }
        cir.setReturnValue(state.isOpaqueCube());
    }
}
