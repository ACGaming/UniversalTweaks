package mod.acgaming.universaltweaks.mods.biomesoplenty.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import biomesoplenty.common.util.block.BlockQuery;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import mod.acgaming.universaltweaks.mods.biomesoplenty.UTBoPPlantable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// Falls back to Block#canSustainPlant when BoP's hardcoded soil lists reject a block
@Mixin(value = BlockQuery.BlockPosQuerySustainsPlantType.class, remap = false)
public class UTBOPBlockQuerySustainsPlantTypeMixin
{
    @Shadow
    private EnumPlantType plantType;

    @ModifyReturnValue(method = "matches", at = @At("RETURN"))
    private boolean utFallbackCanSustainPlant(boolean original, World world, BlockPos pos)
    {
        if (original) return true;
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().canSustainPlant(state, world, pos, EnumFacing.UP, UTBoPPlantable.of(this.plantType));
    }
}
