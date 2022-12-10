package mod.acgaming.universaltweaks.util;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.damagetilt.UTDamageTiltMessage;

public class UTPacketHandler
{
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(UniversalTweaks.MODID);

    public static void init()
    {
        instance.registerMessage(UTDamageTiltMessage.Handler.class, UTDamageTiltMessage.class, 0, Side.CLIENT);
    }
}