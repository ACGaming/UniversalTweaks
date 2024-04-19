package mod.acgaming.universaltweaks.tweaks.blocks.endcrystal.mixin;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEndCrystal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ItemEndCrystal.class)
public abstract class UTEndCrystalPlacing
{
    // Pretend every block is Obsidian when trying to place
    @ModifyExpressionValue(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;", ordinal = 0))
    public Block utEndCrystalPlacingOverride(Block original)
    {
        if (!UTConfigTweaks.BLOCKS.utEndCrystalAnywherePlacing) return original;
        return Blocks.OBSIDIAN;
    }
}
