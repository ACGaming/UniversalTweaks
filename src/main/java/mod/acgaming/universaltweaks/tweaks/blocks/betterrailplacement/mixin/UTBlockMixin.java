package mod.acgaming.universaltweaks.tweaks.blocks.betterrailplacement.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.EnumRailDirection;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Block.class)
public class UTBlockMixin { 
	
	@Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
	void better_rail_placement$getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, CallbackInfoReturnable<IBlockState> ci) {
		if ((Object) this instanceof BlockRailBase) {
			BlockRailBase $this = (BlockRailBase) (Object) this;
			IBlockState iblockstate = ci.getReturnValue();
			
			IProperty<EnumRailDirection> prop = $this.getShapeProperty();
			
			if (placer.getHorizontalFacing().getAxis() == Axis.Z) {
				iblockstate = iblockstate.withProperty(prop, EnumRailDirection.NORTH_SOUTH);
			} else {
				iblockstate = iblockstate.withProperty(prop, EnumRailDirection.EAST_WEST);
			}
			ci.setReturnValue(iblockstate);
		}
	}
}
