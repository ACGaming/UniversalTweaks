package mod.acgaming.universaltweaks.tweaks.misc.armorcurve.mixin;

import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// Courtesy of Jackiecrazy
@Mixin(ModifiableAttributeInstance.class)
public interface UTAttributeUpdater
{
    @Invoker("flagForUpdate")
    void invokeFlagForUpdate();
}