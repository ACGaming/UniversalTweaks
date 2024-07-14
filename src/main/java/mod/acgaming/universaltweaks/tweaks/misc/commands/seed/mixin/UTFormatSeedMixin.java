package mod.acgaming.universaltweaks.tweaks.misc.commands.seed.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.commands.seed.UTCopyCommand;
import net.minecraft.command.CommandShowSeed;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of jchung01
@Mixin(CommandShowSeed.class)
public class UTFormatSeedMixin
{
    @ModifyExpressionValue(method = "execute", at = @At(value = "NEW", target = "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/util/text/TextComponentTranslation;"))
    private TextComponentTranslation utFormatSeedMessage(TextComponentTranslation message)
    {
        if (UTConfigTweaks.MISC.utCopyWorldSeedToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFormatSeedMixin :: Format seed");
            // Format seed
            Style style = new Style();
            style.setColor(TextFormatting.GREEN).setUnderlined(true);
            // Make & set events
            style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to copy seed to clipboard")));
            style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, UTCopyCommand.copyCommandBase + message.getFormatArgs()[0]));
            message.setStyle(style);
        }
        return message;
    }
}