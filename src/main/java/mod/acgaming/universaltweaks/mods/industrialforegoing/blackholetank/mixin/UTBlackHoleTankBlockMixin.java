package mod.acgaming.universaltweaks.mods.industrialforegoing.blackholetank.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.buuz135.industrial.tile.block.BlackHoleTankBlock;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlackHoleTankBlock.class, remap = false)
public class UTBlackHoleTankBlockMixin
{
    /**
     * @author WaitingIdly
     * @reason If the player inventory is full, {@link EntityPlayer#addItemStackToInventory(ItemStack)} will return false.
     * In that situation, the item is supposed to be dropped onto the ground.
     * The current code voids buckets if the inventory is full.
     */
    @WrapOperation(method = "onBlockActivated", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;addItemStackToInventory(Lnet/minecraft/item/ItemStack;)Z"))
    private boolean utDropFallback(EntityPlayer instance, ItemStack p_191521_1_, Operation<Boolean> original)
    {
        if (!original.call(instance, p_191521_1_))
        {
            instance.dropItem(p_191521_1_, false);
        }
        return true;
    }
}
