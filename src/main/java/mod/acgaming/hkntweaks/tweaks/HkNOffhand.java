package mod.acgaming.hkntweaks.tweaks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.hkntweaks.HkNTweaks;
import mod.acgaming.hkntweaks.config.HkNTweaksConfig;

@Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
public class HkNOffhand
{
    @SubscribeEvent
    public static void hknOffhand(PlayerInteractEvent.RightClickBlock event)
    {
        if (!HkNTweaksConfig.tweaks.hknOffhandToggle) return;
        EntityPlayer player = event.getEntityPlayer();
        Item heldItemMainhand = player.getHeldItemMainhand().getItem();
        Item heldItemOffhand = player.getHeldItemOffhand().getItem();
        if (event.getHand() == EnumHand.OFF_HAND
            && heldItemOffhand instanceof ItemBlock
            && (heldItemMainhand instanceof ItemBlock || heldItemMainhand instanceof ItemFood))
            event.setUseItem(Event.Result.DENY);
    }
}