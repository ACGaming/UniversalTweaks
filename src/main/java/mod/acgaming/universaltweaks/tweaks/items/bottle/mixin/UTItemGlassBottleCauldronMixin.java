package mod.acgaming.universaltweaks.tweaks.items.bottle.mixin;

import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockCauldron.class)
public abstract class UTItemGlassBottleCauldronMixin
{
    @Redirect(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setHeldItem(Lnet/minecraft/util/EnumHand;Lnet/minecraft/item/ItemStack;)V", ordinal = 3))
    private void utReturnGlassBottle(EntityPlayer player, EnumHand hand, ItemStack itemstack2)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        itemstack.shrink(1);
        if (itemstack.isEmpty()) player.setHeldItem(hand, itemstack2);
        else if (!player.inventory.addItemStackToInventory(itemstack2)) player.dropItem(itemstack2, false);
    }
}
