package mod.acgaming.universaltweaks.tweaks.items.hardcorebuckets.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Liquid!!
// Snake, did you like my sunglasses?
@Mixin(BlockLiquid.class)
public abstract class UTBlockLiquidMixin
{
    @Dynamic("Method added by Fluidlogged API via ASM")
    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/state/IBlockState;"))
    private static IBlockState utPlace(Block block)
    {
        return block.getDefaultState().withProperty(BlockLiquid.LEVEL, 1);
    }
}
