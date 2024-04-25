package mod.acgaming.universaltweaks.util.particle;

import io.netty.buffer.ByteBuf;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.util.UTPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.List;
import java.util.Random;

public class UTParticleSpawnerMessage implements IMessage
{
    private static final Random avRandomizerCopy = new Random();

    public static void send(WorldServer world, EnumParticleTypes type, UTParticleSituationEnum situation, double x, double y, double z, double xOffset, double yOffset, double zOffset, double speed, int count, int... particleArguments)
    {
        send(world, type, situation, false, x, y, z, xOffset, yOffset, zOffset, speed, count, particleArguments);
    }

    public static void send(WorldServer world, EnumParticleTypes type, UTParticleSituationEnum situation, boolean longDistance, double x, double y, double z, double xOffset, double yOffset, double zOffset, double speed, int count, int... particleArguments)
    {
        final List<EntityPlayer> players = world.playerEntities;
        for (int i = 0; i < players.size(); i++)
        {
            EntityPlayerMP player = (EntityPlayerMP) players.get(i);
            BlockPos pos = player.getPosition();
            double posSq = pos.distanceSq(x, y, z);
            if (posSq <= 1024.0D || longDistance && posSq <= 262144.0D)
            {
                UTPacketHandler.instance.sendTo(new UTParticleSpawnerMessage(type, situation, longDistance, (float) x, (float) y, (float) z, (float) xOffset, (float) yOffset, (float) zOffset, (float) speed, count, particleArguments), player);
            }
        }
    }

    // Base properties are from SPacketParticles.class
    private EnumParticleTypes particleType;
    private UTParticleSituationEnum situation;
    private float xCoord;
    private float yCoord;
    private float zCoord;
    private float xOffset;
    private float yOffset;
    private float zOffset;
    private float particleSpeed;
    private int particleCount;
    private boolean longDistance;
    /** These are the block/item ids and possibly metaData ids that are used to color or texture the particle. */
    private int[] particleArguments;
        
    public UTParticleSpawnerMessage() {}

    private UTParticleSpawnerMessage(EnumParticleTypes type, UTParticleSituationEnum situation, boolean longDistance, float x, float y, float z, float xOffset, float yOffset, float zOffset, float speed, int count, int... particleArguments)
    {
        this.particleType = type;
        this.situation = situation;
        this.longDistance = longDistance;
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
        this.particleSpeed = speed;
        this.particleCount = count;
        this.particleArguments = particleArguments;
    }
    
    @Override
    public void fromBytes(ByteBuf buf)
    {
        PacketBuffer buffer = new PacketBuffer(buf);
        this.particleType = EnumParticleTypes.getParticleFromId(buffer.readInt());
        if (this.particleType == null)
        {
            this.particleType = EnumParticleTypes.BARRIER;
        }
        this.situation = UTParticleSituationEnum.VALUES[buffer.readByte()];
        this.longDistance = buffer.readBoolean();
        this.xCoord = buffer.readFloat();
        this.yCoord = buffer.readFloat();
        this.zCoord = buffer.readFloat();
        this.xOffset = buffer.readFloat();
        this.yOffset = buffer.readFloat();
        this.zOffset = buffer.readFloat();
        this.particleSpeed = buffer.readFloat();
        this.particleCount = buffer.readInt();
        int i = this.particleType.getArgumentCount();
        this.particleArguments = new int[i];
        for (int j = 0; j < i; j++)
        {
            this.particleArguments[j] = buffer.readVarInt();
        }
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeInt(this.particleType.getParticleID());
        buffer.writeByte(this.situation.ordinal());
        buffer.writeBoolean(this.longDistance);
        buffer.writeFloat(this.xCoord);
        buffer.writeFloat(this.yCoord);
        buffer.writeFloat(this.zCoord);
        buffer.writeFloat(this.xOffset);
        buffer.writeFloat(this.yOffset);
        buffer.writeFloat(this.zOffset);
        buffer.writeFloat(this.particleSpeed);
        buffer.writeInt(this.particleCount);
        for (int i = 0; i < this.particleType.getArgumentCount(); i++)
        {
            buffer.writeVarInt(this.particleArguments[i]);
        }
    }

    public static class Handler implements IMessageHandler<UTParticleSpawnerMessage, IMessage>
    {
        @Override
        public IMessage onMessage(UTParticleSpawnerMessage message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() ->
            {
                if (message.particleArguments.length == 0)
                {
                    double d0 = message.particleSpeed * message.xOffset;
                    double d2 = message.particleSpeed * message.yOffset;
                    double d4 = message.particleSpeed * message.zOffset;
                    try
                    {
                        message.situation.spawn(message.particleType, message.longDistance, message.xCoord, message.yCoord, message.zCoord, d0, d2, d4, message.particleArguments);
                    }
                    catch (Throwable t)
                    {
                        UniversalTweaks.LOGGER.warn("Could not spawn particle effect {}", message.particleType, t);
                    }
                }
                else
                {
                    for (int k = 0; k < message.particleCount; k++)
                    {
                        double d1 = avRandomizerCopy.nextGaussian() * (double) message.xOffset;
                        double d3 = avRandomizerCopy.nextGaussian() * (double) message.yOffset;
                        double d5 = avRandomizerCopy.nextGaussian() * (double) message.zCoord;
                        double d6 = avRandomizerCopy.nextGaussian() * (double) message.particleSpeed;
                        double d7 = avRandomizerCopy.nextGaussian() * (double) message.particleSpeed;
                        double d8 = avRandomizerCopy.nextGaussian() * (double) message.particleSpeed;
                        try
                        {
                            message.situation.spawn(message.particleType, message.longDistance, message.xCoord + d1, message.yCoord + d3, message.zCoord + d5, d6, d7, d8, message.particleArguments);
                        }
                        catch (Throwable t)
                        {
                            UniversalTweaks.LOGGER.warn("Could not spawn particle effect {}", message.particleType, t);
                            return;
                        }
                    }
                } 
            });
            return null;
        }
    }
}
