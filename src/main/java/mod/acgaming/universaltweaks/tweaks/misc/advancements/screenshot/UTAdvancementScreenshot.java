package mod.acgaming.universaltweaks.tweaks.misc.advancements.screenshot;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;
import java.util.Set;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.util.UTPacketHandler;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID, value = Side.CLIENT)
public class UTAdvancementScreenshot
{
    private static final Set<ResourceLocation> ADVANCEMENT_SET = new HashSet<>();
    private static long lastScreenshotTick = 0;

    public static void initAdvancementList()
    {
        ADVANCEMENT_SET.clear();
        try
        {
            for (String entry : UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotList) ADVANCEMENT_SET.add(new ResourceLocation(entry));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        UniversalTweaks.LOGGER.info("Advancement Screenshot list initialized");
    }

    @SubscribeEvent
    public static void utAdvancementScreenshot(AdvancementEvent event)
    {
        if (!UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotToggle) return;
        boolean isBlacklist = UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotListMode == UTConfigTweaks.EnumLists.BLACKLIST;
        if (ADVANCEMENT_SET.contains(event.getAdvancement().getId()) == isBlacklist) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAdvancementScreenshot ::: Advancement event");
        long currentTick = Minecraft.getMinecraft().world.getTotalWorldTime();
        if (currentTick - lastScreenshotTick > UTConfigTweaks.MISC.ADVANCEMENT_SCREENSHOT.utAdvancementScreenshotCooldown)
        {
            UTPacketHandler.instance.sendTo(new UTAdvancementScreenshotMessage(), (EntityPlayerMP) event.getEntityPlayer());
            lastScreenshotTick = currentTick;
        }
    }
}
