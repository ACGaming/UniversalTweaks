package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.storagedrawers.capabilities.DrawerItemHandler;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of PrototypeTrousers
@Mixin(value = DrawerItemHandler.class, remap = false)
public abstract class UTDrawerItemHandlerMixin
{
    @Inject(method = "insertItem", at = @At("HEAD"), cancellable = true)
    public void utInsertItem(int slot, ItemStack stack, boolean simulate, CallbackInfoReturnable<ItemStack> cir)
    {
        if (!UTConfigMods.STORAGE_DRAWERS.utSDItemHandlers) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDrawerItemHandler ::: Insert item");
        if (slot == 0)
        {
            cir.setReturnValue(stack);
            return;
        }
        cir.setReturnValue(this.insertItemInternal(--slot, stack, simulate));
    }

    @Shadow
    private ItemStack insertItemInternal(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        return null;
    }
}