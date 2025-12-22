package mod.acgaming.universaltweaks.mods.bibliocraft.sign.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import jds.bibliocraft.rendering.TileEntityFancySignRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = TileEntityFancySignRenderer.class, remap = false)
public abstract class UTTileEntityFancySignRendererMixin
{
    @Shadow
    private int slotRot;

    /**
     * @author WaitingIdly
     * @reason Flip when the "slot rotation" value is 2 instead of 0,
     * to match how it is displayed in the GUI.
     */
    @Definition(id = "slotRot", field = "Ljds/bibliocraft/rendering/TileEntityFancySignRenderer;slotRot:I")
    @Expression("this.slotRot == 0")
    @ModifyExpressionValue(method = "additionalGLStuffForItemStack", at = @At(value = "MIXINEXTRAS:EXPRESSION", ordinal = 1))
    private boolean utOnlyFlip2(boolean original)
    {
        return this.slotRot == 2;
    }
}
