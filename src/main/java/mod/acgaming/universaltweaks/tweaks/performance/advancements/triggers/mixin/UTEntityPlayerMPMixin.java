package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.ISlotContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(EntityPlayerMP.class)
public class UTEntityPlayerMPMixin
{
    @WrapOperation(method = "sendSlotContents", at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/critereon/InventoryChangeTrigger;trigger(Lnet/minecraft/entity/player/EntityPlayerMP;Lnet/minecraft/entity/player/InventoryPlayer;)V"))
    private void utProvideStackContext(InventoryChangeTrigger instance, EntityPlayerMP player, InventoryPlayer inventory, Operation<Void> original, Container container, int slotIndex, ItemStack stack)
    {
        ((ISlotContext) inventory).ut$setStack(stack);
        original.call(instance, player, inventory);
        ((ISlotContext) inventory).ut$resetContext();
    }
}
