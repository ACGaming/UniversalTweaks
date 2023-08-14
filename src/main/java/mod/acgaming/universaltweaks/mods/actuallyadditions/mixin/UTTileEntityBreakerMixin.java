package mod.acgaming.universaltweaks.mods.actuallyadditions.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.IFluidBlock;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBreaker;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = TileEntityBreaker.class, remap = false)
public abstract class UTTileEntityBreakerMixin extends TileEntityInventoryBase
{
    @Shadow
    public boolean isPlacer;

    @Shadow
    private int currentTime;

    public UTTileEntityBreakerMixin(int slots, String name)
    {
        super(slots, name);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!this.world.isRemote)
        {
            if (!this.isRedstonePowered && !this.isPulseMode)
            {
                if (this.currentTime > 0)
                {
                    this.currentTime--;
                    if (this.currentTime <= 0)
                    {
                        this.doWork();
                    }
                }
                else
                {
                    this.currentTime = 15;
                }
            }
        }
    }

    private void doWork()
    {
        EnumFacing side = WorldUtil.getDirectionByPistonRotation(this.world.getBlockState(this.pos));
        BlockPos breakCoords = this.pos.offset(side);
        if (this.world.getTileEntity(breakCoords) != null) return;
        IBlockState stateToBreak = this.world.getBlockState(breakCoords);
        Block blockToBreak = stateToBreak.getBlock();

        if (!this.isPlacer && blockToBreak != Blocks.AIR && !(blockToBreak instanceof BlockLiquid) && !(blockToBreak instanceof IFluidBlock) && stateToBreak.getBlockHardness(this.world, breakCoords) >= 0.0F)
        {
            NonNullList<ItemStack> drops = NonNullList.create();
            blockToBreak.getDrops(drops, this.world, breakCoords, stateToBreak, 0);
            float chance = WorldUtil.fireFakeHarvestEventsForDropChance(this, drops, this.world, breakCoords);

            if (chance > 0 && this.world.rand.nextFloat() <= chance)
            {
                if (StackUtil.canAddAll(this.inv, drops, false))
                {
                    this.world.destroyBlock(breakCoords, false);
                    StackUtil.addAll(this.inv, drops, false);
                    this.markDirty();
                }
            }
        }
        else if (this.isPlacer)
        {
            int slot = StackUtil.findFirstFilled(this.inv);
            if (slot == -1) return;
            this.inv.setStackInSlot(slot, WorldUtil.useItemAtSide(side, this.world, this.pos, this.inv.getStackInSlot(slot)));
        }
    }
}