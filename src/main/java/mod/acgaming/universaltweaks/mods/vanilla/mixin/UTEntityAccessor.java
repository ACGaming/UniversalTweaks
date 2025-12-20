package mod.acgaming.universaltweaks.mods.vanilla.mixin;

import net.minecraft.entity.Entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface UTEntityAccessor
{
    @Accessor("inPortal")
    boolean isInPortal();
}
