package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import net.minecraft.client.gui.GuiIngame;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiIngame.class)
public class UTSelectedItemTooltipMixin
{
    @ModifyConstant(method = "renderSelectedItem", constant = @Constant(intValue = 59))
    public int utSelectedItemTooltipHeight(int constant)
    {
        return UTConfig.TWEAKS_MISC.utSelectedItemTooltipHeight;
    }
}