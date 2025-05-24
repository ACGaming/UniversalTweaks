package mod.acgaming.universaltweaks.mods.properpumpkins.mixin;

import net.minecraft.util.EnumFacing;

import com.Killerjdog51.PumpkinMod.Util.PumpkinEventHandler;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = PumpkinEventHandler.class, remap = false)
public abstract class UTPumpkinEventHandlerMixin
{
    /**
     * @reason valid blockstates for a carved pumpkin are horizontal only,
     * but conversion can attempt to set it to be on the vertical axis, causing a crash.
     * @author WaitingIdly
     */
    @ModifyExpressionValue(method = "clickPumpkin", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/player/PlayerInteractEvent;getFace()Lnet/minecraft/util/EnumFacing;"))
    private EnumFacing utFixVerticalFacing(EnumFacing original)
    {
        if (UTConfigMods.PROPER_PUMPKIN.utFacingFix && original.getAxis().isVertical())
        {
            return EnumFacing.NORTH;
        }
        return original;
    }
}
