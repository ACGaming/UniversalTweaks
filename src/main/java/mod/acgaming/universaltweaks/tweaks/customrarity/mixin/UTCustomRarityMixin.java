package mod.acgaming.universaltweaks.tweaks.customrarity.mixin;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

import mod.acgaming.universaltweaks.tweaks.customrarity.UTCustomRarity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class UTCustomRarityMixin
{
    @Inject(method = "getRarity", at = @At("RETURN"), cancellable = true)
    public void utGetCustomRarity(ItemStack stack, CallbackInfoReturnable<EnumRarity> cir)
    {
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem());
        if (rarity != null) cir.setReturnValue(rarity);
    }

    @Inject(method = "getForgeRarity", at = @At("RETURN"), cancellable = true, remap = false)
    public void utGetCustomForgeRarity(ItemStack stack, CallbackInfoReturnable<IRarity> cir)
    {
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem());
        if (rarity != null) cir.setReturnValue(rarity);
    }
}