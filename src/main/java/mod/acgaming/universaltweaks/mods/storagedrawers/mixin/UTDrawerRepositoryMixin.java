package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawer;
import com.jaquadro.minecraft.storagedrawers.capabilities.DrawerItemRepository;
import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.mods.storagedrawers.api.IAuxData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DrawerItemRepository.class, remap = false)
public class UTDrawerRepositoryMixin
{

    @Inject(method = "insertItem",
        at = @At(value = "INVOKE_ASSIGN",
            target = "Lcom/jaquadro/minecraft/storagedrawers/api/storage/IDrawerGroup;getDrawer(I)Lcom/jaquadro/minecraft/storagedrawers/api/storage/IDrawer;"),
        cancellable = true)
    public void readData(ItemStack stack, boolean simulate, Predicate<ItemStack> predicate,
                         CallbackInfoReturnable<ItemStack> cir,
                         @Local IDrawer drawer, @Local(ordinal = 3) int slot)
    {
        if (drawer instanceof IAuxData)
        {
            int inserted = ((IAuxData) drawer).getOrCreateData().get(slot);
            if (simulate && inserted + drawer.getStoredItemCount() == drawer.getMaxCapacity())
            {
                cir.setReturnValue(stack);
            }
        }
    }
}
