package mod.acgaming.universaltweaks.bugfixes.misc.gui.mixin;

import net.minecraftforge.client.GuiIngameForge;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(value = GuiIngameForge.class, remap = false)
public abstract class UTOverlayMessageFadeOutMixin
{
    @ModifyConstant(method = "renderRecordOverlay", constant = @Constant(intValue = 0, ordinal = 1, expandZeroConditions = Constant.Condition.GREATER_THAN_ZERO))
    public int utOverlayMessageFadeOut(int constant)
    {
        return UTConfigBugfixes.MISC.utOverlayMessageFadeOut ? 8 : constant;
    }
}