package mod.acgaming.universaltweaks.tweaks.performance.pathfinding;

public class NavigationCacheFlag
{
    private static final ThreadLocal<Boolean> usedForNavigation = ThreadLocal.withInitial(() -> false);

    public static boolean get()
    {
        return usedForNavigation.get();
    }

    public static void set(boolean usedForNavigation)
    {
        NavigationCacheFlag.usedForNavigation.set(usedForNavigation);
    }

}
