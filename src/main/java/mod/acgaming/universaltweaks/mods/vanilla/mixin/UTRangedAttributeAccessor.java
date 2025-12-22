package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.entity.ai.attributes.RangedAttribute;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RangedAttribute.class)
public interface UTRangedAttributeAccessor
{
    @Accessor("minimumValue")
    void setMinimumValue(double minimumValue);

    @Accessor("maximumValue")
    void setMaximumValue(double maximumValue);
}
