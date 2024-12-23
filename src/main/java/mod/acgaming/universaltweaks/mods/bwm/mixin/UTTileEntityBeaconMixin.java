package mod.acgaming.universaltweaks.mods.bwm.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import betterwithmods.common.blocks.tile.TileEntityBeacon;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of Rongmario
@Mixin(TileEntityBeacon.class)
public class UTTileEntityBeaconMixin extends TileEntity
{
    @WrapOperation(method = "readFromNBT", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTUtil;readBlockState(Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/block/state/IBlockState;"))
    private IBlockState readBlockState(NBTTagCompound tag, Operation<IBlockState> original)
    {
        if (tag == null)
        {
            return Blocks.AIR.getDefaultState(); // Conforms to normal behavior (BWM doesn't even do anything with the type field!)
        }
        return original.call(tag);
    }
}
