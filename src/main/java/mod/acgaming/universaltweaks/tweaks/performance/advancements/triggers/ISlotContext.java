package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers;

import javax.annotation.Nullable;

import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.item.ItemStack;

/**
 * Duck interface for providing context about inventory slot info.
 */
public interface ISlotContext
{
    /**
     * Gets the count of full/empty/occupied slots.
     */
    SlotCounts ut$getSlotCounts();

    /**
     * Sets the count of full/empty/occupied slots.
     */
    void ut$setSlotCounts(int numFull, int numEmpty, int numOccupied);

    /**
     * Get the stack that was changed.
     * <br>
     * An empty ItemStack means the context was passed an empty stack.
     * A null stack means the context was never passed stack info.
     */
    @Nullable
    ItemStack ut$getStack();

    /**
     * Set the stack that was changed.
     */
    void ut$setStack(ItemStack stack);

    /**
     * Reset stack info and slot counts.
     */
    void ut$resetContext();

    class SlotCounts
    {
        private final int numFull;
        private final int numEmpty;
        private final int numOccupied;

        public SlotCounts(int numFull, int numEmpty, int numOccupied)
        {
            this.numFull = numFull;
            this.numEmpty = numEmpty;
            this.numOccupied = numOccupied;
        }

        public boolean matches(MinMaxBounds full, MinMaxBounds empty, MinMaxBounds occupied)
        {
            if (!full.test(this.numFull)) {
                return false;
            } else if (!empty.test(this.numEmpty)) {
                return false;
            } else {
                return occupied.test(this.numOccupied);
            }
        }

        public int numFull()
        {
            return numFull;
        }

        public int numEmpty()
        {
            return numEmpty;
        }

        public int numOccupied()
        {
            return numOccupied;
        }
    }
}
