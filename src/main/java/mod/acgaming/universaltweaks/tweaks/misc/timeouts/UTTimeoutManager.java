package mod.acgaming.universaltweaks.tweaks.misc.timeouts;

import org.apache.commons.lang3.math.NumberUtils;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTTimeoutManager
{
    public static long readTimeoutMillis;
    public static int loginTimeoutTicks;

    public static void init()
    {
        if (!UTConfigTweaks.MISC.TIMEOUTS.utTimeoutsToggle) return;
        int readTimeout = NumberUtils.toInt(System.getProperty("fml.readTimeout"), UTConfigTweaks.MISC.TIMEOUTS.utReadTimeout);
        // Don't overwrite read timeout that was specified by command line.
        if (readTimeout == UTConfigTweaks.MISC.TIMEOUTS.utReadTimeout)
        {
            System.setProperty("fml.readTimeout", String.valueOf(UTConfigTweaks.MISC.TIMEOUTS.utReadTimeout));
        }
        readTimeoutMillis = UTConfigTweaks.MISC.TIMEOUTS.utReadTimeout * 1000L;
        loginTimeoutTicks = UTConfigTweaks.MISC.TIMEOUTS.utLoginTimeout * 20;
        // fml.loginTimeout is unused
    }
}
