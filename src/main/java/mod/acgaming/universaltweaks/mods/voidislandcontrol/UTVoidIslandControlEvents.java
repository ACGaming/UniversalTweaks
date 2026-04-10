package mod.acgaming.universaltweaks.mods.voidislandcontrol;

import com.bartz24.voidislandcontrol.EventHandler;
import com.bartz24.voidislandcontrol.api.IslandManager;
import com.bartz24.voidislandcontrol.api.IslandPos;
import com.bartz24.voidislandcontrol.config.ConfigOptions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UTVoidIslandControlEvents
{
    @SubscribeEvent
    public void onPlayerChangedDimension(EntityJoinWorldEvent event)
    {
        if (event.getWorld().isRemote && !(event.getEntity() instanceof EntityPlayer)) return;

        if (event.getWorld().provider.getDimension() == ConfigOptions.worldGenSettings.baseDimension && IslandManager.CurrentIslandsList.isEmpty())
        {
            IslandManager.CurrentIslandsList.add(new IslandPos(0, 0));
            event.getWorld().setSpawnPoint(new BlockPos(0, 91, 0));
            EventHandler.createSpawn((EntityPlayer) event.getEntity(), event.getWorld(), new BlockPos(0, 88, 0));
        }
    }
}
