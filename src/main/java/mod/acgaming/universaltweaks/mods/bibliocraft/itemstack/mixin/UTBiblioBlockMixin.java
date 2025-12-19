package mod.acgaming.universaltweaks.mods.bibliocraft.itemstack.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import jds.bibliocraft.blocks.BiblioBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import mod.acgaming.universaltweaks.config.UTConfigMods;

// Courtesy of WaitingIdly
@Mixin(value = BiblioBlock.class, remap = false)
public class UTBiblioBlockMixin
{
    /**
     * @author WaitingIdly
     * @reason BiblioCraft creates a new itemstack with the same item, amount, and metadata as the old one.
     * It then copies the nbt data. This does not work correctly with forge capabilities.
     * This can lead to mods like Immersive Engineering or Simply Backpacks losing data (often items stored).
     * This replaces that by directly using {@link ItemStack#copy()}.
     */
    @WrapOperation(method = "dropStackInSlot", at = @At(value = "NEW", target = "(Lnet/minecraft/item/Item;II)Lnet/minecraft/item/ItemStack;"))
    private ItemStack utCopyItemDirectly(Item itemIn, int amount, int meta, Operation<ItemStack> original, @Local ItemStack stack)
    {
        if (UTConfigMods.BIBLIOCRAFT.utCopyItemStackCorrectlyToggle) return stack.copy();
        return original.call(itemIn, amount, meta);
    }

    /**
     * @author WaitingIdly
     * @reason We have already copied the nbt via the other mixin, {@link #utCopyItemDirectly},
     * and shouldn't copy a second time.
     */
    @ModifyExpressionValue(method = "dropStackInSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasTagCompound()Z"))
    private boolean utSkipSeparateNBTCopy(boolean original)
    {
        return original && !UTConfigMods.BIBLIOCRAFT.utCopyItemStackCorrectlyToggle;
    }
}
