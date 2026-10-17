package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import biomesoplenty.common.block.BlockBOPBamboo;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mod.acgaming.universaltweaks.mods.biomesoplenty.UTBoPPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Bamboo uses its own hardcoded soil list, not BlockQueries
@Mixin(value = BlockBOPBamboo.class, remap = false)
public class UTBOPBambooSoilMixin
{
    @ModifyReturnValue(method = "canBlockStay", at = @At("RETURN"))
    private boolean utFallbackCanSustainPlant(boolean original, World world, BlockPos pos, IBlockState state)
    {
        if (original) return true;
        BlockPos soilPos = pos.down();
        IBlockState soil = world.getBlockState(soilPos);
        return soil.getBlock().canSustainPlant(soil, world, soilPos, EnumFacing.UP, UTBoPPlantable.PLAINS);
    }
}
