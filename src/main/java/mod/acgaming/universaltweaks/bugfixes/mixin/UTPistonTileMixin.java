package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.EnumFacing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// MC-89146
// https://bugs.mojang.com/browse/MC-89146
// Courtesy of Xcom6000
@Mixin(TileEntityPiston.class)
public class UTPistonTileMixin extends TileEntity
{
    @Shadow
    private IBlockState pistonState;

    @Shadow
    private EnumFacing pistonFacing;

    @Shadow
    private float progress;

    @Shadow
    private float lastProgress;

    @Shadow
    private boolean extending;

    @Shadow
    private boolean shouldHeadBeRendered;

    /**
     * @author ACGaming
     * @reason Fix piston progress
     */
    @Overwrite
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
        this.pistonFacing = EnumFacing.byIndex(compound.getInteger("facing"));
        this.progress = compound.getFloat("progress");
        this.lastProgress = compound.getFloat("lastProgress");
        this.extending = compound.getBoolean("extending");
        this.shouldHeadBeRendered = compound.getBoolean("source");
    }

    /**
     * @author ACGaming
     * @reason Fix piston progress
     */
    @Overwrite
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
        compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
        compound.setInteger("facing", this.pistonFacing.getIndex());
        compound.setFloat("progress", this.progress);
        compound.setFloat("lastProgress", this.lastProgress);
        compound.setBoolean("extending", this.extending);
        compound.setBoolean("source", this.shouldHeadBeRendered);
        return compound;
    }
}