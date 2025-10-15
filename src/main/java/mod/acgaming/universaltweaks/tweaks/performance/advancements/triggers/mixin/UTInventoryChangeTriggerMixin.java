package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeTracker;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.ISlotContext;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.StackSizeThresholdManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(InventoryChangeTrigger.class)
public class UTInventoryChangeTriggerMixin
{
    /**
     * This injection cancels triggering advancement scan for changed slot
     * if this trigger was caused by emptying or decreasing the stack size,
     * or if stack size increased but hasn't crossed any threshold
     */
    @SuppressWarnings("ConstantValue")
    @Inject(method = "trigger", at = @At(value = "HEAD"), cancellable = true)
    private void utOnlyTriggerForIncreasedStacks(EntityPlayerMP player, InventoryPlayer inventory, CallbackInfo ci)
    {
        ItemStack stack = ((ISlotContext) inventory).ut$getStack();
        if ((stack.isEmpty() && UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utIgnoreEmptiedStackTriggers)
            || (UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utIgnoreDecreasedStackTriggers
            && stack.getCount() < ((IPrevSizeTracker) (Object) stack).ut$getPreviousStackSize())
            || (UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utOptimizeIncreasedStackTriggers
            && !StackSizeThresholdManager.doesStackPassThreshold(stack)))
        {
            ci.cancel();
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInventoryChangeTrigger ::: Cancelling trigger for {} with previous size {}", stack, ((IPrevSizeTracker) (Object) stack).ut$getPreviousStackSize());
            return;
        }
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTInventoryChangeTrigger ::: Allowing trigger for {} with previous size {}", stack, ((IPrevSizeTracker) (Object) stack).ut$getPreviousStackSize());
        ut$prepareSlotCounts(inventory);
    }

    /**
     * Precompute the number of full (max stack size), empty, and occupied slots.
     * This allows us to speed up matching in {@link InventoryChangeTrigger.Instance}
     */
    @Unique
    private void ut$prepareSlotCounts(InventoryPlayer inventory)
    {
        int i = 0;
        int j = 0;
        int k = 0;

        int invSize = inventory.getSizeInventory();
        for (int l = 0; l < invSize; ++l)
        {
            ItemStack itemstack = inventory.getStackInSlot(l);
            if (itemstack.isEmpty())
            {
                ++j;
            }
            else
            {
                ++k;
                if (itemstack.getCount() >= itemstack.getMaxStackSize())
                {
                    ++i;
                }
            }
        }
        ((ISlotContext) inventory).ut$setSlotCounts(i, j, k);
    }

    /**
     * Add stack size thresholds that are known to be an advancement trigger criterion.
     */
    @Inject(method = "deserializeInstance(Lcom/google/gson/JsonObject;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/advancements/critereon/InventoryChangeTrigger$Instance;", at = @At(value = "RETURN"))
    private void utParseStackSizeThreshold(JsonObject json, JsonDeserializationContext context, CallbackInfoReturnable<InventoryChangeTrigger.Instance> cir, @Local ItemPredicate[] predicates)
    {
        for (ItemPredicate predicate : predicates)
        {
            Float min = (((MinMaxBoundsAccessor) ((ItemPredicateAccessor) predicate).getCount())).getMin();
            if (min != null && min > 1.0F)
            {
                StackSizeThresholdManager.add((int) min.floatValue());
            }
        }
    }
}
