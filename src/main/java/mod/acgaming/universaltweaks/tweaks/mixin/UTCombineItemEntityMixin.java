package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Darkhax
@Mixin(EntityItem.class)
public abstract class UTCombineItemEntityMixin
{
    @Shadow
    public abstract ItemStack getItem();

    @Inject(method = "searchForOtherItemsNearby()V", at = @At("HEAD"), cancellable = true)
    public void utSearchForOtherItemsNearby(CallbackInfo info)
    {
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCombineItemEntityMixin ::: Search for other items nearby");
        final ItemStack stack = this.getItem();
        if (stack.getCount() >= stack.getMaxStackSize()) info.cancel();
    }

    @Inject(method = "combineItems(Lnet/minecraft/entity/item/EntityItem;)Z", at = @At("HEAD"), cancellable = true)
    public void utCombineItems(EntityItem other, CallbackInfoReturnable<Boolean> info)
    {
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCombineItemEntityMixin ::: Combine items");
        final ItemStack stack = this.getItem();
        if (stack.getCount() >= stack.getMaxStackSize()) info.setReturnValue(false);
    }
}