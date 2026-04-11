package mod.acgaming.universaltweaks.bugfixes.blocks.anvil.mixin;

import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ContainerRepair.class)
public abstract class UTContainerRepairMixin
{
    @Redirect(method = "updateRepairOutput", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemStackDamageable()Z", ordinal = 1))
    private boolean utUpdateRepairOutput(ItemStack itemstack1)
    {
        return itemstack1.isItemStackDamageable() || itemstack1.getItem().isRepairable();
    }
}
