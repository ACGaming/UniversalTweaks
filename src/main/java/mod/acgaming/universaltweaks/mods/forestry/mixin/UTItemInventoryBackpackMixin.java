package mod.acgaming.universaltweaks.mods.forestry.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import forestry.core.inventory.ItemInventory;
import forestry.storage.inventory.ItemInventoryBackpack;
import forestry.storage.items.ItemBackpack;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of Focamacho
@Mixin(value = ItemInventoryBackpack.class, remap = false)
public abstract class UTItemInventoryBackpackMixin extends ItemInventory
{
    public UTItemInventoryBackpackMixin(EntityPlayer player, int size, ItemStack parent)
    {
        super(player, size, parent);
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player)
    {
        return player.getHeldItemMainhand().getItem() instanceof ItemBackpack;
    }
}