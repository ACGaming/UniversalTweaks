package mod.acgaming.universaltweaks.mods.bibliocraft.hand.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.items.ItemClipboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of WaitingIdly
@Mixin(value = ItemClipboard.class, remap = false)
public abstract class UTItemClipboardMixin
{
    @Unique
    private EnumHand universalTweaks$handHolder = EnumHand.MAIN_HAND;

    /**
     * @author WaitingIdly
     * @reason Get the held item in the hand being used to support offhand use
     */
    @Redirect(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;getHeldItemMainhand()Lnet/minecraft/item/ItemStack;"), remap = true)
    private ItemStack utGetActiveHand(EntityPlayer instance, @Local(argsOnly = true) EnumHand hand)
    {
        universalTweaks$handHolder = hand;
        return instance.getHeldItem(hand);
    }

    /**
     * @author WaitingIdly
     * @reason Replace the held item in the hand being used to support offhand use
     */
    @Redirect(method = "setClipboardBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/InventoryPlayer;setInventorySlotContents(ILnet/minecraft/item/ItemStack;)V"), remap = true)
    private void utReplaceActiveHand(InventoryPlayer instance, int index, ItemStack stack, @Local(argsOnly = true) EntityPlayer player)
    {
        player.setHeldItem(universalTweaks$handHolder, stack);
    }
}
