package mod.acgaming.universaltweaks.bugfixes.misc.help;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandHelp;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of matthewprenger
@SuppressWarnings("NullableProblems")
public class UTHelp
{
    private static final ICommand testCmd1 = new CommandBase()
    {
        @Override
        public String getName()
        {
            return "a";
        }

        @Override
        public String getUsage(final ICommandSender sender)
        {
            return null;
        }

        @Override
        public void execute(final MinecraftServer server, final ICommandSender sender, String[] args)
        {
        }
    };
    private static final ICommand testCmd2 = new CommandBase()
    {
        @Override
        public String getName()
        {
            return "z";
        }

        @Override
        public String getUsage(final ICommandSender sender)
        {
            return null;
        }

        @Override
        public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args)
        {
        }
    };

    public static void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHelp ::: Server starting event");
        event.registerServerCommand(new CommandHelp()
        {
            @SuppressWarnings("ConstantConditions")
            @Override
            protected List<ICommand> getSortedPossibleCommands(final ICommandSender sender, final MinecraftServer server)
            {
                final List<ICommand> list = server.getCommandManager().getPossibleCommands(sender);
                final Iterator<ICommand> iterator = list.iterator();
                while (iterator.hasNext())
                {
                    final ICommand command = iterator.next();
                    try
                    {
                        if (command.getName() == null)
                        {
                            UniversalTweaks.LOGGER.warn("UTHelp ::: Identified command with null name, Ignoring: {}", command.getClass().getName());
                            iterator.remove();
                        }
                        else if (command.getUsage(sender) == null)
                        {
                            UniversalTweaks.LOGGER.warn("UTHelp ::: Identified command with null usage, Ignoring: {}", command.getClass().getName());
                            iterator.remove();
                        }
                    }
                    catch (Exception e)
                    {
                        UniversalTweaks.LOGGER.warn("UTHelp ::: Failed to test command '{}'", command, e);
                    }
                }
                list.sort(Comparator.comparing(ICommand::getName));
                return list;
            }
        });
    }

    public static void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHelp ::: Server started event");
        Collection<ICommand> commands = FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager().getCommands().values();
        for (final ICommand command : commands)
        {
            if (!(validCompareTo(command)))
            {
                UniversalTweaks.LOGGER.warn("UTHelp ::: Command {} from class {} incorrectly overrides compareTo: %s", command.getName(), command.getClass().getName());
            }
        }
    }

    /**
     * Checks to see if an {@link net.minecraft.command.ICommand ICommand} has a valid compareTo method
     *
     * @param command the command
     * @return {@code true} if the compareTo method is valid, {@code false} if not
     */
    static boolean validCompareTo(@Nonnull final ICommand command)
    {
        try
        {
            return command.compareTo(testCmd1) != command.compareTo(testCmd2);
        }
        catch (Exception e)
        {
            UniversalTweaks.LOGGER.warn("UTHelp ::: Failed to test command '{}' for a valid compareTo", command, e);
            return true; // True because we don't know that the impl is bad, just that it throws an exception
        }
    }
}