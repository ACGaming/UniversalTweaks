package mod.acgaming.universaltweaks.mods.enderio.cyclebutton.mixin;

import com.enderio.core.api.client.gui.IGuiScreen;
import com.enderio.core.client.gui.button.TooltipButton;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = TooltipButton.class, remap = false)
public interface UTTooltipButtonAccessor
{
    @Accessor("gui")
    @NotNull IGuiScreen getGui();
}
