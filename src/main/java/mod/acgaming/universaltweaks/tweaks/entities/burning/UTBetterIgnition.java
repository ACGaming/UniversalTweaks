package mod.acgaming.universaltweaks.tweaks.entities.burning;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTBetterIgnition
{
    @SubscribeEvent
    public static void utBetterIgnition(PlayerInteractEvent.EntityInteract event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterIgnition ::: Right click entity event");
        EnumHand hand = event.getHand();
        EntityPlayer player = event.getEntityPlayer();
        ItemStack stackMainhand = player.getHeldItemMainhand();
        ItemStack stackOffhand = player.getHeldItemOffhand();
        if (hand == EnumHand.MAIN_HAND && stackMainhand.getItem() instanceof ItemFlintAndSteel) stackMainhand.damageItem(1, player);
        else if (hand == EnumHand.OFF_HAND && stackOffhand.getItem() instanceof ItemFlintAndSteel) stackOffhand.damageItem(1, player);
        else return;
        World world = event.getWorld();
        Entity target = event.getTarget();
        world.playSound(player, target.getPosition(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.rand.nextFloat() * 0.4F + 0.8F);
        target.setFire(8);
    }
}