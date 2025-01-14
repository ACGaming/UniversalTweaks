package mod.acgaming.universaltweaks.mods.enderio.cyclebutton.mixin;

import java.io.IOException;

import com.enderio.core.api.client.gui.IGuiScreen;
import com.enderio.core.client.gui.button.CycleButton;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(targets = "com.enderio.core.client.gui.button.CycleButton$PickerOverlay", remap = false)
public abstract class UTPickerOverlayMixin
{
    @Shadow
    CycleButton<?> cycleButton;

    /**
     * @author WaitingIdly
     * @reason Update the filter that the damage button was changed when
     * interacting with any of internal buttons of the picker overlay.
     * This is required to ensure that the filter damage setting is saved.
     */
    @Inject(method = "handleMouseInput", at = @At(value = "INVOKE", target = "Lcom/enderio/core/client/gui/button/CycleButton;setMode(Ljava/lang/Enum;)V", shift = At.Shift.AFTER))
    private void utNotifyFilterOfChange(int x, int y, int b, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.ENDER_IO.utSaveFilterCycleButtonProperly) return;
        try
        {
            if (cycleButton instanceof UTTooltipButtonAccessor)
            {
                IGuiScreen gui = ((UTTooltipButtonAccessor) cycleButton).getGui();
                if (gui instanceof UTBasicItemFilterGuiAccessor)
                {
                    gui.doActionPerformed(((UTBasicItemFilterGuiAccessor) gui).getDamageB());
                }
            }
        }
        catch (IOException ignored) {}
    }
}
