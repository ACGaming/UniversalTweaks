package mod.acgaming.universaltweaks.mods.actuallyadditions.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityInventoryBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPhantomPlacer;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityPhantomface;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import de.ellpeck.actuallyadditions.mod.util.WorldUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = TileEntityPhantomPlacer.class, remap = false)
public abstract class UTTileEntityPhantomPlacerMixin extends TileEntityInventoryBase
{
    @Shadow
    @Final
    public static int RANGE;
    @Shadow
    public boolean isBreaker;

    @Shadow
    public BlockPos boundPosition;

    @Shadow
    public int side;
    @Shadow
    public int range;
    @Shadow
    public int currentTime;
    @Shadow
    private int oldRange;

    public UTTileEntityPhantomPlacerMixin(int slots, String name)
    {
        super(slots, name);
    }

    @Shadow
    public abstract boolean isBoundThingInRange();

    @Shadow
    public abstract void renderParticles();

    @Shadow
    public abstract boolean hasBoundPosition();

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        if (!this.world.isRemote)
        {
            this.range = TileEntityPhantomface.upgradeRange(RANGE, this.world, this.pos);

            if (!this.hasBoundPosition())
            {
                this.boundPosition = null;
            }

            if (this.isBoundThingInRange())
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
                        this.currentTime = 30;
                    }
                }
            }

            if (this.oldRange != this.range)
            {
                this.oldRange = this.range;

                this.sendUpdate();
            }
        }
        else
        {
            if (this.boundPosition != null)
            {
                this.renderParticles();
            }
        }
    }

    private void doWork()
    {
        if (this.isBoundThingInRange())
        {
            if (this.isBreaker)
            {
                if (this.world.getTileEntity(this.boundPosition) != null) return;
                Block blockToBreak = this.world.getBlockState(this.boundPosition).getBlock();
                if (this.world.getBlockState(this.boundPosition).getBlockHardness(this.world, this.boundPosition) > -1.0F)
                {
                    NonNullList<ItemStack> drops = NonNullList.create();
                    blockToBreak.getDrops(drops, this.world, this.pos, this.world.getBlockState(this.boundPosition), 0);

                    if (StackUtil.canAddAll(this.inv, drops, false))
                    {
                        this.world.playEvent(2001, this.boundPosition, Block.getStateId(this.world.getBlockState(this.boundPosition)));
                        this.world.setBlockToAir(this.boundPosition);
                        StackUtil.addAll(this.inv, drops, false);
                        this.markDirty();
                    }
                }
            }
            else
            {
                int theSlot = StackUtil.findFirstFilled(this.inv);
                if (theSlot == -1) return;
                this.inv.setStackInSlot(theSlot, WorldUtil.useItemAtSide(WorldUtil.getDirectionBySidesInOrder(this.side), this.world, this.boundPosition, this.inv.getStackInSlot(theSlot)));
            }
        }
    }
}