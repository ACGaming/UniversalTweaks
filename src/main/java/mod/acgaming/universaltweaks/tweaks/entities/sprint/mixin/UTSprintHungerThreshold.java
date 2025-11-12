package mod.acgaming.universaltweaks.tweaks.entities.sprint.mixin;

import net.minecraft.client.entity.EntityPlayerSP;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityPlayerSP.class)
public class UTSprintHungerThreshold
{
    @ModifyConstant(method = "onLivingUpdate", constant = @Constant(floatValue = 6.0F))
    private float utHungerThresholdToSprint(float threshold)
    {
        if (threshold == 6.0f) return (float)UTConfigTweaks.ENTITIES.utSprintHungerThreshold;
        return threshold;
    }
}