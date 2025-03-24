package mod.acgaming.universaltweaks.tweaks.blocks.enchantmenttable.mixin;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ContainerEnchantment.class)
public abstract class UTEnchantmentTableContainerMixin
{
    @Redirect(method = "onCraftMatrixChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean utEnchantmentTableAirBlockCheck(World world, BlockPos pos)
    {
        return UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle || world.isAirBlock(pos);
    }
}
