package mod.acgaming.universaltweaks.tweaks.blocks.betterrailplacement.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import mod.acgaming.universaltweaks.tweaks.blocks.betterrailplacement.UTRailExtension;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBase.Rail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(Rail.class)
public abstract class UTRailMixin implements UTRailExtension {

	@Shadow
	@Final
	private World world;
	@Shadow
	@Final
	private BlockPos pos;
	@Shadow
	@Final
	private BlockRailBase block;
	@Shadow
	private IBlockState state;
	@Shadow
	private boolean isPowered;
	@Shadow
	@Final
	private List<BlockPos> connectedRails;
	
	@Shadow
	abstract boolean hasNeighborRail(BlockPos posIn);

	@Shadow
	abstract void updateConnectedRails(BlockRailBase.EnumRailDirection railDirection);
	
	@Shadow
    abstract Rail findRailAt(BlockPos pos);

	@Shadow
	abstract void removeSoftConnections();
	
	@Shadow
	abstract boolean canConnectTo(Rail rail);
	
	@Shadow
	abstract void connectTo(BlockRailBase.Rail rail);
	
	@Unique
	@Override
	public void ut$updateConnections(Rail other) {
		this.removeSoftConnections();

		if (this.canConnectTo(other)) {
			this.connectTo(other);
		}
	}
	
	@Overwrite
	public BlockRailBase.Rail place(boolean powered, boolean initialPlacement) {
		BlockPos blockpos = this.pos.north();
		BlockPos blockpos1 = this.pos.south();
		BlockPos blockpos2 = this.pos.west();
		BlockPos blockpos3 = this.pos.east();
		boolean flag = this.hasNeighborRail(blockpos);
		boolean flag1 = this.hasNeighborRail(blockpos1);
		boolean flag2 = this.hasNeighborRail(blockpos2);
		boolean flag3 = this.hasNeighborRail(blockpos3);
		BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;

		if ((flag || flag1) && !flag2 && !flag3) {
			blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
		}

		if ((flag2 || flag3) && !flag && !flag1) {
			blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
		}

		if (!this.isPowered) {
			if (flag1 && flag3 && !flag && !flag2) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
			}

			if (flag1 && flag2 && !flag && !flag3) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
			}

			if (flag && flag2 && !flag1 && !flag3) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
			}

			if (flag && flag3 && !flag1 && !flag2) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
			}
		}

		if (blockrailbase$enumraildirection == null) {
			if (flag || flag1) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			}

			if (flag2 || flag3) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
			}

			if (!this.isPowered) {
				if (powered) {
					if (flag1 && flag3) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
					}

					if (flag2 && flag1) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
					}

					if (flag3 && flag) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
					}

					if (flag && flag2) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
					}
				} else {
					if (flag && flag2) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
					}

					if (flag3 && flag) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
					}

					if (flag2 && flag1) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
					}

					if (flag1 && flag3) {
						blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
					}
				}
			}
		}

		if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH && this.block.canMakeSlopes(this.world, this.pos)) {
			if (BlockRailBase.isRailBlock(this.world, blockpos.up())) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
			}

			if (BlockRailBase.isRailBlock(this.world, blockpos1.up())) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
			}
		}

		if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST && this.block.canMakeSlopes(this.world, this.pos)) {
			if (BlockRailBase.isRailBlock(this.world, blockpos3.up())) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
			}

			if (BlockRailBase.isRailBlock(this.world, blockpos2.up())) {
				blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
			}
		}

		if (blockrailbase$enumraildirection == null) {
			// blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
			blockrailbase$enumraildirection = this.state.getValue(this.block.getShapeProperty());
		}

		this.updateConnectedRails(blockrailbase$enumraildirection);
		this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);

		if (initialPlacement || this.world.getBlockState(this.pos) != this.state) {
			this.world.setBlockState(this.pos, this.state, 3);

			for (int i = 0; i < this.connectedRails.size(); ++i) {
				BlockRailBase.Rail blockrailbase$rail = this.findRailAt(this.connectedRails.get(i));

				if (blockrailbase$rail != null) {
					((UTRailExtension) blockrailbase$rail).ut$updateConnections((Rail) (Object) this);
				}
			}
		}

		return (Rail) (Object) this;
	}

}
