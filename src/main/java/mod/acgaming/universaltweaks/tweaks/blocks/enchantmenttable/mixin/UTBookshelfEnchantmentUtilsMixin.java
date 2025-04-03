package mod.acgaming.universaltweaks.tweaks.blocks.enchantmenttable.mixin;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.darkhax.bookshelf.util.EnchantmentUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EnchantmentUtils.class)
public abstract class UTBookshelfEnchantmentUtilsMixin
{
    @Redirect(method = "getEnchantingPower", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"))
    private static boolean utEnchantingPowerAirBlockCheck(World world, BlockPos pos)
    {
        return UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle || world.isAirBlock(pos);
    }
}
