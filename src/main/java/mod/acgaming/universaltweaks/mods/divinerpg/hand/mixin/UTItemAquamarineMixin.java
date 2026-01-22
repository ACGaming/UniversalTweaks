package mod.acgaming.universaltweaks.mods.divinerpg.hand.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import divinerpg.objects.items.arcana.ItemAquamarine;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ItemAquamarine.class, remap = false)
public abstract class UTItemAquamarineMixin
{
    /**
     * @author WaitingIdly
     * @reason fix completely ignoring the actual itemstack being used and instead creating and modifying the durability of a new instance.
     */
    @WrapOperation(method = "onItemUse", at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item;)Lnet/minecraft/item/ItemStack;"), remap = true)
    private ItemStack utUseCorrectHand(Item itemIn, Operation<ItemStack> original, @Local(argsOnly = true) EntityPlayer player, @Local(argsOnly = true) EnumHand hand)
    {
        if (!UTConfigMods.DIVINE_RPG.utFixHandConsumption) return original.call(itemIn);
        return player.getHeldItem(hand);
    }
}
