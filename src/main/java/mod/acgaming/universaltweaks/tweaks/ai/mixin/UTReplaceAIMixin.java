package mod.acgaming.universaltweaks.tweaks.ai.mixin;

import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.util.math.MathHelper;

import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.ai.UTDiamondAtan2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityLookHelper.class)
public class UTReplaceAIMixin
{
    @Redirect(method = "onUpdateLook", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;atan2(DD)D"))
    public double utReplaceAI(double y, double x)
    {
        if (UTConfig.tweaks.utAIReplacementToggle) return UTDiamondAtan2.atan2(y, x);
        else return MathHelper.atan2(y, x);
    }
}