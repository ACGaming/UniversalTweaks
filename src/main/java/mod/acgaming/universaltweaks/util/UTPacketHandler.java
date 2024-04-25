package mod.acgaming.universaltweaks.util;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.damagetilt.UTDamageTiltMessage;
import mod.acgaming.universaltweaks.util.particle.UTParticleSpawnerMessage;

public class UTPacketHandler
{
    public static final SimpleNetworkWrapper instance = NetworkRegistry.INSTANCE.newSimpleChannel(UniversalTweaks.MODID);

    private static int id;

    public static void init()
    {
        instance.registerMessage(UTDamageTiltMessage.Handler.class, UTDamageTiltMessage.class, id++, Side.CLIENT);
        instance.registerMessage(UTParticleSpawnerMessage.Handler.class, UTParticleSpawnerMessage.class, id++, Side.CLIENT);
    }
}