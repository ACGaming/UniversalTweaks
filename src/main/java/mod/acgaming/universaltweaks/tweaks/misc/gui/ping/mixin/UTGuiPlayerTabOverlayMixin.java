package mod.acgaming.universaltweaks.tweaks.misc.gui.ping.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiPlayerTabOverlay.class)
public abstract class UTGuiPlayerTabOverlayMixin
{
    @Unique
    private static final int PING_TEXT_RENDER_OFFSET = 13;
    @Unique
    private static final String MAXIMUM_EXTRA_WIDTH_STRING = "9999ms";

    @Unique
    private static int utColorForTime(int responseTime)
    {
        if (responseTime <= 0) return 0xFFFFFF; // white
        if (responseTime <= 150) return 0x00FF00; // green
        if (responseTime <= 300) return 0xFFBB00; // yellow
        if (responseTime <= 600) return 0xFF8000; // orange
        if (responseTime < 10000) return 0xFF0000; // red
        return 0xFFFFFF; // white (again)
    }

    @Shadow
    @Final
    private Minecraft mc;

    @ModifyConstant(method = "renderPlayerlist", constant = @Constant(intValue = 13))
    private int utAdjustTotalWidth(int original)
    {
        if (!UTConfigTweaks.MISC.utBetterPing) return original;
        return original + mc.fontRenderer.getStringWidth(MAXIMUM_EXTRA_WIDTH_STRING) + 3;
    }

    @Inject(method = "drawPing", at = @At(value = "HEAD"))
    private void utDrawTimeDisplay(int x0, int x1, int y, NetworkPlayerInfo player, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utBetterPing) return;
        int responseTime = player.getResponseTime();
        // If the time is 0 or extremely long, it is probably inaccurate, and we should display ??? instead.
        String pingTimeText = responseTime <= 0 || responseTime >= 10000 ? "???ms" : String.format("%dms", responseTime);
        int pingStringWidth = mc.fontRenderer.getStringWidth(pingTimeText);
        mc.fontRenderer.drawStringWithShadow(pingTimeText, x0 + x1 - pingStringWidth - PING_TEXT_RENDER_OFFSET, y, utColorForTime(responseTime));
    }
}