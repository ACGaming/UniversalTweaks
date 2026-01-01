package mod.acgaming.universaltweaks.mods.ae2uel.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import appeng.util.inv.ItemSlot;

@Mixin(value = ItemSlot.class, remap = false)
public interface UTItemSlotAccessor {

    @Invoker("setExtractable")
    void ut$setExtractable(boolean isExtractable);

}
