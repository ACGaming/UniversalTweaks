package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

import net.minecraft.item.ItemStack;

import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawer;
import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawerGroup;
import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController;
import com.jaquadro.minecraft.storagedrawers.capabilities.DrawerItemRepository;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import mod.acgaming.universaltweaks.mods.storagedrawers.api.IAuxData;
import mod.acgaming.universaltweaks.mods.storagedrawers.api.SlotGroupAccessor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TileEntityController.class, remap = false)
public class UTDrawerControllerMixin
{

    @Mixin(targets = "com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController$ItemRepository",
        remap = false)
    public static abstract class RepositoryMixin extends DrawerItemRepository
    {

        @Shadow
        @Final
        TileEntityController this$0;
        // should probably use unreflect for this
        @Unique
        Method UT$getGroupForSlotRecord = null;

        public RepositoryMixin(IDrawerGroup group)
        {
            super(group);
        }

        @Inject(method = "insertItem",
            at = @At(value = "INVOKE_ASSIGN",
                target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;"),
            cancellable = true)
        public void readData(ItemStack stack, boolean simulate, Predicate<ItemStack> predicate,
                             CallbackInfoReturnable<ItemStack> cir,
                             @Local Iterator<SlotGroupAccessor> iterator,
                             @Local LocalIntRef amount,
                             @Local Set<Integer> checkedSlots)
        {
            while (iterator.hasNext())
            {
                SlotGroupAccessor record = iterator.next();
                IDrawerGroup candidateGroup = UT$getDrawerGroup(this$0, record);
                if (candidateGroup != null)
                {
                    IDrawer drawerx = candidateGroup.getDrawer(record.UT$getSlot());
                    if (!drawerx.isEmpty() && this.testPredicateInsert(drawerx, stack, predicate) &&
                        this.hasAccess(candidateGroup, drawerx))
                    {
                        if (simulate)
                        {
                            int inserted = ((IAuxData) drawerx).getOrCreateData().get(record.UT$getSlot());
                            if (inserted + drawerx.getStoredItemCount() == drawerx.getMaxCapacity())
                            {
                                continue;
                            }
                        }
                        amount.set(simulate ?
                            Math.max(amount.get() - drawerx.getAcceptingRemainingCapacity(), 0) :
                            drawerx.adjustStoredItemCount(amount.get()));
                        if (amount.get() == 0)
                        {
                            cir.setReturnValue(ItemStack.EMPTY);
                        }

                        if (simulate)
                        {
                            checkedSlots.add(record.UT$getIndex());
                        }
                    }
                }
            }
        }

        @Shadow
        protected abstract boolean hasAccess(IDrawerGroup group, IDrawer drawer);

        @Unique
        private IDrawerGroup UT$getDrawerGroup(TileEntityController controller, SlotGroupAccessor o)
        {
            try
            {
                if (UT$getGroupForSlotRecord == null)
                {
                    UT$getGroupForSlotRecord = TileEntityController.class
                        .getDeclaredMethod("getGroupForSlotRecord", o.UT$getType());
                    UT$getGroupForSlotRecord.setAccessible(true);
                }

                return (IDrawerGroup) UT$getGroupForSlotRecord.invoke(controller, o);
            }
            catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    @Pseudo
    @Mixin(targets = "com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityController$SlotRecord", remap = false)
    private static abstract class SlotRecordMixin implements SlotGroupAccessor
    {

        @Shadow
        public int slot;

        @Shadow
        public int index;

        @Override
        public int UT$getSlot()
        {
            return slot;
        }

        @Override
        public int UT$getIndex()
        {
            return index;
        }

        @Override
        public Class<?> UT$getType()
        {
            return getClass();
        }
    }
}
