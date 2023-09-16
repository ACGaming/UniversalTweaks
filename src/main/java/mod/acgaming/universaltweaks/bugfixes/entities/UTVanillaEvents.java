package mod.acgaming.universaltweaks.bugfixes.entities;

import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.item.ItemShears;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Focamacho
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTVanillaEvents
{
    @SubscribeEvent
    public static void utShearMooshroom(PlayerInteractEvent.EntityInteract event)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utShearMooshroomDupeToggle) return;
        if (event.getTarget() instanceof EntityMooshroom && event.getItemStack().getItem() instanceof ItemShears && event.getTarget().isDead)
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void utHorseInventory(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utDonkeyMuleDupeToggle) return;
        BlockPos playerPos = event.player.getPosition();
        event.player.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(playerPos.getX() - 32, playerPos.getY() - 32, playerPos.getZ() - 32, playerPos.getX() + 32, playerPos.getY() + 32, playerPos.getZ() + 32)).forEach(player -> {
            if (player.openContainer instanceof ContainerHorseInventory)
            {
                player.closeScreen();
            }
        });
    }
}