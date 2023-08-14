package mod.acgaming.universaltweaks.mods.arcanearchives.mixin;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;

import com.aranaira.arcanearchives.inventory.ContainerRadiantCraftingTable;
import com.aranaira.arcanearchives.tileentities.RadiantCraftingTableTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = ContainerRadiantCraftingTable.class, remap = false)
public abstract class UTContainerRadiantCraftingTableMixin extends Container
{
    @Shadow
    public RadiantCraftingTableTileEntity tile;

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer player)
    {
        BlockPos pos = this.tile.getPos();
        if (this.tile.getWorld().getTileEntity(pos) != this.tile) return false;
        return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }

}