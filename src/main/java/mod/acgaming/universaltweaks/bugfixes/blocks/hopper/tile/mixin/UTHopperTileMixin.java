package mod.acgaming.universaltweaks.bugfixes.blocks.hopper.tile.mixin;

import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntityHopper;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TileEntityHopper.class)
public class UTHopperTileMixin
{
    // TODO: Figure out root cause
    @Redirect(method = "insertStack", at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/IInventory;isEmpty()Z"))
    private static boolean utHopperInsertStack(IInventory iInventory)
    {
        if (!UTConfig.BUGFIXES_BLOCKS.utHopperInsertToggle) return iInventory.isEmpty();
        try
        {
            iInventory.isEmpty();
        }
        catch (Exception e)
        {
            if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTHopperTile ::: Tile entity of inventory {} is unavailable!", iInventory.getDisplayName());
        }
        return false;
    }
}