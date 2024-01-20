package mod.acgaming.universaltweaks.bugfixes.misc.potionamplifier.mixin;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// MC-130847
// https://bugs.mojang.com/browse/MC-130847
// Courtesy of fonnymunkey
@Mixin(InventoryEffectRenderer.class)
public class UTPotionAmplifierMixin
{
    private int amplifier = 0;

    @Inject(method = "drawActivePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getTextureManager()Lnet/minecraft/client/renderer/texture/TextureManager;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void utDrawActivePotionEffectsInject(CallbackInfo ci, int i, int j, int k, Collection collection, int l, Iterator var6, PotionEffect potioneffect, Potion potion)
    {
        this.amplifier = potioneffect.getAmplifier();
    }

    @ModifyArg(method = "drawActivePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;drawStringWithShadow(Ljava/lang/String;FFI)I", ordinal = 0))
    public String utDrawActivePotionEffectsModifyArg(String text)
    {
        if (this.amplifier > 3 && this.amplifier < 10) text += " " + I18n.format("enchantment.level." + (this.amplifier + 1));
        if (this.amplifier >= 10 && this.amplifier < 128) text += " " + (this.amplifier + 1);
        this.amplifier = 0;
        return text;
    }
}