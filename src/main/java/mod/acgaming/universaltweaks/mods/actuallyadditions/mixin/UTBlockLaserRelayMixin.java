package mod.acgaming.universaltweaks.mods.actuallyadditions.mixin;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import de.ellpeck.actuallyadditions.mod.blocks.BlockLaserRelay;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = BlockLaserRelay.class)
public abstract class UTBlockLaserRelayMixin
{
    @Inject(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;isCreative()Z"))
    public void utOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float par8, float par9, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.ACTUALLY_ADDITIONS.utLaserUpgradeVoid) return;
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityLaserRelay)
        {
            ItemStack stack = player.getHeldItem(hand);
            ItemStack set = stack.copy();
            set.setCount(1);
            ((TileEntityLaserRelay) tile).inv.setStackInSlot(0, set);
            if (!player.isCreative())
            {
                player.setHeldItem(hand, StackUtil.shrink(stack, 1));
            }
            cir.setReturnValue(true);
        }
    }
}