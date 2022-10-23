package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTOffhand
{
    @SubscribeEvent
    public static void utOffhand(PlayerInteractEvent.RightClickBlock event)
    {
        if (!UTConfig.tweaks.utOffhandToggle) return;
        EntityPlayer player = event.getEntityPlayer();
        Item heldItemMainhand = player.getHeldItemMainhand().getItem();
        Item heldItemOffhand = player.getHeldItemOffhand().getItem();
        if (event.getHand() == EnumHand.OFF_HAND
            && heldItemOffhand instanceof ItemBlock
            && (heldItemMainhand instanceof ItemBlock || heldItemMainhand instanceof ItemFood))
            event.setUseItem(Event.Result.DENY);
    }
}