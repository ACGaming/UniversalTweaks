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
        Integer meta = UTCustomRarity.itemMetaMap.get(stack.getItem());
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem());
        try
        {
            if (stack.getHasSubtypes() && stack.getMetadata() != meta) return;
        }
        catch (Exception ignored) {}
        if (rarity != null) cir.setReturnValue(rarity);
    }

    @Inject(method = "getForgeRarity", at = @At("RETURN"), cancellable = true, remap = false)
    public void utGetCustomForgeRarity(ItemStack stack, CallbackInfoReturnable<IRarity> cir)
    {
        Integer meta = UTCustomRarity.itemMetaMap.get(stack.getItem());
        EnumRarity rarity = UTCustomRarity.itemRarityMap.get(stack.getItem());
        try
        {
            if (stack.getHasSubtypes() && stack.getMetadata() != meta) return;
        }
        catch (Exception ignored) {}
        if (rarity != null) cir.setReturnValue(rarity);
    }
}