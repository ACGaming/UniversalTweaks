package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemShield;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTOffhand
{
    // Don't place blocks in the offhand when blocks or food are in the mainhand
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utOffhandBlock(PlayerInteractEvent.RightClickBlock event)
    {
        if (!UTConfig.TWEAKS_MISC.utOffhandToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTOffhand ::: Right click block event");
        EntityPlayer player = event.getEntityPlayer();
        Item heldItemMainhand = player.getHeldItemMainhand().getItem();
        Item heldItemOffhand = player.getHeldItemOffhand().getItem();
        if (event.getHand() == EnumHand.OFF_HAND
            && heldItemOffhand instanceof ItemBlock
            && (heldItemMainhand instanceof ItemBlock || heldItemMainhand instanceof ItemFood))
            event.setUseItem(Event.Result.DENY);
    }

    // Don't interact with entities when wielding shields
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utOffhandEntity(PlayerInteractEvent.EntityInteract event)
    {
        if (!UTConfig.TWEAKS_MISC.utOffhandToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTOffhand ::: Right click entity event");
        EntityPlayer player = event.getEntityPlayer();
        Item heldItemMainhand = player.getHeldItemMainhand().getItem();
        Item heldItemOffhand = player.getHeldItemOffhand().getItem();
        if (event.getHand() == EnumHand.MAIN_HAND && heldItemOffhand instanceof ItemShield
            || event.getHand() == EnumHand.OFF_HAND && heldItemMainhand instanceof ItemShield)
        {
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
        }
    }
}