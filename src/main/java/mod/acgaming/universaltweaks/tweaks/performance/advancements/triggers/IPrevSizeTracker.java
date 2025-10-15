package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers;

/**
 * Duck interface for {@link net.minecraft.item.ItemStack} to track previous stack size for advancement triggers
 */
public interface IPrevSizeTracker
{
    /**
     * Save the previous stack size, before the inventory change.
     *
     * @param size previous stack size
     */
    void ut$setPreviousStackSize(int size);

    /**
     * Get the previous stack size, before inventory change
     *
     * @return previous stack size
     */
    int ut$getPreviousStackSize();
}
