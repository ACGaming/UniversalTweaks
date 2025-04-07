package mod.acgaming.universaltweaks.mods.mekanism.dupes.mixin;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.inventory.InventoryPersonalChest;
import mekanism.common.inventory.container.ContainerMekanism;
import mekanism.common.inventory.container.ContainerPersonalChest;
import mekanism.common.tile.TileEntityPersonalChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Focamacho
@Mixin(value = ContainerPersonalChest.class, remap = false)
public abstract class UTContainerPersonalChestMixin extends ContainerMekanism<TileEntityPersonalChest>
{
    @Shadow
    private boolean isBlock;

    @Shadow
    private IInventory itemInventory;

    protected UTContainerPersonalChestMixin(TileEntityPersonalChest tileEntityPersonalChest, InventoryPlayer inventory)
    {
        super(tileEntityPersonalChest, inventory);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        if (isBlock) return super.canInteractWith(player);

        final ItemStack currentItem = player.getHeldItemMainhand();
        if (itemInventory instanceof InventoryPersonalChest)
        {
            final ItemStack stack = ((InventoryPersonalChest) itemInventory).getStack();
            return super.canInteractWith(player) && !stack.isEmpty() && currentItem == stack && BlockStateMachine.MachineType.get(stack) == BlockStateMachine.MachineType.PERSONAL_CHEST;
        }
        return false;
    }
}