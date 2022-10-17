package mod.acgaming.hkntweaks.tweaks.mixin;

import net.minecraft.entity.ai.attributes.RangedAttribute;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RangedAttribute.class)
public interface RangedAttributeAccessor
{
    @Accessor("minimumValue")
    void setMinimumValue(double minimumValue);

    @Accessor("maximumValue")
    void setMaximumValue(double maximumValue);
}