package mod.acgaming.universaltweaks.tweaks.incurablepotions.mixin;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.incurablepotions.UTIncurablePotions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityLivingBase.class, remap = false)
public class UTIncurablePotionsMixin
{
    @Redirect(method = "curePotionEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;isCurativeItem(Lnet/minecraft/item/ItemStack;)Z"))
    public boolean utCurePotionEffects(PotionEffect effect, ItemStack curativeItem)
    {
        if (UTConfig.TWEAKS_MISC.utIncurablePotions.length < 1) return effect.isCurativeItem(curativeItem);
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTIncurablePotions ::: Check potion effect");
        return effect.isCurativeItem(curativeItem) && !UTIncurablePotions.incurablePotions.contains(effect.getPotion().getRegistryName().toString());
    }
}