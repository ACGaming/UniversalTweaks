package mod.acgaming.universaltweaks.mods.extrautilities.radar.mixin;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.rwtema.extrautils2.crafting.Radar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigMods;

// Courtesy of WaitingIdly
@Mixin(value = Radar.PacketPing.class, remap = false)
public abstract class UTRadarPingMixin
{
    @WrapOperation(method = "lambda$doStuffServer$1", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/items/IItemHandler;getStackInSlot(I)Lnet/minecraft/item/ItemStack;"))
    private ItemStack utCatchItemHandlerException(IItemHandler instance, int slot, Operation<ItemStack> original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utCatchRadarException) return original.call(instance, slot);
        try
        {
            return original.call(instance, slot);
        }
        catch (AbstractMethodError e)
        {
            e.printStackTrace();
            return ItemStack.EMPTY;
        }
    }
}
