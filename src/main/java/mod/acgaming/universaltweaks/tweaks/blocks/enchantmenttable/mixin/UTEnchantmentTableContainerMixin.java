package mod.acgaming.universaltweaks.tweaks.blocks.enchantmenttable.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ContainerEnchantment.class)
public abstract class UTEnchantmentTableContainerMixin
{
    @WrapOperation(method = "onCraftMatrixChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isAirBlock(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean utEnchantmentTableAirBlockCheck(World world, BlockPos pos, Operation<Boolean> original)
    {
        return UTConfigTweaks.BLOCKS.utEnchantmentTableObstructionToggle || original.call(world, pos);
    }
}
