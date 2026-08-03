package mod.acgaming.universaltweaks.tweaks.misc.commands.locate.mixin;

import net.minecraft.command.CommandLocate;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CommandLocate.class)
public class UTFormatLocateMixin
{
    /**
     * @author atferrys
     * @reason Replaces the original "success" {@link ITextComponent} with one the player can
     * click to teleport to that specific location. Also appends the distance to that structure.
     */
    @WrapOperation(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/command/ICommandSender;sendMessage(Lnet/minecraft/util/text/ITextComponent;)V"))
    private void utFormatLocateMessage(ICommandSender sender, ITextComponent message, Operation<Void> original)
    {
        if (UTConfigTweaks.MISC.utLocateTeleportToggle)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTFormatLocateMixin :: Format locate");

            Object[] args = ((TextComponentTranslation) message).getFormatArgs();

            String structureName = (String) args[0];
            int x = (int) args[1];
            int z = (int) args[2];

            ITextComponent coordsComponent = new TextComponentString(String.format("[%d, ~, %d]", x, z));
            Style coordsStyle = coordsComponent.getStyle();
            coordsStyle.setColor(TextFormatting.GREEN);
            coordsStyle.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentTranslation("cmd.universaltweaks.locate.tooltip")));
            coordsStyle.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, String.format("/tp @s %d ~ %d", x, z)));

            BlockPos senderPos = sender.getPosition();
            int distance = (int) senderPos.getDistance(x, senderPos.getY(), z);

            original.call(sender, new TextComponentTranslation("cmd.universaltweaks.locate", structureName, coordsComponent, distance));
            return;
        }
        original.call(sender, message);
    }
}
