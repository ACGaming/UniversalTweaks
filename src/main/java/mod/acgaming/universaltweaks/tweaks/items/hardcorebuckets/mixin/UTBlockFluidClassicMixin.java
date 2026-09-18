package mod.acgaming.universaltweaks.tweaks.items.hardcorebuckets.mixin;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fluids.BlockFluidClassic;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockFluidClassic.class)
public abstract class UTBlockFluidClassicMixin
{
    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/BlockFluidClassic;getDefaultState()Lnet/minecraft/block/state/IBlockState;"))
    private static IBlockState utPlace(BlockFluidClassic block)
    {
        return block.getDefaultState().withProperty(BlockLiquid.LEVEL, 1);
    }
}
