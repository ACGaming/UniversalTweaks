package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import java.util.Objects;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.ISlotContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Courtesy of jchung01
@Mixin(value = InventoryPlayer.class)
public class UTInventoryPlayerMixin implements ISlotContext
{
    @Unique
    private ItemStack ut$changedStack;

    @Unique
    private SlotCounts ut$slotCounts;

    @Override
    public SlotCounts ut$getSlotCounts()
    {
        return Objects.requireNonNull(ut$slotCounts, "ut$slotCounts was not set!");
    }

    @Override
    public void ut$setSlotCounts(int numFull, int numEmpty, int numOccupied)
    {
        ut$slotCounts = new SlotCounts(numFull, numEmpty, numOccupied);
    }

    @Override
    public ItemStack ut$getStack()
    {
        return ut$changedStack;
    }

    @Override
    public void ut$setStack(ItemStack stack)
    {
        ut$changedStack = stack;
    }

    @Override
    public void ut$resetContext()
    {
        ut$changedStack = ItemStack.EMPTY;
        ut$slotCounts = null;
    }
}
