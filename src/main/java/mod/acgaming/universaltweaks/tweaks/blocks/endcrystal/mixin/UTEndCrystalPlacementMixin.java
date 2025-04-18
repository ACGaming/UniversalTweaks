package mod.acgaming.universaltweaks.tweaks.blocks.endcrystal.mixin;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemEndCrystal;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.blocks.endcrystal.UTEndCrystalPlacement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ItemEndCrystal.class)
public abstract class UTEndCrystalPlacementMixin
{
    // Pretend every block is Obsidian when trying to place allowed blocks
    @ModifyExpressionValue(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getBlock()Lnet/minecraft/block/Block;", ordinal = 0))
    public Block utEndCrystalPlacement(Block original)
    {
        if (!UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementToggle) return original;
        boolean isBlacklist = UTConfigTweaks.BLOCKS.END_CRYSTAL_PLACEMENT.utEndCrystalPlacementListMode == UTConfigTweaks.EnumLists.BLACKLIST;
        if (UTEndCrystalPlacement.BLOCK_LIST.contains(original) == isBlacklist) return original;
        return Blocks.OBSIDIAN;
    }
}
