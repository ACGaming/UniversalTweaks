package mod.acgaming.universaltweaks.mods.aoa3.mixin;

import net.minecraft.inventory.Container;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.tslat.aoa3.utils.player.PlayerDataManager.PlayerEquipment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// courtesy of jchung01
@Mixin(value = PlayerEquipment.class, remap = false)
public class UTPlayerEquipmentMixin
{
    private boolean utShouldUpdate = false;

    @Inject(method = "tickEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/ItemHandlerHelper;giveItemToPlayer(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V"))
    private void utShouldUpdateInventory(CallbackInfo ci)
    {
        if (!UTConfigMods.AOA.utFixPlayerTickInInventorylessGui) return;
        utShouldUpdate = true;
    }

    @WrapWithCondition(method = "tickEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Container;detectAndSendChanges()V"))
    private boolean utUpdateInventoryIfAllowed(Container instance)
    {
        if (!UTConfigMods.AOA.utFixPlayerTickInInventorylessGui) return true;
        if (utShouldUpdate)
        {
            utShouldUpdate = false;
            return true;
        }
        return false;
    }
}
