package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers;

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
     */
    ItemStack ut$getStack();

    /**
     * Set the stack that was changed.
     */
    void ut$setStack(ItemStack stack);

    void ut$resetContext();

    class SlotCounts
    {
        public final int numFull;
        public final int numEmpty;
        public final int numOccupied;

        public SlotCounts(int numFull, int numEmpty, int numOccupied)
        {
            this.numFull = numFull;
            this.numEmpty = numEmpty;
            this.numOccupied = numOccupied;
        }
    }
}
