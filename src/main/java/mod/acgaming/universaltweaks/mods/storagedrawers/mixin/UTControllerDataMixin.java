package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController;
import com.jaquadro.minecraft.storagedrawers.block.tile.tiledata.ControllerData;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of PrototypeTrousers
@Mixin(value = ControllerData.class, remap = false)
public class UTControllerDataMixin
{
    @Shadow
    private BlockPos controllerCoord;

    private TileEntityController controller;

    @Inject(method = "getController", at = @At(value = "HEAD"), cancellable = true)
    void utGetController(TileEntity host, CallbackInfoReturnable<TileEntityController> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.STORAGE_DRAWERS.utSDItemHandlers) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTControllerData ::: Get controller");
        if (controller != null)
        {
            if (!controller.isInvalid())
            {
                cir.setReturnValue(controller);
                return;
            }
            controller = null;
            host.markDirty();
        }
        if (controllerCoord != null)
        {
            TileEntity te = host.getWorld().getTileEntity(controllerCoord);
            if (!(te instanceof TileEntityController))
            {
                controllerCoord = null;
                controller = null;
            }
            else controller = (TileEntityController) te;
            host.markDirty();
        }
        cir.setReturnValue(controller);
    }

    @Inject(method = "bindCoord", at = @At(value = "RETURN"))
    void utBindCoord(BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfig.MOD_INTEGRATION.STORAGE_DRAWERS.utSDItemHandlers) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTControllerData ::: Bind coordinates");
        controller = null;
    }
}