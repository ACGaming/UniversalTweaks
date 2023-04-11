package mod.acgaming.universaltweaks.tweaks.misc.commands.seed.mixin;

import net.minecraft.command.CommandShowSeed;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// Courtesy of jchung01
@Mixin(CommandShowSeed.class)
public class UTFormatSeedMixin
{
    // Can probably also use @ModifyArg + MixinExtras' @Local, but in beta/subject to change
    @Inject(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void utFormatSeedCommand(MinecraftServer server, ICommandSender sender, String[] args, CallbackInfo ci, World world)
    {
        if (!UTConfig.TWEAKS_MISC.utCopyWorldSeedToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFormatSeedMixin :: Format seed");

        long seed = world.getSeed();
        TextComponentTranslation seedText = new TextComponentTranslation("commands.seed.success", seed);

        // format seed
        Style style = new Style();
        style.setColor(TextFormatting.GREEN).setUnderlined(true);

        // make & set events
        style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy seed to clipboard")));
        // use a dummy command that UTCopySeedMixin will handle
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/utCopySeed " + seed));
        seedText.setStyle(style);

        sender.sendMessage(seedText);
        ci.cancel();
    }
}