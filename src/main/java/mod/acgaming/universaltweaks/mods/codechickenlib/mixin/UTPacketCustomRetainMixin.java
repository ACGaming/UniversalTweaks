package mod.acgaming.universaltweaks.mods.codechickenlib.mixin;

import java.util.List;
import java.util.function.Predicate;
import codechicken.lib.packet.PacketCustom;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * This mixin retains PacketCustom payloads, increasing the ref count by the number of players
 * that are to receive the packet. This is necessary as the payload is shared between all receivers
 * and each receiver releases the payload in {@link UTPacketCustomReleaseMixin}.
 * <p>
 * CCL doesn't fully utilize Forge's built-in networking, so this mixin adds the missing behavior that
 * {@link net.minecraftforge.fml.common.network.FMLOutboundHandler.OutboundTarget#selectNetworks} and
 * {@link net.minecraftforge.fml.common.network.FMLOutboundHandler#write} 
 * would normally do to clean up packets.
 * @author jchung01
 */
@Mixin(value = PacketCustom.class, remap = false)
public abstract class UTPacketCustomRetainMixin
{
    @Shadow
    public abstract boolean release();

    @Inject(method = "sendToClients(Lnet/minecraft/network/Packet;)V", at = @At(value = "HEAD"))
    private static void utRetainForAllClients(Packet<INetHandler> packet, CallbackInfo ci)
    {
        ut$retainForPlayers(packet, player -> true);
    }

    @Inject(method = "sendToAllAround", at = @At(value = "HEAD"))
    private static void utRetainForAllAround(Packet<INetHandler> packet, double x, double y, double z, double range, int dim, CallbackInfo ci)
    {
        ut$retainForPlayers(packet, player -> {
            if (player.dimension != dim) return false;
            double dx = player.posX - x;
            double dy = player.posY - y;
            double dz = player.posZ - z;
            return dx * dx + dy * dy + dz * dz < range * range;
        });
    }

    @Inject(method = "sendToDimension(Lnet/minecraft/network/Packet;I)V", at = @At(value = "HEAD"))
    private static void utRetainForAllInDimension(Packet<INetHandler> packet, int dim, CallbackInfo ci)
    {
        ut$retainForPlayers(packet, player -> player.dimension == dim);
    }

    @Inject(method = "sendToChunk(Lnet/minecraft/network/Packet;Lnet/minecraft/world/World;II)V", at = @At(value = "HEAD"))
    private static void utRetainForAllInChunk(Packet<INetHandler> packet, World world, int chunkX, int chunkZ, CallbackInfo ci)
    {
        PlayerChunkMapEntry playersInChunk = ((WorldServer) world).getPlayerChunkMap().getEntry(chunkX, chunkZ);
        if (playersInChunk != null) ut$retainForPlayers(packet, playersInChunk::containsPlayer);
    }

    @Inject(method = "sendToOps(Lnet/minecraft/network/Packet;)V", at = @At(value = "HEAD"))
    private static void utRetainForOps(Packet<INetHandler> packet, CallbackInfo ci)
    {
        ut$retainForPlayers(packet, player -> FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(player.getGameProfile()));
    }

    /**
     * Release this buf after copying it, just to be safe.
     */
    @Inject(method = "toPacket", at = @At(value = "RETURN"))
    private void utReleaseOriginal(CallbackInfoReturnable<FMLProxyPacket> cir)
    {
        this.release();
    }

    @Unique
    private static void ut$retainForPlayers(Packet<INetHandler> packet, Predicate<EntityPlayerMP> condition)
    {
        if (packet instanceof FMLProxyPacket)
        {
            List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
            // Check for null dispatchers like Forge's FMLOutboundHandler.OutboundTarget#selectNetworks does.
            Predicate<EntityPlayerMP> hasNetworkDispatcher = player -> player.connection.netManager.channel().attr(NetworkDispatcher.FML_DISPATCHER).get() != null;
            int retainCount = (int) (playerList.stream().filter(condition.and(hasNetworkDispatcher)).count() - 1);
            if (retainCount > 0) ((FMLProxyPacket) packet).payload().retain(retainCount);
        }
    }
}
