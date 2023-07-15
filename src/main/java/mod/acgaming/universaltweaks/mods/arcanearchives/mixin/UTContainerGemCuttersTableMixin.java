package mod.acgaming.universaltweaks.mods.arcanearchives.mixin;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;

import com.aranaira.arcanearchives.inventory.ContainerGemCuttersTable;
import com.aranaira.arcanearchives.tileentities.GemCuttersTableTileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = ContainerGemCuttersTable.class, remap = false)
public abstract class UTContainerGemCuttersTableMixin extends Container
{
    @Shadow
    @Final
    private GemCuttersTableTileEntity tile;

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer player)
    {
        BlockPos pos = this.tile.getPos();
        if (this.tile.getWorld().getTileEntity(pos) != this.tile) return false;
        else return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
}