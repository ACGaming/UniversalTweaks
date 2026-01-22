package mod.acgaming.universaltweaks.mods.immersivenegineering.toolevent.mixin;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

import blusunrize.immersiveengineering.common.items.ItemIETool;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = ItemIETool.class, remap = false)
public abstract class UTIEToolMixin
{
    /**
     * @author WaitingIdly
     * @reason if the container itemstack used is empty, fire {@link PlayerDestroyItemEvent} with the original stack.
     */
    @Inject(method = "getContainerItem", at = @At(value = "INVOKE", target = "Lblusunrize/immersiveengineering/common/items/ItemIETool;damageIETool(Lnet/minecraft/item/ItemStack;ILjava/util/Random;Lnet/minecraft/entity/player/EntityPlayer;)V", shift = At.Shift.AFTER))
    private void utFireDestroyForContainer(ItemStack stack, CallbackInfoReturnable<ItemStack> cir, @Local(ordinal = 1) ItemStack container)
    {
        if (container.isEmpty())
        {
            ForgeEventFactory.onPlayerDestroyItem(ForgeHooks.getCraftingPlayer(), stack, null);
        }
    }

    /**
     * @author WaitingIdly
     * @reason store a copy of the original itemstack for use in {@link #utFireDestroyOnDamage}.
     */
    @Inject(method = "damageIETool", at = @At("HEAD"))
    private void utStoreHand(ItemStack stack, int amount, Random rand, EntityPlayer player, CallbackInfo ci, @Share("original") LocalRef<ItemStack> original)
    {
        original.set(stack.copy());
    }

    /**
     * @author WaitingIdly
     * @reason if the current stack is empty and player is not null
     * (which means not handled by {@link #utFireDestroyForContainer}) fire {@link PlayerDestroyItemEvent} with the original itemstack.
     * Due to not having direct access to the hand used, we use the best approximation of "main hand then offhand" - since this section will only
     * be called when breaking a block, there should not be situations where this approximation is incorrect.
     */
    @Inject(method = "damageIETool", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;shrink(I)V", shift = At.Shift.AFTER))
    private void utFireDestroyOnDamage(ItemStack stack, int amount, Random rand, EntityPlayer player, CallbackInfo ci, @Share("original") LocalRef<ItemStack> original)
    {
        // use original instead of stack for the destroy part because the stack has been mutated
        if (stack.isEmpty() && player != null)
        {
            ItemStack itemStack = original.get();
            EnumHand hand = null;
            if (player.getHeldItem(EnumHand.MAIN_HAND).isItemEqual(itemStack)) hand = EnumHand.MAIN_HAND;
            else if (player.getHeldItem(EnumHand.OFF_HAND).isItemEqual(itemStack)) hand = EnumHand.OFF_HAND;
            ForgeEventFactory.onPlayerDestroyItem(player, itemStack, hand);
        }
    }

    /**
     * @author WaitingIdly
     * @reason if the itemstack is being set to empty, fire {@link PlayerDestroyItemEvent} with the original stack.
     */
    @Inject(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setItemStackToSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER), remap = true)
    private void utFireDestroyOnUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<EnumActionResult> cir, @Local(ordinal = 0) ItemStack stack)
    {
        ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
    }
}