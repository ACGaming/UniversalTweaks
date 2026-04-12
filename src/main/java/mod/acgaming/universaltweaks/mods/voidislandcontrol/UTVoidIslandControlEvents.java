package mod.acgaming.universaltweaks.mods.voidislandcontrol;

import com.bartz24.voidislandcontrol.EventHandler;
import com.bartz24.voidislandcontrol.api.IslandManager;
import com.bartz24.voidislandcontrol.api.IslandPos;
import com.bartz24.voidislandcontrol.config.ConfigOptions;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class UTVoidIslandControlEvents
{
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.toDim == ConfigOptions.worldGenSettings.baseDimension && IslandManager.CurrentIslandsList.isEmpty())
        {
            World world = DimensionManager.getWorld(event.toDim);
            BlockPos pos = new BlockPos(0, ConfigOptions.islandSettings.islandYLevel, 0);
            IslandManager.CurrentIslandsList.add(new IslandPos(0, 0));
            world.setSpawnPoint(new BlockPos(0, ConfigOptions.islandSettings.islandYLevel, 0));
            EventHandler.createSpawn(event.player, world, pos);
            IslandManager.tpPlayerToPos(event.player, pos, IslandManager.CurrentIslandsList.get(0));
        }
    }
}
