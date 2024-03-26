package mod.acgaming.universaltweaks.tweaks.entities.burning.player.mixin;

import net.minecraft.client.renderer.ItemRenderer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ItemRenderer.class)
public class UTFirstPersonBurningOverlayMixin
{
    @ModifyConstant(method = "renderFireInFirstPerson", constant = @Constant(floatValue = -0.3F))
    public float utFirstPersonBurningOverlay(float constant)
    {
        return (float) UTConfigTweaks.ENTITIES.utFirstPersonBurningOverlay;
    }
}