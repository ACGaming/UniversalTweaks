package mod.acgaming.universaltweaks.bugfixes.entities.maxhealth;

import java.util.Locale;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Laike-Endaril
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTMaxHealth
{
    @SubscribeEvent
    public static void utPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMaxHealth ::: Player logged in event");
        event.player.getTags().add("loginhpgo");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void utPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utMaxHealthToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMaxHealth ::: Player tick event");
        if (event.side == Side.SERVER && event.phase == TickEvent.Phase.END)
        {
            EntityPlayer player = event.player;
            Set<String> strings = player.getTags();
            for (String s : strings.toArray(new String[0]))
            {
                if (s.contains("loginhp") && !s.equals("loginhpgo"))
                {
                    if (strings.contains("loginhpgo"))
                    {
                        try
                        {
                            player.setHealth(player.getMaxHealth() * Float.parseFloat(s.replace("loginhp", "")));
                        }
                        catch (NumberFormatException exception)
                        {
                            UniversalTweaks.LOGGER.error("Invalid health value: " + s);
                        }
                        strings.remove("loginhpgo");
                    }
                    strings.remove(s);
                    break;
                }
            }
            strings.add("loginhp" + String.format(Locale.ENGLISH, "%.2f", Math.max(player.getHealth() / player.getMaxHealth(), 1.00F)));
        }
    }
}