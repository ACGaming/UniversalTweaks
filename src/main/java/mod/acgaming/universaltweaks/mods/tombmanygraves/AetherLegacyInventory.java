package mod.acgaming.universaltweaks.mods.tombmanygraves;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.gildedgames.the_aether.api.AetherAPI;
import com.gildedgames.the_aether.api.player.util.IAccessoryInventory;
import com.m4thg33k.tombmanygraves.api.GraveInventoryHelper;
import com.m4thg33k.tombmanygraves.api.GraveRegistry;
import com.m4thg33k.tombmanygraves.api.IGraveInventory;
import com.m4thg33k.tombmanygraves.api.TempInventory;

/**
 * Same code as their source code at
 * <a href="https://github.com/M4thG33k/TombManyGraves2/blob/master/src/main/java/com/m4thg33k/tombmanygraves/inventory/AetherLegacyInventory.java">AetherLegacyInventory.java</a>
 * except using valid methods + removing the println calls + actually part of a jar.
 */
@SuppressWarnings("unused") // GraveRegistry is registered via reflection
@GraveRegistry(id = "aether_legacy", name = "Aether", reqMod = "aether_legacy", overridable = true)
public class AetherLegacyInventory implements IGraveInventory
{
    @Override
    public boolean pregrabLogic(EntityPlayer player)
    {
        return true;
    }

    @Override
    public TempInventory getItems(EntityPlayer player)
    {
        return GraveInventoryHelper.storeInventory(AetherAPI.getInstance().get(player).getAccessoryInventory());
    }

    @Override
    public void insertInventory(EntityPlayer player, TempInventory graveItems, boolean shouldForce)
    {
        IAccessoryInventory inventory = AetherAPI.getInstance().get(player).getAccessoryInventory();
        for (int i = 0; i < graveItems.getSizeInventory(); i++)
        {
            ItemStack graveItem = graveItems.getStackInSlot(i);

            if (!graveItem.isEmpty())
            {
                ItemStack playerItem = inventory.getStackInSlot(i).copy();

                if (playerItem.isEmpty())
                {
                    // No problem, just put the grave item in!
                    inventory.setInventorySlotContents(i, graveItem);
                }
                else if (shouldForce)
                {
                    // Slot is blocked, but we're forcing it
                    inventory.setInventorySlotContents(i, graveItem);
                    GraveInventoryHelper.dropItem(player, playerItem);
                }
                else
                {
                    // Slot is blocked, but not forcing
                    GraveInventoryHelper.dropItem(player, graveItem);
                }
            }
        }
    }
}