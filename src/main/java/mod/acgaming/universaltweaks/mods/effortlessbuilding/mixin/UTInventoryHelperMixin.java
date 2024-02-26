package mod.acgaming.universaltweaks.mods.effortlessbuilding.mixin;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import nl.requios.effortlessbuilding.helper.InventoryHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryHelper.class)
public class UTInventoryHelperMixin
{

    @Inject(method = "findItemStackInInventory", at = @At("HEAD"), cancellable = true, remap = false)
    private static void utFindItemStackInInventory(EntityPlayer player, Block block, CallbackInfoReturnable<ItemStack> cir)
    {
        if (UTConfigMods.EFFORTLESS_BUILDING.utEFTransmutationFixToggle) cir.setReturnValue(ItemStack.EMPTY);
    }
}