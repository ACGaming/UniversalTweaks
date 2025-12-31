package mod.acgaming.universaltweaks.mods.ae2uel.mixin;

import net.minecraft.item.ItemStack;

import appeng.util.inv.ItemHandlerIterator;
import appeng.util.inv.ItemSlot;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemHandlerIterator.class, remap = false)
public class UTItemHandlerIteratorMixin {

    @Shadow
    @Final
    private ItemSlot itemSlot;

    @Shadow
    private int slot;

    /// As far as I can tell, this crash happens when the IItemHandler that's being iterated over changes its
    /// length in the middle of being iterated over. This is obviously a bug, but AE2 has no control over how the
    /// IItemHandler behaves. This mixin changes the behaviour of ItemHandlerIterator to instead pretend these
    /// invalid slots are empty and cannot be extracted from, instead of crashing.
    ///
    /// java.util.NoSuchElementException
    ///     at appeng.util.inv.ItemHandlerIterator.next(ItemHandlerIterator.java:48)
    ///     at appeng.util.inv.ItemHandlerIterator.next(ItemHandlerIterator.java:28)
    ///     at appeng.parts.misc.ItemHandlerAdapter$InventoryCache.update(ItemHandlerAdapter.java:272)
    ///     at appeng.parts.misc.ItemHandlerAdapter.onTick(ItemHandlerAdapter.java:201)
    ///     at appeng.parts.misc.PartStorageBus.tickingRequest(PartStorageBus.java:316)
    ///     at appeng.me.cache.TickManagerCache.onUpdateTick(TickManagerCache.java:91)
    ///     at appeng.me.GridCacheWrapper.onUpdateTick(GridCacheWrapper.java:40)
    ///     at appeng.me.Grid.update(Grid.java:229)
    ///     at appeng.hooks.TickHandler.onTick(TickHandler.java:196)
    ///     at net.minecraftforge.fml.common.eventhandler.ASMEventHandler.invoke(ASMEventHandler.java:78)
    ///     at net.minecraftforge.fml.common.eventhandler.EventBus.post(EventBus.java:212)
    ///     at net.minecraftforge.fml.common.FMLCommonHandler.onPostServerTick(FMLCommonHandler.java:277)
    ///     at net.minecraft.server.MinecraftServer.tick(MinecraftServer.java:712)
    ///     at net.minecraft.server.integrated.IntegratedServer.tick(IntegratedServer.java:185)
    ///     at net.minecraft.server.MinecraftServer.run(MinecraftServer.java:526)
    ///     at java.lang.Thread.run(Thread.java:1474)
    @Inject(method = "next()Lappeng/util/inv/ItemSlot;", at = @At(value = "NEW", target = "()Ljava/util/NoSuchElementException;"), cancellable = true)
    private void ut$preventNSEE(CallbackInfoReturnable<ItemSlot> cir) {
        ((UTItemSlotAccessor) this.itemSlot).ut$setExtractable(false);
        this.itemSlot.setItemStack(ItemStack.EMPTY);
        this.itemSlot.setSlot(this.slot);
        ++this.slot;
        cir.setReturnValue(this.itemSlot);
    }
}
