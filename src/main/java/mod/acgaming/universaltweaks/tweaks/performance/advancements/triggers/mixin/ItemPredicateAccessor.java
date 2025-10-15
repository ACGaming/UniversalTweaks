package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import javax.annotation.Nonnull;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemPredicate.class)
public interface ItemPredicateAccessor
{
    @Nonnull
    @Accessor("count")
    MinMaxBounds getCount();
}
