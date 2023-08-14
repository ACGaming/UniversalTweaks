package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import net.minecraftforge.client.GuiIngameForge;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GuiIngameForge.class, remap = false)
public class UTSelectedItemTooltipForgeMixin
{
    @ModifyConstant(method = "renderToolHighlight", constant = @Constant(intValue = 59))
    public int utSelectedItemTooltipHeight(int constant)
    {
        return UTConfig.TWEAKS_MISC.utSelectedItemTooltipHeight;
    }
}