package mod.acgaming.universaltweaks.tweaks.blocks.lenientpaths.mixin;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemSpade;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemSpade.class)
public class UTLenientPathsShovelMixin
{
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getMaterial()Lnet/minecraft/block/material/Material;"))
    public Material utLenientPathsShovel(IBlockState blockState)
    {
        if (!UTConfig.TWEAKS_BLOCKS.utLenientPathsToggle) return blockState.getMaterial();
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLenientPathsShovel ::: On item use");
        return Material.AIR;
    }
}