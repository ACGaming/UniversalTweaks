package mod.acgaming.universaltweaks.mods.abyssalcraft.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.common.entity.EntitySpiritItem;
import com.shinoow.abyssalcraft.common.handlers.ItemTransferEventHandler;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata.UTWorldDataCapability;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of jchung01
@Mixin(value = ItemTransferEventHandler.class, remap = false)
public class UTItemTransferEventHandlerMixin
{
    // Overwrites onTick() to be more performant
    @Inject(method = "onTick", at = @At(value = "HEAD"), cancellable = true)
    private void utOnTick(TickEvent.WorldTickEvent event, CallbackInfo ci)
    {
        if (!UTConfigMods.ABYSSALCRAFT.utOptimizedItemTransferToggle) return;
        ci.cancel();

        // Mostly copied from original onTick()
        if (event.side == Side.SERVER && event.type == TickEvent.Type.WORLD && event.phase == TickEvent.Phase.END)
        {
            World world = event.world;
            if (world.getTotalWorldTime() % 20 != 0) return;
            if (UTWorldDataCapability.getCap(world).isEmpty()) return;
            // Process a map of all configured tile entities instead of using stream
            UTWorldDataCapability.getCap(world).getFlattenedView().forEach((pos, tile) -> {
                if (!world.isBlockLoaded(pos)) return;
                // Get tile entity if it was unknown at add time
                if (tile == null)
                {
                    tile = world.getTileEntity(pos);
                    UTWorldDataCapability.getCap(world).updateConfigured(world.getChunk(pos), pos, tile);
                }
                // Could be disabled by spell
                if (!hasCap(tile)) return;

                IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
                for (ItemTransferConfiguration cfg : cap.getTransferConfigurations())
                {
                    IItemHandler inventory = ItemTransferEventHandler.getInventory(tile, cfg.getExitFacing());
                    // Sided inventories, you never know
                    if (inventory != null)
                    {
                        boolean hasFilter = !cfg.getFilter().isEmpty() && cfg.getFilter().stream().anyMatch(i -> !i.isEmpty());
                        ItemStack stack = ItemStack.EMPTY;
                        int slot = -1;
                        for (int i = 0; i < inventory.getSlots(); i++)
                        {
                            stack = inventory.getStackInSlot(i);
                            if (!stack.isEmpty()) if (!hasFilter || isInFilter(cfg.getFilter(), stack, cfg.filterByNBT()))
                            {
                                stack = inventory.extractItem(i, 1, true);
                                slot = i;
                                break;
                            }
                        }
                        if (!stack.isEmpty() && slot > -1)
                        {
                            BlockPos exitPos = cfg.getRoute()[cfg.getRoute().length - 1];
                            TileEntity te = world.getTileEntity(exitPos);
                            if (te != null)
                            {
                                IItemHandler exitInv = ItemTransferEventHandler.getInventory(te, cfg.getEntryFacing());
                                // Insertion worked
                                if (exitInv != null && ItemHandlerHelper.insertItem(exitInv, stack, true).isEmpty())
                                {
                                    stack = inventory.extractItem(slot, 1, false);
                                    EntitySpiritItem spirit = new EntitySpiritItem(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, stack.copy());
                                    spirit.setRoute(cfg.getRoute());
                                    spirit.setFacing(cfg.getEntryFacing());
                                    world.spawnEntity(spirit);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    @Shadow
    private boolean hasCap(TileEntity te)
    {
        return false;
    }

    @Shadow
    private boolean isInFilter(NonNullList<ItemStack> filter, ItemStack stack, boolean nbt)
    {
        return false;
    }
}