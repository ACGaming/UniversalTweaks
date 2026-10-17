package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import biomesoplenty.api.enums.BOPFlowers;
import biomesoplenty.common.block.BlockBOPFlower;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mod.acgaming.universaltweaks.mods.biomesoplenty.UTBoPPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * For default (Plains) BOP flowers, also accept soils that report support via {@code Block#canSustainPlant}.
 * Special soil flowers keep their existing hardcoded rules.
 */
@Mixin(value = BlockBOPFlower.class, remap = false)
public class UTBOPFlowerSoilMixin
{
    @ModifyReturnValue(method = "canBlockStay", at = @At("RETURN"))
    private boolean utFallbackCanSustainPlant(boolean original, World world, BlockPos pos, IBlockState state)
    {
        if (original) return true;

        BlockBOPFlower flowerBlock = (BlockBOPFlower) (Object) this;
        BOPFlowers flower = (BOPFlowers) state.getValue(flowerBlock.variantProperty);
        switch (flower)
        {
            case ENDERLOTUS:
            case BROMELIAD:
            case WILTED_LILY:
            case BURNING_BLOSSOM:
            case MINERS_DELIGHT:
                return false;
            default:
                break;
        }

        BlockPos soilPos = pos.down();
        IBlockState soil = world.getBlockState(soilPos);
        return soil.getBlock().canSustainPlant(soil, world, soilPos, EnumFacing.UP, UTBoPPlantable.PLAINS);
    }
}
