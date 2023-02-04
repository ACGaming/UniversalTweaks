package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerAttributes;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of PrototypeTrousers
@Mixin(targets = "com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.FractionalDrawerGroup$FractionalStorage", remap = false)
public class UTFractionalDrawerGroupMixin
{
    @Shadow
    IDrawerAttributes attrs;

    @Inject(method = {"adjustStoredItemCount"}, at = @At(value = "HEAD"), cancellable = true)
    public void utAdjustStoredItemCount(int slot, int amount, CallbackInfoReturnable<Integer> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.STORAGE_DRAWERS.utSDItemHandlers) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFractionalDrawerGroup ::: Adjust stored item count");
        if (this.attrs.isUnlimitedVending()) cir.setReturnValue(0);
    }
}