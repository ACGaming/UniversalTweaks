package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.entity.item.EntityItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityItem.class)
public interface UTEntityItemAccessor
{
    @Invoker("combineItems")
    boolean callCombineItems(EntityItem other);
}
