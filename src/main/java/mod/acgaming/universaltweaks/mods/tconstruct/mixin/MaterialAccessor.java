package mod.acgaming.universaltweaks.mods.tconstruct.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import slimeknights.tconstruct.library.materials.Material;

@Mixin(value = Material.class, remap = false)
public interface MaterialAccessor
{
    @Accessor("hidden")
    void setHidden(boolean hidden);
}