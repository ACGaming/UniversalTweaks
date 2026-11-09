package mod.acgaming.universaltweaks.tweaks.blocks.anvil.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemHoe.class)
public abstract class UTItemHoeMixin extends Item
{
    @Shadow
    protected Item.ToolMaterial toolMaterial;

    // Do YOU seriously hate hoes this much Mojang? To make them unrepairable on anvils by default?
    @Override
    public boolean getIsRepairable(@NotNull ItemStack toRepair, @NotNull ItemStack repair)
    {
        ItemStack mat = this.toolMaterial.getRepairItemStack();
        if (UTConfigTweaks.BLOCKS.ANVIL.utHoeAnvilRepairing && !mat.isEmpty() && OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }
}
