package mod.acgaming.universaltweaks.mods.bibliocraft.sign.mixin;

import jds.bibliocraft.gui.GuiFancySign;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// Courtesy of WaitingIdly
@Mixin(value = GuiFancySign.class, remap = false)
public abstract class UTGuiFancySignMixin
{

    /**
     * @author WaitingIdly
     * @reason Fixes blocks in the GUI being rotated too far, turns
     * <br><code>GL11.glRotatef(-225.0F, 0.0F, 1.0F, 0.0F)</code>
     * into
     * <br><code>GL11.glRotatef(135.0f, 0.0F, 1.0F, 0.0F)</code>
     */
    @ModifyConstant(method = "drawGuiContainerBackgroundLayer", constant = @Constant(floatValue = -225.0f))
    private float utFixLeft(float original)
    {
        return 135.0f;
    }

    /**
     * @author WaitingIdly
     * @reason Fixes blocks in the GUI being rotated too far, turns
     * <br><code>GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F)</code>
     * into
     * <br><code>GL11.glRotatef(0.0f, 0.0F, 1.0F, 0.0F)</code>
     */
    @ModifyConstant(method = "drawGuiContainerBackgroundLayer", constant = @Constant(floatValue = 90.0f))
    private float utFixCenter(float original)
    {
        return 0.0f;
    }

    /**
     * @author WaitingIdly
     * @reason Fixes blocks in the GUI being rotated too far, turns
     * <br><code>GL11.glRotatef(225.0F, 0.0F, 1.0F, 0.0F)</code>
     * into
     * <br><code>GL11.glRotatef(45.0f, 0.0F, 1.0F, 0.0F)</code>
     */
    @ModifyConstant(method = "drawGuiContainerBackgroundLayer", constant = @Constant(floatValue = 225.0f))
    private float utFixRight(float original)
    {
        return 45.0f;
    }
}
