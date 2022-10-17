package mod.acgaming.hkntweaks.bugfixes;

import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.hkntweaks.HkNTweaks;
import mod.acgaming.hkntweaks.config.HkNTweaksConfig;

// Courtesy of Laike-Endaril
@Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
public class HkNMaxHealth
{
    @SubscribeEvent
    public static void hknPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!HkNTweaksConfig.bugfixes.hknMaxHealthToggle) return;
        event.player.getTags().add("loginhpgo");
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void hknPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!HkNTweaksConfig.bugfixes.hknMaxHealthToggle) return;
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
                        player.setHealth(player.getMaxHealth() * Float.parseFloat(s.replace("loginhp", "")));
                        strings.remove("loginhpgo");
                    }
                    strings.remove(s);
                    break;
                }
            }
            strings.add("loginhp" + String.format("%.2f", player.getHealth() / player.getMaxHealth()));
        }
    }
}