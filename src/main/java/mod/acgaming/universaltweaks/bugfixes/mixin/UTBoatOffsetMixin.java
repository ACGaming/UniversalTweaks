package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.item.EntityBoat;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

// MC-90084
// https://bugs.mojang.com/browse/MC-90084
@Mixin(EntityBoat.class)
public class UTBoatOffsetMixin
{
    @ModifyConstant(method = "getMountedYOffset", constant = @Constant(doubleValue = -0.1))
    public double utBoatOffset(double offset)
    {
        if (!UTConfig.BUGFIXES_ENTITIES.utBoatOffsetToggle) return offset;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBoatOffset ::: Return offset");
        return 0;
    }
}