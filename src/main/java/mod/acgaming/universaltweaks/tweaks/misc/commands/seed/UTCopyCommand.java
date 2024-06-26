package mod.acgaming.universaltweaks.tweaks.misc.commands.seed;

import java.util.Arrays;
import javax.annotation.Nonnull;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.IClientCommand;

public class UTCopyCommand extends CommandBase implements IClientCommand
{
    public static final String copyCommandBase = "/universalTweaksCopy ";

    @Nonnull
    @Override
    public String getName()
    {
        return copyCommandBase.substring(1).trim();
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender)
    {
        return copyCommandBase + "<message to copy>";
    }

    @Override
    public boolean checkPermission(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender)
    {
        return true;
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, ICommandSender sender, @Nonnull String[] args) throws CommandException
    {
        // Sanity check
        if (!sender.getEntityWorld().isRemote) return;
        if (!UTConfigTweaks.MISC.utCopyWorldSeedToggle)
        {
            sender.sendMessage(new TextComponentString("\nCopying the seed is disabled on client-side, please enable in 'Universal Tweaks - Tweaks' -> 'Misc' -> 'Copy World Seed'."));
            return;
        }
        if (args.length < 1)
        {
            UniversalTweaks.LOGGER.warn("UTCopyCommand :: Malformed input! " + Arrays.toString(args));
        }
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCopyCommand :: Copy seed");
        GuiScreen.setClipboardString(buildString(args, 0));
        sender.sendMessage(new TextComponentString("\nCopied seed to clipboard!"));
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message)
    {
        return false;
    }
}
