package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.entity.item.EntityBoat;

import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(EntityBoat.class)
public class UTBoatSpeedMixin
{
    @ModifyConstant(method = "controlBoat", constant = @Constant(floatValue = 0.04F))
    public float utBoatSpeed(float constant)
    {
        return (float) UTConfig.TWEAKS_ENTITIES.utBoatSpeed;
    }
}