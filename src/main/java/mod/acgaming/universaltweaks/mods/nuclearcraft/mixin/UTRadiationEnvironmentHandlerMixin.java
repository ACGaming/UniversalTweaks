package mod.acgaming.universaltweaks.mods.nuclearcraft.mixin;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.config.UTConfig.ModIntegrationCategory.NuclearCraftCategory.EnumMaps;
import nc.radiation.environment.RadiationEnvironmentHandler;
import nc.radiation.environment.RadiationEnvironmentInfo;
import nc.util.FourPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// courtesy of jchung01
@Mixin(value = RadiationEnvironmentHandler.class, remap = false)
public class UTRadiationEnvironmentHandlerMixin
{
    @Mutable
    @Shadow
    @Final
    private static ConcurrentMap<FourPos, RadiationEnvironmentInfo> ENVIRONMENT;

    @Redirect(method = "<clinit>", at = @At(value = "FIELD", ordinal = 0))
    private static void utRadiationEnvironmentMap(ConcurrentMap<FourPos, RadiationEnvironmentInfo> map)
    {
        if (UTConfig.MOD_INTEGRATION.NUCLEARCRAFT.utNCRadiationEnvironmentMap == EnumMaps.CONCURRENT_LINKED_HASHMAP)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRadiationEnvironmentHandler ::: Concurrent linked hash map");
            // only replace ENVIRONMENT, ENVIRONMENT_BACKUP is not iterated on often
            ENVIRONMENT = new ConcurrentLinkedHashMap.Builder<FourPos, RadiationEnvironmentInfo>().maximumWeightedCapacity(Long.MAX_VALUE - Integer.MAX_VALUE).build();
        }
        else ENVIRONMENT = new ConcurrentHashMap();
    }
}
