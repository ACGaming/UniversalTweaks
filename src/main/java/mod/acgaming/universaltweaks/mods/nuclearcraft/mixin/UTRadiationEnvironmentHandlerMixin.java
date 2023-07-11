package mod.acgaming.universaltweaks.mods.nuclearcraft.mixin;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.config.UTConfig.ModIntegrationCategory.NuclearCraftCategory.EnumMaps;
import nc.config.NCConfig;
import nc.radiation.environment.RadiationEnvironmentHandler;
import nc.radiation.environment.RadiationEnvironmentInfo;
import nc.tile.radiation.ITileRadiationEnvironment;
import nc.util.FourPos;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// courtesy of jchung01
@Mixin(value = RadiationEnvironmentHandler.class, remap = false)
public class UTRadiationEnvironmentHandlerMixin
{
    // Used instead of iterating through HashMap in updateRadiationEnvironment()
    private static final Queue<Pair<FourPos, RadiationEnvironmentInfo>> ENVIRONMENT_QUEUE = UTConfig.MOD_INTEGRATION.NUCLEARCRAFT.utNCRadiationEnvironmentMap == EnumMaps.CONCURRENT_LINKED_QUEUE ? new ConcurrentLinkedQueue<>() : null;
    // ENVIRONMENT will be used as the "backup" backing map if using ENVIRONMENT_QUEUE
    // ENVIRONMENT_BACKUP will be unused
    @Mutable
    @Shadow
    @Final
    private static ConcurrentMap<FourPos, RadiationEnvironmentInfo> ENVIRONMENT;

    // If using ENVIRONMENT as "backup", don't need to track ENVIRONMENT_BACKUP
    @Inject(method = "removeTile", at = @At(value = "FIELD", target = "Lnc/radiation/environment/RadiationEnvironmentHandler;ENVIRONMENT_BACKUP:Ljava/util/concurrent/ConcurrentMap;", opcode = Opcodes.GETSTATIC), cancellable = true)
    private static void utSkipRemoveFromBackup(CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.NUCLEARCRAFT.utNCRadiationEnvironmentMap != EnumMaps.CONCURRENT_LINKED_QUEUE) return;
        ci.cancel();
    }

    @Redirect(method = "<clinit>", at = @At(value = "FIELD", target = "Lnc/radiation/environment/RadiationEnvironmentHandler;ENVIRONMENT:Ljava/util/concurrent/ConcurrentMap;"))
    private static void utUseEnvironmentLinkedMap(ConcurrentMap<FourPos, RadiationEnvironmentInfo> map)
    {
        if (UTConfig.MOD_INTEGRATION.NUCLEARCRAFT.utNCRadiationEnvironmentMap == EnumMaps.CONCURRENT_LINKED_HASHMAP)
        {
            if (UTConfig.DEBUG.utDebugToggle)
                UniversalTweaks.LOGGER.debug("UTRadiationEnvironmentHandler ::: Concurrent linked hash map");
            // only replace ENVIRONMENT, ENVIRONMENT_BACKUP is not iterated on often (if at all)
            ENVIRONMENT = new ConcurrentLinkedHashMap.Builder<FourPos, RadiationEnvironmentInfo>().maximumWeightedCapacity(Long.MAX_VALUE - Integer.MAX_VALUE).build();
        }
        else ENVIRONMENT = new ConcurrentHashMap<>();
    }

    @Inject(method = "updateRadiationEnvironment", at = @At(value = "HEAD"), cancellable = true)
    private void utUseEnvironmentQueue(WorldTickEvent event, CallbackInfo ci)
    {
        if (UTConfig.MOD_INTEGRATION.NUCLEARCRAFT.utNCRadiationEnvironmentMap != EnumMaps.CONCURRENT_LINKED_QUEUE) return;
        ci.cancel();

        if (!NCConfig.radiation_enabled_public) return;

        if (event.phase != Phase.END || event.side == Side.CLIENT || !(event.world instanceof WorldServer)) return;
        int dim = event.world.provider.getDimension();

        // Don't check size here, it's expensive for queue
        int count = (1 + NCConfig.radiation_world_chunks_per_tick) / 2;
        boolean queueEmpty = false;

        while (count > 0 && !(queueEmpty = ENVIRONMENT_QUEUE.isEmpty()))
        {
            --count;

            Map.Entry<FourPos, RadiationEnvironmentInfo> environmentEntry = ENVIRONMENT_QUEUE.poll();
            if (environmentEntry == null) break;

            FourPos pos = environmentEntry.getKey();
            RadiationEnvironmentInfo info = environmentEntry.getValue();

            if (pos.getDimension() == dim)
            {
                for (Map.Entry<FourPos, ITileRadiationEnvironment> infoEntry : info.tileMap.entrySet())
                {
                    infoEntry.getValue().handleRadiationEnvironmentInfo(info);
                }
            }

            // Already removed entry with poll()

            ENVIRONMENT.put(pos, info);
        }

        if (queueEmpty)
        {
            // addAll has casting issues, use forEach
            for (Map.Entry<FourPos, RadiationEnvironmentInfo> environmentEntry : ENVIRONMENT.entrySet())
            {
                ENVIRONMENT_QUEUE.add(Pair.of(environmentEntry.getKey(), environmentEntry.getValue()));
            }
            ENVIRONMENT.clear();
        }
    }
}
