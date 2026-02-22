package mod.acgaming.universaltweaks.mods.divinerpg.armorset.mixin;

import divinerpg.capabilities.armor.ArmorPowers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ArmorPowers.class, remap = false)
public interface UTArmorPowersAccessor
{
    @Invoker("unsubscribe")
    void ut$unsubscribe();
}
