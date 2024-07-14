package mod.acgaming.universaltweaks.util;

import mod.acgaming.universaltweaks.tweaks.misc.commands.seed.UTCopyCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class UTCommands
{
    public static void initClientCommands()
    {
        ClientCommandHandler.instance.registerCommand(new UTCopyCommand());
    }
}
