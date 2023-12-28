package mod.acgaming.universaltweaks.mods.ironbackpacks.mixin;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.container.ContainerBackpack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(ContainerBackpack.class)
public class UTContainerBackpackMixin extends Container
{
    @Shadow(remap = false)
    @Final
    @Nonnull
    private ItemStack backpackStack;

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        // Currently breaks hotkey functionality
        return this.backpackStack.getItem() instanceof IBackpack && (playerIn.getHeldItemMainhand() == this.backpackStack || playerIn.getHeldItemOffhand() == this.backpackStack);
    }
}