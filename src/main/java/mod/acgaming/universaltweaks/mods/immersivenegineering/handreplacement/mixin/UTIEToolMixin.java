package mod.acgaming.universaltweaks.mods.immersivenegineering.handreplacement.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import blusunrize.immersiveengineering.common.items.ItemIETool;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigMods;

// Courtesy of WaitingIdly
@Mixin(value = ItemIETool.class, remap = false)
public abstract class UTIEToolMixin
{
    /**
     * @author WaitingIdly
     * @reason Fix setting the stack not accounting for what hand was used
     */
    @WrapOperation(method = "onItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;setItemStackToSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;Lnet/minecraft/item/ItemStack;)V"))
    private void utReplaceCorrectItem(EntityPlayer instance, EntityEquipmentSlot slotIn, ItemStack stack, Operation<Void> original, @Local(ordinal = 0, argsOnly = true) EnumHand hand)
    {
        if (UTConfigMods.IMMERSIVE_ENGINEERING.utFixIncorrectHandReplacement)
        {
            instance.setHeldItem(hand, stack);
        }
        else
        {
            original.call(instance, slotIn, stack);
        }
    }
}