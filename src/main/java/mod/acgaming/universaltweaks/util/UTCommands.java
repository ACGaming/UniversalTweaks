package mod.acgaming.universaltweaks.util;

import net.minecraftforge.client.ClientCommandHandler;

import mod.acgaming.universaltweaks.tweaks.misc.commands.seed.UTCopyCommand;

public class UTCommands
{
    public static void initClientCommands()
    {
        ClientCommandHandler.instance.registerCommand(new UTCopyCommand());
    }
}
