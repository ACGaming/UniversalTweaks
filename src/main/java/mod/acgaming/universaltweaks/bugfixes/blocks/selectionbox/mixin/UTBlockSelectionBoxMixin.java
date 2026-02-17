package mod.acgaming.universaltweaks.bugfixes.blocks.selectionbox.mixin;

import net.minecraft.client.renderer.RenderGlobal;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of Invadermonky
@Mixin(RenderGlobal.class)
public abstract class UTBlockSelectionBoxMixin
{
    @ModifyConstant(method = "drawSelectionBox", constant = @Constant(doubleValue = 0.0020000000949949026D))
    private double utBlockSelectionBox(double original)
    {
        return UTConfigBugfixes.BLOCKS.utBlockSelectionBoxOffset;
    }
}
