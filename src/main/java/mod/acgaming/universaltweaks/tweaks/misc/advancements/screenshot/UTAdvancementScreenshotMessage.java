package mod.acgaming.universaltweaks.tweaks.misc.advancements.screenshot;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ScreenShotHelper;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import io.netty.buffer.ByteBuf;

public class UTAdvancementScreenshotMessage implements IMessage
{
    public UTAdvancementScreenshotMessage() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<UTAdvancementScreenshotMessage, IMessage>
    {
        @Override
        public IMessage onMessage(UTAdvancementScreenshotMessage message, MessageContext ctx)
        {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                try
                {
                    Minecraft mc = Minecraft.getMinecraft();
                    ScreenShotHelper.saveScreenshot(mc.gameDir, mc.displayWidth, mc.displayHeight, mc.getFramebuffer());
                }
                catch (Exception ignored) {}
            });
            return null;
        }
    }
}
