package mod.acgaming.universaltweaks.mods.simplyjetpacks.network.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;
import tonius.simplyjetpacks.handler.SyncHandler;

// Courtesy of jchung01
public class MessageClientStatesReset implements IMessage, IMessageHandler<MessageClientStatesReset, IMessage>
{
    public MessageClientStatesReset() {}

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    @Override
    public IMessage onMessage(MessageClientStatesReset message, MessageContext ctx)
    {
        // Just clear the maps, the client-side ones should only track the client's player.
        SyncHandler.clearAll();
        return null;
    }
}
