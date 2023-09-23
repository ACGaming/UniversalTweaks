package mod.acgaming.universaltweaks.tweaks.misc.buttons.snooper.mixin;

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptions;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GuiOptions.class)
public class UTSnooperClientButton
{
    @WrapWithCondition(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 12))
    public boolean utRemoveSnooperButton(List<GuiButton> list, Object e)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSnooperClientButton ::: Init GUI");
        return !UTConfigTweaks.MISC.utSnooperToggle;
    }
}