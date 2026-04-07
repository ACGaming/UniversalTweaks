package mod.acgaming.universaltweaks.mods.divinerpg.nightmarebed.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import divinerpg.objects.blocks.vethea.BlockNightmareBed;
import divinerpg.registry.ItemRegistry;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlockNightmareBed.class, remap = false)
public abstract class UTBlockNightmareBedMixin
{
    /**
     * @author WaitingIdly
     * @reason The Nightmare Bed is registered without a corresponding {@link net.minecraft.item.ItemBlock ItemBlock},
     * so running {@link Item#getItemFromBlock(Block)} always returns {@link net.minecraft.init.Items#AIR AIR}.
     */
    @WrapOperation(method = "getItemDropped", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;getItemFromBlock(Lnet/minecraft/block/Block;)Lnet/minecraft/item/Item;"))
    public Item targetValidItem(Block blockIn, Operation<Item> original)
    {
        if (!UTConfigMods.DIVINE_RPG.utFixNightmareBed) return original.call(blockIn);
        return ItemRegistry.nightmareBed;
    }

    /**
     * @author WaitingIdly
     * @reason The Nightmare Bed is registered without a corresponding {@link net.minecraft.item.ItemBlock ItemBlock},
     * so creating a new {@link ItemStack} instance from {@link divinerpg.registry.BlockRegistry#nightmareBed BlockRegistry.nightmareBed} always
     * creates an empty {@link ItemStack}.
     */
    @WrapOperation(method = "dropBlockAsItemWithChance", at = @At(value = "NEW", target = "(Lnet/minecraft/block/Block;I)Lnet/minecraft/item/ItemStack;"))
    public ItemStack dropValidItemStack(Block blockIn, int amount, Operation<ItemStack> original)
    {
        if (!UTConfigMods.DIVINE_RPG.utFixNightmareBed) return original.call(blockIn, amount);
        return new ItemStack(ItemRegistry.nightmareBed);
    }
}
