package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeMatcher;
import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeTracker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of jchung01
@Mixin(value = ItemPredicate.class)
public abstract class UTItemPredicateMixin implements IPrevSizeMatcher
{
    @Shadow
    @Final
    private MinMaxBounds count;

    @Shadow
    public abstract boolean test(ItemStack item);

    /**
     * Check the previous stack count to avoid matching if not within bounds.
     */
    @Override
    public boolean ut$sizeCheckedMatch(ItemStack stack)
    {
        if (!UTConfigTweaks.PERFORMANCE.ADVANCEMENT_TRIGGERS.utCompareSizeBeforePredicateMatch)
        {
            return test(stack);
        }
        Float min = ((MinMaxBoundsAccessor) count).getMin();
        Float max = ((MinMaxBoundsAccessor) count).getMax();
        int size = stack.getCount();
        int prevSize = ((IPrevSizeTracker) (Object) stack).ut$getPreviousStackSize();

        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTItemPredicate ::: Checking stack {} for range [{}, {}]", stack, min, max);

        boolean crossesMin = min == null ? prevSize == 0 : prevSize < min && min <= size;
        boolean withinMax = max == null || size <= max;
        return (crossesMin && withinMax) && test(stack);
    }
}
