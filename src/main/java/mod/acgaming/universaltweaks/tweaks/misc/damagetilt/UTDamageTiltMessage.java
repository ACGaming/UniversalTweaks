package mod.acgaming.universaltweaks.tweaks.misc.damagetilt;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import io.netty.buffer.ByteBuf;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Charles445
public class UTDamageTiltMessage implements IMessage
{
    private float attackedAtYaw;

    public UTDamageTiltMessage() {}

    public UTDamageTiltMessage(EntityLivingBase entity)
    {
        this.attackedAtYaw = entity.attackedAtYaw;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.attackedAtYaw = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeFloat(this.attackedAtYaw);
    }

    public static class Handler implements IMessageHandler<UTDamageTiltMessage, IMessage>
    {
        @SideOnly(Side.CLIENT)
        public static void fromMessage(UTDamageTiltMessage message)
        {
            if (!UTConfig.TWEAKS_MISC.utDamageTiltToggle) return;
            Minecraft.getMinecraft().player.attackedAtYaw = message.attackedAtYaw;
        }

        @Override
        public IMessage onMessage(UTDamageTiltMessage message, MessageContext ctx)
        {
            if (ctx.side == Side.CLIENT) Minecraft.getMinecraft().addScheduledTask(() -> fromMessage(message));
            return null;
        }
    }
}