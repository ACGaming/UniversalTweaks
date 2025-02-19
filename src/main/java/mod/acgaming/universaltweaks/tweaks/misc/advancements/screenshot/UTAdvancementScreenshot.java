package mod.acgaming.universaltweaks.tweaks.misc.advancements.screenshot;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTPacketHandler;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTAdvancementScreenshot
{
    private static long lastScreenshotTick = 0;

    @SubscribeEvent
    public static void utAdvancementScreenshot(AdvancementEvent event)
    {
        if (!UTConfigTweaks.MISC.utAdvancementScreenshotToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAdvancementScreenshot ::: Advancement event");
        long currentTick = Minecraft.getMinecraft().world.getTotalWorldTime();
        if (currentTick - lastScreenshotTick > 20)
        {
            UTPacketHandler.instance.sendTo(new UTAdvancementScreenshotMessage(), (EntityPlayerMP) event.getEntityPlayer());
            lastScreenshotTick = currentTick;
        }
    }
}
