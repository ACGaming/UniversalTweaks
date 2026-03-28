package mod.acgaming.universaltweaks.bugfixes.misc.potion.mixin;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.PotionEffect;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

// MC-130847
// https://bugs.mojang.com/browse/MC-130847
// Courtesy of fonnymunkey
@Mixin(InventoryEffectRenderer.class)
public abstract class UTPotionAmplifierMixin
{
    @ModifyArg(method = "drawActivePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I", ordinal = 0))
    public String utDrawActivePotionEffects(String text, @Local PotionEffect potioneffect)
    {
        int amplifier = potioneffect.getAmplifier();
        if (amplifier > 3)
        {
            String potionLevel = I18n.format("enchantment.level." + (amplifier + 1));
            if (!potionLevel.contains("enchantment.level.")) text += " " + potionLevel;
            else text += " " + (amplifier + 1);
        }
        return text;
    }
}
