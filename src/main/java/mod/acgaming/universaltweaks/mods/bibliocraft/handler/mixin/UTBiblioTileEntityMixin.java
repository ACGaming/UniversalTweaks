package mod.acgaming.universaltweaks.mods.bibliocraft.handler.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

import jds.bibliocraft.tileentities.BiblioTileEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of WaitingIdly
@Mixin(value = BiblioTileEntity.class, remap = false)
public abstract class UTBiblioTileEntityMixin implements IItemHandler
{
    @Shadow
    public NonNullList<ItemStack> inventory;

    /**
     * @author WaitingIdly
     * BiblioCraft has their {@link BiblioTileEntity} implement both
     * {@link net.minecraft.inventory.IInventory IInventory} and {@link IItemHandler}.
     * Both of these classes have a method <code>getStackInSlot</code> method -
     * {@link IItemHandler#getStackInSlot(int)} and {@link net.minecraft.inventory.IInventory#getStackInSlot(int) IInventory#getStackInSlot(int)}.
     * However, the vanilla class gets obsfucated, which changes the method name to <code>func_70301_a</code>.
     * This means that anything that tries to access the method {@link IItemHandler#getStackInSlot(int)} will fail and crash,
     * as the method is not implemented.
     * <p>
     * This creates a copy of that method but in such a way that it will not be obsfucated.
     */
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public @NotNull ItemStack getStackInSlot(int slot)
    {
        ItemStack output = ItemStack.EMPTY;
        if (slot >= 0 && slot < this.inventory.size())
        {
            output = this.inventory.get(slot);
        }
        return output;
    }
}
