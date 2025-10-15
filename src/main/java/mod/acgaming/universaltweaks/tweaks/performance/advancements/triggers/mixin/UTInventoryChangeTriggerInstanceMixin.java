package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeMatcher;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.ISlotContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(value = InventoryChangeTrigger.Instance.class)
public class UTInventoryChangeTriggerInstanceMixin
{
    @Shadow
    @Final
    private MinMaxBounds full;

    @Shadow
    @Final
    private MinMaxBounds empty;

    @Shadow
    @Final
    private MinMaxBounds occupied;

    @Shadow
    @Final
    private ItemPredicate[] items;

    /**
     * Check if any predicates match the changed stack, and if not, skip the full inventory scan.
     * Slot counts are precomputed in {@link UTInventoryChangeTriggerMixin} to avoid re-computing it for every advancement.
     * This is essentially an @Overwrite of the original method.
     */
    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    private void utFasterCriterionTest(InventoryPlayer inventory, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utFasterCriterionTest)
        {
            return;
        }
        // Test slot count bounds first, with precomputed counts
        ISlotContext.SlotCounts slotCounts = ((ISlotContext) inventory).ut$getSlotCounts();
        if (!this.full.test(slotCounts.numFull))
        {
            cir.setReturnValue(false);
            return;
        }
        else if (!this.empty.test(slotCounts.numEmpty))
        {
            cir.setReturnValue(false);
            return;
        }
        else if (!this.occupied.test(slotCounts.numOccupied))
        {
            cir.setReturnValue(false);
            return;
        }

        ItemStack changedStack = ((ISlotContext) inventory).ut$getStack();
        int numPredicates = items.length;
        if (numPredicates == 0)
        {
            cir.setReturnValue(true);
        }
        else if (numPredicates == 1)
        {
            // Directly test the predicate with the changed stack
            ItemPredicate predicate = items[0];
            cir.setReturnValue(!changedStack.isEmpty() && ((IPrevSizeMatcher) predicate).ut$sizeCheckedMatch(changedStack));
        }
        else
        {
            // Use a tracking array for matched predicates instead of removeIf()
            boolean[] matched = new boolean[numPredicates];
            int remaining = numPredicates;

            // Check that the changed stack can be matched first
            for (int predicateIndex = 0; predicateIndex < numPredicates; predicateIndex++)
            {
                if (((IPrevSizeMatcher) items[predicateIndex]).ut$sizeCheckedMatch(changedStack))
                {
                    matched[predicateIndex] = true;
                    remaining--;
                    break;
                }
            }
            // The changed stack was not matched by any predicate, skip other requirements
            if (remaining == numPredicates)
            {
                cir.setReturnValue(false);
                return;
            }

            // Then do a full inventory scan for the remaining predicates
            int invSize = inventory.getSizeInventory();
            for (int i = 0; i < invSize && remaining > 0; i++)
            {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack.isEmpty())
                {
                    continue;
                }

                for (int predicateIndex = 0; predicateIndex < numPredicates; predicateIndex++)
                {
                    if (!matched[predicateIndex] && (((IPrevSizeMatcher) items[predicateIndex]).ut$sizeCheckedMatch(stack)))
                    {
                        matched[predicateIndex] = true;
                        if (--remaining == 0)
                        {
                            cir.setReturnValue(true);
                            return;
                        }
                    }
                }
            }
            cir.setReturnValue(false);
        }
    }
}
