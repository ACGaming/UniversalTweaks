package mod.acgaming.universaltweaks.tweaks.items.autoswitch;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTAutoSwitch
{
    @SubscribeEvent
    public static void utAutoSwitch(PlayerInteractEvent.LeftClickBlock event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAutoSwitch ::: Left click block event");
        World world = event.getWorld();
        BlockPos blockPos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();

        for (int i = 0; i < 9; i++)
        {
            ItemStack itemStack = player.inventory.getStackInSlot(i);
            if (ForgeHooks.canToolHarvestBlock(world, blockPos, itemStack) && ForgeHooks.isToolEffective(world, blockPos, itemStack))
            {
                player.inventory.currentItem = i;
                return;
            }
        }
    }
}