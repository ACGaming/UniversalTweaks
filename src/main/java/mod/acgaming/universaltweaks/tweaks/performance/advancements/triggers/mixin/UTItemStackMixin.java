package mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.mixin;

import net.minecraft.item.ItemStack;

import mod.acgaming.universaltweaks.tweaks.performance.advancements.triggers.IPrevSizeTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

// Courtesy of jchung01
@Mixin(value = ItemStack.class)
public class UTItemStackMixin implements IPrevSizeTracker
{
    @Unique
    private int ut$prevStackSize;

    @Override
    public void ut$setPreviousStackSize(int size)
    {
        ut$prevStackSize = size;
    }

    @Override
    public int ut$getPreviousStackSize()
    {
        return ut$prevStackSize;
    }
}
