package mod.acgaming.universaltweaks.tweaks.performance.connectionspeed;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.google.common.net.InetAddresses;

import mod.acgaming.universaltweaks.UniversalTweaks;

public class UTConnectionPatch
{
    public static InetAddress patch(String hostName) throws UnknownHostException
    {
        return patch(InetAddress.getByName(hostName), hostName);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static InetAddress patch(InetAddress original, String hostName) throws UnknownHostException
    {
        if (InetAddresses.isInetAddress(hostName))
        {
            InetAddress patched = InetAddress.getByAddress(original.getHostAddress(), original.getAddress());
            UniversalTweaks.LOGGER.debug("Patching ip-only InetAddress from {} to {}", original, patched);
            return patched;
        }
        return original;
    }
}
