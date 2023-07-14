package mod.acgaming.universaltweaks.mods.erebus.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import erebus.blocks.BlockPreservedBlock;
import erebus.tileentity.TileEntityPreservedBlock;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Courtesy of noobanidus
@Mixin(BlockPreservedBlock.class)
public class UTBlockPreservedBlockMixin
{
    @Inject(method = "getPickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/nbt/NBTTagCompound;setTag(Ljava/lang/String;Lnet/minecraft/nbt/NBTBase;)V"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void utGetPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player, CallbackInfoReturnable<ItemStack> cir, TileEntityPreservedBlock tile, BlockPreservedBlock.EnumAmberType type, ItemStack stack, NBTTagCompound tag)
    {
        if (!UTConfig.MOD_INTEGRATION.EREBUS.utEBPreservedBlocksToggle) return;
        NBTTagCompound entityNBT = tile.getEntityNBT();
        if (entityNBT != null)
        {
            tag.setTag("EntityNBT", entityNBT);
        }
        stack.setTagCompound(tag);
        cir.setReturnValue(stack);
        cir.cancel();
    }
}