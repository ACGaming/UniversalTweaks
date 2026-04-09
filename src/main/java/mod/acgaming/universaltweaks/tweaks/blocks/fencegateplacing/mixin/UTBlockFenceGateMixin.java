package mod.acgaming.universaltweaks.tweaks.blocks.fencegateplacing.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlockFenceGate.class)
public abstract class UTBlockFenceGateMixin extends Block
{
    public UTBlockFenceGateMixin(Material blockMaterialIn, MapColor blockMapColorIn)
    {
        super(blockMaterialIn, blockMapColorIn);
    }

    @Inject(method = "canPlaceBlockAt", at = @At(value = "HEAD"), cancellable = true)
    public void utUnsupportedPlacingOverride(World worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.BLOCKS.utUnsupportedFenceGatePlacing) return;
        cir.setReturnValue(super.canPlaceBlockAt(worldIn, pos));
    }
}
