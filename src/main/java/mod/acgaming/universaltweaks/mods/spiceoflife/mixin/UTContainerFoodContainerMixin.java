package mod.acgaming.universaltweaks.mods.spiceoflife.mixin;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import squeek.spiceoflife.inventory.ContainerFoodContainer;
import squeek.spiceoflife.inventory.ContainerGeneric;
import squeek.spiceoflife.items.ItemFoodContainer;

// Courtesy of Focamacho
@Mixin(value = ContainerFoodContainer.class, remap = false)
public abstract class UTContainerFoodContainerMixin extends ContainerGeneric
{
    public UTContainerFoodContainerMixin(IInventory inventory)
    {
        super(inventory);
    }

    @Shadow
    @Nonnull
    public abstract ItemStack getItemStack();

    @ParametersAreNonnullByDefault
    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return inventory.isUsableByPlayer(playerIn) && playerIn.getHeldItemMainhand().getItem() instanceof ItemFoodContainer;
    }
}