package mod.acgaming.universaltweaks.tweaks.misc.commands.seed.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Courtesy of jchung01
@Mixin(GuiScreen.class)
public abstract class UTCopySeedMixin
{
    @Shadow
    public Minecraft mc;

    @Inject(method = "handleComponentClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiScreen;sendChatMessage(Ljava/lang/String;Z)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void utCopySeed(ITextComponent component, CallbackInfoReturnable<Boolean> cir, ClickEvent clickevent)
    {
        if (!UTConfig.TWEAKS_MISC.utCopyWorldSeedToggle) return;

        String[] splitCommand = clickevent.getValue().split(" ");
        if (!splitCommand[0].equals("/utCopySeed")) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCopySeedMixin :: Copy seed");

        GuiScreen.setClipboardString(splitCommand[1]);
        mc.player.sendMessage(new TextComponentString("Copied seed to clipboard!"));
        cir.setReturnValue(Boolean.TRUE);
    }
}