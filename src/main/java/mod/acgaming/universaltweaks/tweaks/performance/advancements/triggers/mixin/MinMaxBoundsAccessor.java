package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import javax.annotation.Nullable;

import net.minecraft.advancements.critereon.MinMaxBounds;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = MinMaxBounds.class)
public interface MinMaxBoundsAccessor
{
    @Nullable
    @Accessor("min")
    Float getMin();

    @Nullable
    @Accessor("max")
    Float getMax();
}
