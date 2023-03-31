package mod.acgaming.universaltweaks.tweaks.pickupnotification;

import java.lang.reflect.Field;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Courtesy of gigaherz
public class UTContainerWrapper extends ContainerPlayer
{
    private static final Field f_listeners = ObfuscationReflectionHelper.findField(Container.class, FMLLaunchHandler.isDeobfuscatedEnvironment() ? "listeners" : "field_75149_d");
    private final ContainerPlayer original;
    private final Runnable callback;

    @SuppressWarnings("unchecked")
    public UTContainerWrapper(ContainerPlayer original, EntityPlayer player, Runnable callback)
    {
        super(player.inventory, original.isLocalWorld, player);
        this.original = original;
        this.callback = callback;
        this.craftMatrix = original.craftMatrix;
        this.craftResult = original.craftResult;
        this.isLocalWorld = original.isLocalWorld;
        this.inventoryItemStacks = original.inventoryItemStacks;
        this.inventorySlots = original.inventorySlots;
        this.windowId = original.windowId;
        try
        {
            this.listeners = (List<IContainerListener>) f_listeners.get(original);
        }
        catch (IllegalAccessException ignored) {}
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn)
    {
        if (original != null) original.onCraftMatrixChanged(inventoryIn);
    }

    @Override
    public void onContainerClosed(EntityPlayer playerIn)
    {
        original.onContainerClosed(playerIn);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        return original.canInteractWith(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return original.transferStackInSlot(playerIn, index);
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn)
    {
        return original.canMergeSlot(stack, slotIn);
    }

    @Override
    protected Slot addSlotToContainer(Slot slotIn)
    {
        // Ignore -- the original container keeps the list of slots
        return slotIn;
    }

    @Override
    public void addListener(IContainerListener listener)
    {
        original.addListener(listener);
    }

    @Override
    public NonNullList<ItemStack> getInventory()
    {
        return original.getInventory();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void removeListener(IContainerListener listener)
    {
        original.removeListener(listener);
    }

    @Override
    public void detectAndSendChanges()
    {
        original.detectAndSendChanges();
    }

    @Override
    public boolean enchantItem(EntityPlayer playerIn, int id)
    {
        return original.enchantItem(playerIn, id);
    }

    @Override
    @Nullable
    public Slot getSlotFromInventory(IInventory inv, int slotIn)
    {
        return original.getSlotFromInventory(inv, slotIn);
    }

    @Override
    public Slot getSlot(int slotId)
    {
        return original.getSlot(slotId);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
    {
        return original.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack)
    {
        original.putStackInSlot(slotID, stack);
    }

    @Override
    public void setAll(List<ItemStack> p_190896_1_)
    {
        original.setAll(p_190896_1_);
        callback.run();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        original.updateProgressBar(id, data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public short getNextTransactionID(InventoryPlayer invPlayer)
    {
        return original.getNextTransactionID(invPlayer);
    }

    @Override
    public boolean getCanCraft(EntityPlayer player)
    {
        return original.getCanCraft(player);
    }

    @Override
    public void setCanCraft(EntityPlayer player, boolean canCraft)
    {
        original.setCanCraft(player, canCraft);
    }

    @Override
    public boolean canDragIntoSlot(Slot slotIn)
    {
        return original.canDragIntoSlot(slotIn);
    }
}