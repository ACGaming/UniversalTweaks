package mod.acgaming.universaltweaks.mods.rftoolsdimension.mixin;

import net.minecraftforge.common.DimensionManager;

import mcjty.rftoolsdim.dimensions.RfToolsDimensionManager;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = RfToolsDimensionManager.class, remap = false)
public abstract class UTDimensionManager
{
    @Shadow
    private static RfToolsDimensionManager clientInstance;

    @Inject(method = "cleanupDimensionInformation", at = @At("HEAD"))
    private static void utClearClientInstance(CallbackInfo ci)
    {
        if (!UTConfigMods.RFTOOLS_DIMENSIONS.utFullyUnregisterDimensions) return;
        if (clientInstance != null)
        {
            UniversalTweaks.LOGGER.info("UTDimensionManager ::: Cleaning up RFTools dimensions for the clientInstance");

            for (int id : clientInstance.getDimensions().keySet())
            {
                if (DimensionManager.isDimensionRegistered(id))
                {
                    UniversalTweaks.LOGGER.info("UTDimensionManager ::: Unregister dimension: " + id);

                    try
                    {
                        DimensionManager.unregisterDimension(id);
                    }
                    catch (Exception e)
                    {
                        UniversalTweaks.LOGGER.error("UTDimensionManager ::: Error unregistering dimension: " + id, e);
                    }
                }
                else
                {
                    UniversalTweaks.LOGGER.info("UTDimensionManager ::: Already unregistered! Dimension: " + id);
                }
            }
            clientInstance.getDimensions().clear();
            ((UTRfToolsDimensionManagerAccessor) clientInstance).getDimensionToID().clear();
            ((UTRfToolsDimensionManagerAccessor) clientInstance).getDimensionInformation().clear();
            ((UTRfToolsDimensionManagerAccessor) clientInstance).getReclaimedIds().clear();
            clientInstance = null;
        }
    }
}
