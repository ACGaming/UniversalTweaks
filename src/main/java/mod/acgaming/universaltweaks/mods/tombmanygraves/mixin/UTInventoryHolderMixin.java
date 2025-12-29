package mod.acgaming.universaltweaks.mods.tombmanygraves.mixin;

import com.m4thg33k.tombmanygraves.invman.InventoryHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of WaitingIdly
@Mixin(value = InventoryHolder.class, remap = false)
public abstract class UTInventoryHolderMixin
{
    /**
     * @author WaitingIdly
     * @reason Change the timestamp to use an ISO 8601-based format,
     * ignoring milliseconds and time zone.
     */
    @ModifyConstant(method = "grabPlayerData(Lnet/minecraft/entity/player/EntityPlayer;)V", constant = @Constant(stringValue = "MM_dd_YYYY_HH_mm_ss"))
    private String utChangeTimestamp(String constant)
    {
        return "yyyy-MM-dd'T'HH:mm:ss";
    }
}
