package mod.acgaming.universaltweaks.mods.aoa3.mixin;

import net.minecraft.inventory.Container;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.tslat.aoa3.utils.player.PlayerDataManager.PlayerEquipment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = PlayerEquipment.class, remap = false)
public class UTPlayerEquipmentMixin
{
    @Inject(method = "tickEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/ItemHandlerHelper;giveItemToPlayer(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V"))
    private void utShouldUpdateInventory(CallbackInfo ci, @Share("shouldUpdate") LocalBooleanRef shouldUpdate)
    {
        shouldUpdate.set(true);
    }

    @WrapWithCondition(method = "tickEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;detectAndSendChanges()V", remap = true))
    private boolean utUpdateInventoryIfAllowed(Container instance, @Share("shouldUpdate") LocalBooleanRef shouldUpdate)
    {
        if (shouldUpdate.get())
        {
            shouldUpdate.set(false);
            return true;
        }
        return false;
    }
}
