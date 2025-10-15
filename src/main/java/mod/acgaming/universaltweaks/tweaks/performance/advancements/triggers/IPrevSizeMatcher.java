package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers;

import net.minecraft.item.ItemStack;

/**
 * Duck interface for {@link net.minecraft.advancements.critereon.ItemPredicate}
 * to account for previous and current stack size when checking threshold.
 */
public interface IPrevSizeMatcher
{
    /**
     * Check changed stack size before trying to match
     *
     * @param stack the stack to match
     * @return match result
     */
    boolean ut$sizeCheckedMatch(ItemStack stack);
}
