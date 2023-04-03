package mod.acgaming.universaltweaks.tweaks.items.rarity.mixin;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IRarity;

import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
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
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem().toString() + meta);
        if (rarity != null) cir.setReturnValue(rarity);
    }

    @Inject(method = "getForgeRarity", at = @At("RETURN"), cancellable = true, remap = false)
    public void utGetCustomForgeRarity(ItemStack stack, CallbackInfoReturnable<IRarity> cir)
    {
        int meta = 0;
        try
        {
            if (stack.getHasSubtypes()) meta = stack.getMetadata();
        }
        catch (Exception ignored) {}
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem().toString() + meta);
        if (rarity != null) cir.setReturnValue(rarity);
    }
}