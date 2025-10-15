package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers;

import net.minecraft.item.ItemStack;

import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import mod.acgaming.universaltweaks.UniversalTweaks;

/**
 * Helper class for collecting and checking stack size thresholds.
 * Adapted from the mod Icterine.
 */
public class StackSizeThresholdManager
{
    private static final IntSortedSet stackSizeThresholds = new IntAVLTreeSet();

    public static void clear()
    {
        stackSizeThresholds.clear();
        stackSizeThresholds.add(1);
    }

    public static void add(int value)
    {
        stackSizeThresholds.add(value);
    }

    public static boolean doesStackPassThreshold(ItemStack stack)
    {
        int prevValue = ((IPrevSizeTracker) (Object) stack).ut$getPreviousStackSize();
        int newValue = stack.getCount();

        IntIterator itr = stackSizeThresholds.iterator();
        while (itr.hasNext())
        {
            int threshold = itr.nextInt();
            if (prevValue < threshold && newValue >= threshold)
            {
                return true;
            }
        }

        return false;
    }

    public static void debugPrint()
    {
        UniversalTweaks.LOGGER.debug("StackSizeThresholdManager ::: Gathered stack size thresholds - {}", stackSizeThresholds);
    }
}
